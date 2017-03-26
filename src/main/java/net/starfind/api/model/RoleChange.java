package net.starfind.api.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public final class RoleChange {

	@Column(nullable=false)
	@NotNull
	private Role previousRole;

	@Column(nullable=false)
	@NotNull
	private LocalDate changeDate;
	
	private String notes;
	
	public RoleChange () {
		
	}
	
	public RoleChange (Role previousRole, LocalDate changeDate, String notes) {
		this.previousRole = previousRole;
		this.changeDate = changeDate;
		this.notes = notes;
	}

	public Role getPreviousRole() {
		return previousRole;
	}

	public LocalDate getChangeDate() {
		return changeDate;
	}

	public String getNotes() {
		return notes;
	}
}