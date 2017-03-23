package net.starfind.api.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Pattern;

@Embeddable
public final class NameChange {
	@Column(nullable=false, length=12)
	@Pattern(regexp="^[a-zA-Z0-9\\ \\-\\_]{1,12}$", message="Invalid display name (nameChange.name)")
	private String name;
	
	@Column(nullable=false)
	private LocalDate changeDate;
	
	public NameChange () {
		
	}
	
	public NameChange (String name, LocalDate changeDate) {
		this.name = Objects.requireNonNull(name);
		this.changeDate = Objects.requireNonNull(changeDate);
	}

	public String getName() {
		return name;
	}

	public LocalDate getChangeDate() {
		return changeDate;
	}
}