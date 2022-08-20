package iss.team5.vms.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Calendar;

public class DateHelper {
public static LocalDate FirstDayOfDateWeek(LocalDate date)
{
	int week  = date.get(WeekFields.ISO.weekOfWeekBasedYear());
//	int month = date.getMonth().getValue();
	int year = date.getYear();
	
	Calendar calendar = Calendar.getInstance();
	calendar.clear();
	calendar.set(Calendar.WEEK_OF_YEAR, week);
	calendar.set(Calendar.YEAR, year);
	
	return LocalDate.ofInstant(calendar.getTime().toInstant(), ZoneOffset.ofHours(8)).plusDays(8);
}

public static boolean isWeekend(final LocalDate ld) {
	DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
	return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
}
}
