package net.starfind.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Embeddable
public final class NameChange {
	@Column(nullable=false, length=12)
	@Pattern(regexp="^[a-zA-Z0-9\\ \\-\\_]{1,12}$", message="Invalid display name (nameChange.previousName)")
	private String previousName;
	
	@Column(nullable=false)
	@NotNull
	private LocalDate changeDate;
	
	public NameChange () {
		
	}
	
	public NameChange (String previousName, LocalDate changeDate) {
		this.previousName = previousName;
		this.changeDate = changeDate;
	}

	public String getPreviousName() {
		return previousName;
	}

	public LocalDate getChangeDate() {
		return changeDate;
	}
}