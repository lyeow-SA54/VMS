package iss.team5.vms.helper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.WeekFields;
import java.util.Calendar;

public class FirstDayOfCurrentWeek {
public static LocalDate value(LocalDate date)
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
}
