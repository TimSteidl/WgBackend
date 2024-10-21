package de.volkswagen.wgbackend.wg;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.volkswagen.wgbackend.item.Item;
import de.volkswagen.wgbackend.profile.Profile;
import de.volkswagen.wgbackend.task.Task;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class Wg {

	@Id
	@GeneratedValue
	private int id;

	@OneToMany(mappedBy = "wg")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Wg.class)
	private List<Profile> profiles;

	@OneToMany(mappedBy = "wg")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Wg.class)
	private List<Item> items;

	@OneToMany(mappedBy = "wg")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Wg.class)
	private List<Task> tasks;

	private String wgPassword;

	public Wg() {
	}

	public Wg(List<Profile> profiles, List<Item> items, List<Task> tasks, String wgPassword) {
		this.profiles = profiles;
		this.items = items;
		this.tasks = tasks;
		this.wgPassword = wgPassword;
	}
}