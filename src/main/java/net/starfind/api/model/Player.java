package net.starfind.api.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Player {
	
	@Embeddable
	static final class NameChange {
		@Column(nullable=false, length=12)
		String name;
		
		@Column(nullable=false)
		LocalDate changeDate;
	}
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;
	
	@Column(nullable=false, length=12)
	private String name;
	
	@ElementCollection(targetClass=NameChange.class)
	private List<NameChange> nameHistory;
	
	public String getId() {
		return id;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		setName(name, LocalDate.now(Clock.systemUTC()));
	}
	
	public void setName (String name, LocalDate changeDate) {
		if (name != null && !name.equalsIgnoreCase(this.name)) {
			NameChange change = new NameChange();
			change.name = name;
			change.changeDate = changeDate;
			nameHistory.add(change);
			
			this.name = name;
		}
	}
	
}
