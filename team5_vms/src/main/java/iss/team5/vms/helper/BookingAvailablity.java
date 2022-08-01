package iss.team5.vms.helper;

import lombok.Getter;

public enum BookingAvailablity {	
	OPEN("open"),
	FULL("full");
	
	@Getter
    private String displayName;

	BookingAvailablity(String displayName) {
        this.displayName = displayName;
    }
}
