package net.starfind.api.model;

import static javax.persistence.FetchType.EAGER;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankedPlayer extends Player {
	
	@Column(nullable=false)
	private LocalDate addedDate;
	
	@Column(nullable=false)
	@NotNull
	private Role role;
	
	@ElementCollection(targetClass=RoleChange.class, fetch=EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<RoleChange> roleHistory;
	
	public RankedPlayer () {
		addedDate = LocalDate.now(Clock.systemUTC());
	}
	
	public RankedPlayer (String name, Role role, LocalDate added) {
		this.name = name;
		this.role = role;
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

	public void updateRole (Role role) {
		updateRole(role, null);
	}

	public void updateRole (Role role, String notes) {
		updateRole(role, notes, LocalDate.now(Clock.systemUTC()));
	}

	public void updateRole (Role role, String notes, LocalDate changeDate) {
		if (role != null && !role.equals(this.role)) {
			roleHistory.add(new RoleChange(this.role, changeDate, notes));
			this.role = role;
		}
	}
}
