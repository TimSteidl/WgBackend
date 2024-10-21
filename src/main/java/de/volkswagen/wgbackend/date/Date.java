package de.volkswagen.wgbackend.date;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.volkswagen.wgbackend.task.Task;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Date {

	@Id
	@GeneratedValue
	private int id;

	private LocalDate day;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Date.class)
	@ManyToOne(optional = false)
	private Task task;
}