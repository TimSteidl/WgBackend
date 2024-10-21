package de.volkswagen.wgbackend.item;


import de.volkswagen.wgbackend.wg.Wg;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

	private final ItemRepository itemRepository;

	public ItemService(ItemRepository itemRepository) {
		this.itemRepository = itemRepository;
	}

	public List<Item> getAllForWg(Wg wg) {
		return this.itemRepository.findAllByWg(wg).stream().filter(item -> !item.isDeleted()).toList();
	}

	public Item saveItem(Item item) {
		if (item.getWg() == null) {
			throw new IllegalArgumentException("Can't save an Item without a Wg.");
		}
		return this.itemRepository.save(item);
	}

	public Item getItemById(long id) {
		return this.itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	public Item updateItem(long itemId, Item item) {
		if (item.getId() != itemId) {
			throw new IllegalArgumentException("Item id does not match.");
		}
		Item oldItem = this.getItemById(itemId);
		oldItem.setName(item.getName());
		oldItem.setBought(item.isBought());
		oldItem.setFavorite(item.isFavorite());
		oldItem.setPosition(item.getPosition());
		return this.itemRepository.save(oldItem);
	}
}