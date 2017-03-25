package net.starfind.api.request;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import net.starfind.api.model.Role;

public final class RoleChangeRequest {
	
	@NotNull
	private Role role;

	private LocalDate changeDate;
	
	private String notes;
	
	RoleChangeRequest () {
		
	}
	
	public RoleChangeRequest (Role role, LocalDate changeDate, String notes) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoleChangeRequest other = (RoleChangeRequest) obj;
		if (changeDate == null) {
			if (other.changeDate != null)
				return false;
		} else if (!changeDate.equals(other.changeDate))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (role != other.role)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RoleChangeRequest [role=" + role + ", changeDate=" + changeDate + ", notes=" + notes + "]";
	}

	public static class Builder {
		Role role;
		LocalDate changeDate;
		String notes;
		
		Builder (Role role) {
			this.role = role;
		}
		
		public Builder notes (String notes) {
			this.notes = notes;
			return this;
		}
		
		public Builder changeDate (LocalDate changeDate) {
			this.changeDate = changeDate;
			return this;
		}
		
		public RoleChangeRequest build() {
			return new RoleChangeRequest(role, changeDate, notes);
		}
	}
	
	public static Builder builder (Role role) {
		return new Builder(role);
	}

}
