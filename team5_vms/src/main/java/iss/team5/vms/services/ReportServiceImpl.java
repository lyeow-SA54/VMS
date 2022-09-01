package iss.team5.vms.services;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iss.team5.vms.helper.BookingStatus;
import iss.team5.vms.helper.DateHelper;
import iss.team5.vms.helper.ReportCategory;
import iss.team5.vms.helper.ReportStatus;
import iss.team5.vms.model.Booking;
import iss.team5.vms.model.Report;
import iss.team5.vms.model.Room;
import iss.team5.vms.model.Student;
import iss.team5.vms.repositories.BookingRepo;
import iss.team5.vms.repositories.ReportRepo;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	ReportRepo rprepo;

	@Autowired
	BookingRepo brepo;

	@Autowired
	StudentService ss;
	
	@Autowired
	BookingService bs;

	@Autowired
	MailService ms;

	public boolean tableExist() {
		return rprepo.existsBy();
	}

	@Override
	@Transactional
	public List<Report> findAllReports() {
		List<Report> r = rprepo.findAll();
		return r;
	}

	@Transactional
	public Report findReportById(String id) {
		return rprepo.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Report createReport(Report report) {
		return rprepo.saveAndFlush(report);
	}

	@Override
	@Transactional
	public Report changeReport(Report report) {
		Report r = findReportById(report.getId());
		r.setDetails(report.getDetails());
		r.setStudent(report.getStudent());
		r.setBooking(report.getBooking());
		r.setCategory(report.getCategory());
		return rprepo.saveAndFlush(r);
	}
	
	@Override
	public boolean checkMultipleReports(Report report) {
		if (rprepo.findReportByBooking(report.getBooking())!=null)
		return true;
		else
		return false;
	}

	@Override
	@Transactional
	public void addBooking(Report report, Booking booking) {
		report.setBooking(booking);
		rprepo.saveAndFlush(report);
	}

	@Override
	public void dailyBookingCheckinScoring() {
		// scheduled to run at 11pm daily
		LocalDateTime now = LocalDateTime.now();
		LocalDateTime nextRun = now.withHour(23).withMinute(0).withSecond(0);
		if (now.compareTo(nextRun) > 0)
			nextRun = nextRun.plusDays(1);

		Duration duration = Duration.between(now, nextRun);
		long initialDelay = duration.getSeconds();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		// pulls list of bookings for the day with status = successful and checkedin =
		// false and adds 2 to score of student in booking
		Runnable scoreBookingCheckin = () -> {
			List<Booking> bookings = brepo.findAllByDate(LocalDate.now());
			List<Student> students = bookings.stream().filter(b -> b.getStatus().equals(BookingStatus.SUCCESSFUL))
					.filter(b -> !b.isCheckedIn()).map(Booking::getStudent).collect(Collectors.toList());
			students.stream().forEach(s -> s.setScore(s.getScore() + 2));
			students.stream().forEach(s -> ss.changeStudent(s));
		};

		scheduler.scheduleAtFixedRate(scoreBookingCheckin, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
	}

	@Override
	public void approveReportScoring(Report report) {

		Student student = report.getStudent();
		int score = student.getScore();
		if (report.getCategory().equals(ReportCategory.VANDALISE)) {
			score += 2;
		} else {
			score += 1;
		}
		student.setScore(score);
		report.setReportStatus(ReportStatus.APPROVED);
		createReport(report);
		ss.changeStudent(student);
		ms.sendSimpleMail(student.getUser().getEmail(),
				"REPORT AGAINST YOUR BOOKING ON:" + report.getBooking().getDate() + "FOR: "
						+ report.getBooking().getRoom().getRoomName() + "WAS APPROVED",
				"Your current score is now: " + student.getScore()
						+ ". \nPlease note your future booking application requests will be a lower priority once above 3.");
	}
	
	@Override
	public void weeklyScoringUpdate() {
		// scheduled to run at every Sunday 11pm
		LocalDateTime now = LocalDateTime.now();
		WeekFields weekFields = WeekFields.of(Locale.getDefault());
		LocalDateTime nextRun = LocalDateTime.now()
									.plusWeeks(1)
		                            .with(weekFields.dayOfWeek(),1)//sunday
		                            .withHour(23)
		                            .withMinute(59)
		                            .withSecond(0);
		
		if (now.compareTo(nextRun) > 0)
			nextRun = nextRun.plusDays(6);

		Duration duration = Duration.between(now, nextRun);
		long initialDelay = duration.getSeconds();

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

		// pulls list students with score >0
		// reduces all filtered students score by 1
		Runnable getStudentWithScore = () -> {
			List<Student> students = ss.findAllStudents().stream()
					.filter(s->s.getScore()>0)
					.collect(Collectors.toList());					
			students.stream().forEach(s -> s.setScore(s.getScore() - 1));
			students.stream().forEach(s -> ss.changeStudent(s));
		};

		scheduler.scheduleAtFixedRate(getStudentWithScore, initialDelay, TimeUnit.DAYS.toSeconds(6), TimeUnit.SECONDS);
	}
	
	@Override
	public int getReportStatusCounts(List<Report> report, ReportStatus status) {
		long count = report.stream().filter(b -> b.getReportStatus().equals(status)).count();
		return (int) count;
	}
	
	@Override
	public int getReportCatCounts(List<Report> report, ReportCategory category) {
		long count = report.stream().filter(b -> b.getCategory().equals(category)).count();
		return (int) count;
	}
	
	@Override
	public int getReportRoomCounts(List<Report> report, Room room) {
		long count = report.stream().filter(b -> b.getBooking().getRoom().equals(room)).count();
		return (int) count;
	}
	
	@Override
	public List<Report> findReportsInCurrentWeek(LocalDate date){
		LocalDate firstDayOfWeek = DateHelper.FirstDayOfDateWeek(date);
		List<Booking> booking = brepo.findByDateBetween(firstDayOfWeek, firstDayOfWeek.plusDays(6));
		List<Report> foundReports = new ArrayList<>();
		for (Booking b:booking) {
			Report r = rprepo.findReportByBooking(b);
			if (r != null) {
			foundReports.add(r);}
		}	

		return foundReports;
	}

}
