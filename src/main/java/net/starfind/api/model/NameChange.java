package net.starfind.api.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class NameChange {
	@Column(nullable=false, length=12)
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