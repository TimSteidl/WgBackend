package de.volkswagen.wgbackend.item;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.volkswagen.wgbackend.wg.Wg;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Item {

	@Id
	@GeneratedValue
	private int id;

	@ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Item.class)
	private Wg wg;

	private String name;

	private int position;

	private boolean isBought;
	private boolean isFavorite;
	private boolean isDeleted;
}