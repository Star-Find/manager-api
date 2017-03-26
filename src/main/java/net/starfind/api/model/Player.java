package net.starfind.api.model;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;
import static javax.persistence.FetchType.EAGER;
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
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Inheritance(strategy=TABLE_PER_CLASS)
public abstract class Player {
	
	@Id
	@GeneratedValue
	@JsonProperty(access=READ_ONLY)
	protected UUID id;
	
	@Column(nullable=false, length=12, unique=true)
	@Pattern(regexp="^[a-zA-Z0-9\\ \\-\\_]{1,12}$", message="Invalid display name (player.name)")
	protected String name;
	
	@ElementCollection(targetClass=NameChange.class, fetch=EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
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
	
	public void updateName (String name) {
		updateName(name, LocalDate.now(Clock.systemUTC()));
	}
	
	public void updateName (String name, LocalDate changeDate) {
		if (name != null && !name.equalsIgnoreCase(this.name)) {
			nameHistory.add(new NameChange(this.name, changeDate));
			this.name = name;
		}
	}
	
}
