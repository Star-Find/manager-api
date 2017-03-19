package net.starfind.api.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

public class Member {

	private UUID id;
	
	private LocalDate addedDate;
	
	private Map<LocalDate, String> nameHistory;
}
