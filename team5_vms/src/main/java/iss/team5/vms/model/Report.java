package iss.team5.vms.model;

import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import iss.team5.vms.helper.ReportStatus;
import org.hibernate.annotations.GenericGenerator;
import iss.team5.vms.helper.Category;
import iss.team5.vms.helper.StudentStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor

public class Report {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_id_gen")
	@GenericGenerator(name = "report_id_gen", strategy = "iss.team5.vms.generators.ReportIdGenerator")
	private String id;
	@NotNull
	private String details;
	private String imgPath;
	@OneToOne
	private Student student;
	@OneToOne
	private Booking booking;
	@Column(name = "category", columnDefinition = "ENUM('CLEANLINESS','VANDALISE','HOGGING','MISUSE')")
	@Enumerated(EnumType.STRING)
	private Category category;
	@Column(name = "Student_status", columnDefinition = "ENUM('PROBATION','NORMAL')")
	@Enumerated(EnumType.STRING)
	private StudentStatus status;
	@Column(name = "Report_status", columnDefinition = "ENUM('PROCESSING','REJECTED','APPROVED')")
	@Enumerated(EnumType.STRING)
	private ReportStatus reportStatus;
//	@OneToOne
//	private Image img;
	
	public Report(String details, Category category, StudentStatus status, ReportStatus reportStatus) {
		this.details=details;
		this.category=category;
		this.status=status;
		this.reportStatus=reportStatus;
	}
	
	public Report(String details, String imgPath) {
		this.details=details;
		this.imgPath=imgPath;
	}
	public Report(String details, String imgPath, Booking booking) {
		this.details=details;
		this.imgPath=imgPath;
		this.booking=booking;
	}
	public Report(String details, String imgPath, Booking booking, ReportStatus reportStatus) {
		this.details=details;
		this.imgPath=imgPath;
		this.booking=booking;
		this.reportStatus=reportStatus;
	}
	public Report(String details, String imgPath, Booking booking, ReportStatus reportStatus, Category category, Student student) {
		this.details=details;
		this.imgPath=imgPath;
		this.booking=booking;
		this.reportStatus=reportStatus;
		this.category=category;
		this.student=student;
	}
}