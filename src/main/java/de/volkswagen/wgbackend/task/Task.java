package de.volkswagen.wgbackend.task;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.volkswagen.wgbackend.date.Date;
import de.volkswagen.wgbackend.profile.Profile;
import de.volkswagen.wgbackend.wg.Wg;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Task {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Task.class)
	private Wg wg;

	private String description;

	private String color;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Task.class)
	@OneToMany(mappedBy = "task", orphanRemoval = true)
	private List<Date> days;

	@ManyToOne
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Task.class)
	private Profile profile;
}