package net.starfind.api.model;

import static javax.persistence.InheritanceType.TABLE_PER_CLASS;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
	protected String id;
	
	@Column(nullable=false, length=12)
	protected String name;
	
	@ElementCollection(targetClass=NameChange.class)
	protected List<NameChange> nameHistory;
	
	public String getId() {
		return id;
	}
	
	public String getName () {
		return name;
	}
	
	public void setName (String name) {
		setName(name, Optional.empty());
	}
	
	public void setName (String name, Optional<LocalDate> changeDate) {
		if (name != null && !name.equalsIgnoreCase(this.name)) {
			NameChange change = new NameChange();
			change.name = name;
			change.changeDate = changeDate.orElse(LocalDate.now(Clock.systemUTC()));
			nameHistory.add(change);
			
			this.name = name;
		}
	}
	
}
