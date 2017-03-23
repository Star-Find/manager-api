package net.starfind.api.model;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankedPlayer extends Player {
	
	@Column(nullable=false)
	private LocalDate addedDate;
	
	private Role role;
	
	@ElementCollection(targetClass=RoleChange.class)
	private List<RoleChange> roleHistory;
	
	public RankedPlayer () {
		addedDate = LocalDate.now(Clock.systemUTC());
	}
	
	public RankedPlayer (String name, LocalDate added) {
		this.name = name;
		this.addedDate = added;
	}

	public LocalDate getAddedDate() {
		return addedDate;
	}

	public List<RoleChange> getRoleHistory() {
		return roleHistory;
	}

	public Role getRole() {
		return role;
	}

	public void setRole (Role role) {
		setRole(role, null);
	}

	public void setRole (Role role, String notes) {
		setRole(role, notes, LocalDate.now(Clock.systemUTC()));
	}

	public void setRole (Role role, String notes, LocalDate changeDate) {
		if (role != null && !role.equals(this.role)) {
			roleHistory.add(new RoleChange(role, changeDate, notes));
			this.role = role;
		}
	}
}
