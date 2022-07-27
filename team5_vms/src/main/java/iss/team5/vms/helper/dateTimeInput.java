package iss.team5.vms.helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class dateTimeInput {

	public static LocalDate dateInput(String userInput) {

	    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	    LocalDate date = LocalDate.parse(userInput, dateFormat);
	    return date ;
	}
	
	public static LocalTime timeInput(String userInput) {

	    return LocalTime.parse(userInput) ;
	}
}
