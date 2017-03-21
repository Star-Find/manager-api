package net.starfind.api.model;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class RankedPlayer extends Player {
	
	@Embeddable
	static final class RoleChange {
		
		Role role;
		
		LocalDate date;
		
		String notes;
	}
	
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
		setRole(role, Optional.empty(), Optional.empty());
	}

	public void setRole (Role role, Optional<String> notes, Optional<LocalDate> dateChanged) {
		if (role != null && !role.equals(this.role)) {
			RoleChange change = new RoleChange();
			change.role = role;
			change.notes = notes.orElse(null);
			change.date = dateChanged.orElse(LocalDate.now(Clock.systemUTC()));
			roleHistory.add(change);
			
			this.role = role;
		}
	}
}
