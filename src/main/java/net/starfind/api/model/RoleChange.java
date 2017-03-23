package net.starfind.api.model;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public final class RoleChange {

	@Column(nullable=false)
	private Role role;

	@Column(nullable=false)
	private LocalDate changeDate;
	
	private String notes;
	
	public RoleChange () {
		
	}
	
	public RoleChange (Role role, LocalDate changeDate, String notes) {
		this.role = Objects.requireNonNull(role);
		this.changeDate = Objects.requireNonNull(changeDate);
		this.notes = notes;
	}

	public Role getRole() {
		return role;
	}

	public LocalDate getChangeDate() {
		return changeDate;
	}

	public String getNotes() {
		return notes;
	}
}