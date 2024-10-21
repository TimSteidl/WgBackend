package de.volkswagen.wgbackend.item;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import de.volkswagen.wgbackend.wg.Wg;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
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

	public Item(Wg wg, String name, int position, boolean isBought, boolean isFavorite, boolean isDeleted) {
		this.wg = wg;
		this.name = name;
		this.position = position;
		this.isBought = isBought;
		this.isFavorite = isFavorite;
		this.isDeleted = isDeleted;
	}

	public Item(String name, int position, boolean isBought, boolean isFavorite, boolean isDeleted) {
		this.name = name;
		this.position = position;
		this.isBought = isBought;
		this.isFavorite = isFavorite;
		this.isDeleted = isDeleted;
	}
}