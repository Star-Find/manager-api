package net.starfind.api.model;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
public class RankedPlayer extends Player {
	
	@Embeddable
	static final class RoleChange {
		
		Role role;
		
		LocalDate date;
		
		String notes;
	}
	
	private LocalDate addedDate;
	
	private Role role;
	
	@ElementCollection(targetClass=RoleChange.class)
	private List<RoleChange> roleHistory;
	
	
	public RankedPlayer () {
		addedDate = LocalDate.now(Clock.systemUTC());
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
		if (role != null && !role.equals(this.role)) {
			RoleChange change = new RoleChange();
			change.role = role;
			change.notes = notes;
			change.date = LocalDate.now(Clock.systemUTC());
			roleHistory.add(change);
			
			this.role = role;
		}
	}
}
