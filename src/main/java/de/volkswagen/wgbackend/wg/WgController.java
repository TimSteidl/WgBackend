package de.volkswagen.wgbackend.wg;


import de.volkswagen.wgbackend.item.Item;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Wg")
public class WgController {

	private final WgService wgService;

	public WgController(WgService wgService) {
		this.wgService = wgService;
	}

	@GetMapping("/{id}")
	public Wg getWgById(@PathVariable long id) {
		return this.wgService.getCleanWgById(id);
	}

	@GetMapping("/{id}/items")
	public List<Item> getItemsForWgById(@PathVariable long id) {
		return this.wgService.getItemsForWgById(id);
	}

	@PostMapping("/{id}/items")
	public List<Item> addItemToWg(@PathVariable long id, @RequestBody Item item) {
		return this.wgService.addItemToWg(id, item);
	}

	@PutMapping("/{id}/items/{itemId}")
	public List<Item> updateItem(@PathVariable long id, @PathVariable long itemId, @RequestBody Item item) {
		return this.wgService.updateItem(id, itemId, item);
	}

	@PutMapping("/{id}/items")
	public List<Item> updateAllItems(@PathVariable long id, @RequestBody List<Item> items) {
		return this.wgService.updateAllItems(id, items);
	}

	@DeleteMapping("/{id}/items/{itemId}")
	public List<Item> deleteItem(@PathVariable long id, @PathVariable long itemId) {
		return this.wgService.deleteItem(id, itemId);
	}
}