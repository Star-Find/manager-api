package net.starfind.api.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Player {
	
	@Id
	@GeneratedValue
	protected UUID id;
	
	@Column(nullable=false, length=12, unique=true)
	protected String name;
	
	@ElementCollection(targetClass=NameChange.class)
	protected List<NameChange> nameHistory;
	
	public UUID getId() {
		return id;
	}
	
	public String getName () {
		return name;
	}

	public List<NameChange> getNameHistory() {
		return nameHistory;
	}
	
	public void setName (String name) {
		setName(name, LocalDate.now(Clock.systemUTC()));
	}
	
	public void setName (String name, LocalDate changeDate) {
		if (name != null && !name.equalsIgnoreCase(this.name)) {
			nameHistory.add(new NameChange(name, changeDate));
			this.name = name;
		}
	}
	
}
