package net.starfind.api.request;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public final class NameChangeRequest {
	@Pattern(regexp="^[a-zA-Z0-9\\ \\-\\_]{1,12}$", message="Invalid display name (nameChange.name)")
	@NotNull
	private String name;
	
	private LocalDate changeDate;
	
	NameChangeRequest () {
		
	}
	
	public NameChangeRequest (String name) {
		this(name, null);
	}
	
	public NameChangeRequest (String name, LocalDate changeDate) {
		this.name = Objects.requireNonNull(name);
		this.changeDate = changeDate;
	}

	public String getName() {
		return name;
	}

	public LocalDate getChangeDate() {
		return changeDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((changeDate == null) ? 0 : changeDate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		NameChangeRequest other = (NameChangeRequest) obj;
		if (changeDate == null) {
			if (other.changeDate != null)
				return false;
		} else if (!changeDate.equals(other.changeDate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "NameChangeRequest [name=" + name + ", changeDate=" + changeDate + "]";
	}
	
}
