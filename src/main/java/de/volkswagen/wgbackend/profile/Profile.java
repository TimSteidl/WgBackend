package de.volkswagen.wgbackend.profile;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.volkswagen.wgbackend.task.Task;
import de.volkswagen.wgbackend.wg.Wg;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
public class Profile {

	@Id
	@GeneratedValue
	private int id;

	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Profile.class)
	@ManyToOne(optional = false)
	@JoinColumn(name = "wg_id")
	private Wg wg;

	@OneToMany(mappedBy = "profile")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Profile.class)
	private List<Task> tasks;

	private String googleId;

	private String name;

	@Email
	private String email;

	private String picture;

	private String givenName;

	private String familyName;

	public Profile() {
	}

	public Profile(Wg wg, List<Task> tasks, String googleId, String name, String email, String picture,
	               String givenName, String familyName) {
		this.wg = wg;
		this.tasks = tasks;
		this.googleId = googleId;
		this.name = name;
		this.email = email;
		this.picture = picture;
		this.givenName = givenName;
		this.familyName = familyName;
	}
}