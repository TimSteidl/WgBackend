package de.volkswagen.wgbackend.wg;


import de.volkswagen.wgbackend.item.Item;
import de.volkswagen.wgbackend.item.ItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class WgService {

	private final WgRepository wgRepository;
	private final ItemService itemService;

	public WgService(WgRepository wgRepository, ItemService itemService) {
		this.wgRepository = wgRepository;
		this.itemService = itemService;
	}

	public Optional<Wg> getWgById(long id) {
		return this.wgRepository.findById(id);
	}

	public Wg getCleanWgById(long id) {
		return this.getWgById(id).orElseThrow(EntityNotFoundException::new);
	}

	public String generateWgPassword() {
		return new Random().ints(10, 0, 9).mapToObj(String::valueOf).collect(Collectors.joining()) + "$";
	}

	@Transactional
	public Wg save(Wg wg) {
		if (wg.getWgPassword() == null) {
			wg.setWgPassword(this.generateWgPassword());
		}
		if (wg.getProfiles().isEmpty()) {
			throw new IllegalArgumentException("Can't save a Wg without at least one Profile.");
		}
		return this.wgRepository.save(wg);
	}

	public List<Item> getItemsForWgById(long id) {
		Wg wg = this.getCleanWgById(id);
		return this.itemService.getAllForWg(wg);
	}

	public List<Item> addItemToWg(long id, Item item) {
		Wg wg = this.getCleanWgById(id);
		Item items = this.itemService.saveItem(item);
		wg.getItems().add(items);
		return this.wgRepository.save(wg).getAllActiveItems();
	}

	public List<Item> updateItem(long id, long itemId, Item item) {
		Wg wg = this.getCleanWgById(id);
		this.itemService.updateItem(itemId, item);
		return this.getItemsForWgById(id);
	}

	public List<Item> updateAllItems(long id, List<Item> items) {
		Wg wg = this.getCleanWgById(id);
		items = items.stream().map(item -> this.itemService.updateItem(item.getId(), item)).toList();
		return this.getItemsForWgById(id);
	}

	public List<Item> deleteItem(long id, long itemId) {
		Wg wg = this.getCleanWgById(id);
		Item item = this.itemService.getItemById(itemId);
		item.setDeleted(true);
		this.itemService.updateItem(itemId, item);
		return this.getItemsForWgById(id);
	}
}