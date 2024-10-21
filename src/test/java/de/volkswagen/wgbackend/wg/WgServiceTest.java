package de.volkswagen.wgbackend.wg;

import de.volkswagen.wgbackend.item.Item;
import de.volkswagen.wgbackend.item.ItemService;
import de.volkswagen.wgbackend.profile.Profile;
import de.volkswagen.wgbackend.task.Task;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@Rollback
@Transactional
public class WgServiceTest {

	private final List<Profile> testProfiles = List.of(
			new Profile(new Wg(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "1234"), new ArrayList<>(),
					"AAA", "Tim", "S", "", "",
					""),
			new Profile(new Wg(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "1234"), new ArrayList<>(),
					"AAA", "Tim", "S", "", "",
					""));
	private final List<Item> testItems = List.of(
			new Item("A", 0, false, false, false),
			new Item("B", 1, false, false, false),
			new Item("C", 2, true, false, true)
	);
	private final List<Task> testTasks = List.of(new Task(), new Task());
	private final List<Wg> testWgs = List.of(
			new Wg(this.testProfiles, this.testItems, new ArrayList<>(), "1234"),
			new Wg(this.testProfiles, this.testItems, new ArrayList<>(), "1235"),
			new Wg(new ArrayList<>(), this.testItems, new ArrayList<>(), "1236"),
			new Wg(this.testProfiles, this.testItems, this.testTasks, "1237")
	);

	@InjectMocks
	private WgService wgService;

	@Mock
	private WgRepository wgRepository;

	@Mock
	private ItemService itemService;


	@Test
	public void testSaveWg() {
		Mockito.when(this.wgRepository.save(Mockito.any(Wg.class))).thenReturn(this.testWgs.get(1));
		Wg wg = this.wgService.save(this.testWgs.get(1));
		assertThat(wg).isNotNull();
	}

	@Test
	public void testGenerateWgPassword() {
		String password = this.wgService.generateWgPassword();
		assertThat(password).isNotNull();
		assertThat(password).isNotEmpty();
	}

	@Test
	public void testGetItemsForWgById() {
		Mockito.when(this.itemService.getAllForWg(Mockito.any(Wg.class))).thenReturn(this.testItems);
		Mockito.when(this.wgRepository.findById(1L)).thenReturn(Optional.of(this.testWgs.get(1)));
		Wg wg = this.wgService.save(this.testWgs.get(1));
		List<Item> items = this.wgService.getItemsForWgById(1);
		assertThat(items).isNotNull();
		assertThat(items).isNotEmpty();
		assertThat(items).containsAll(this.testItems);
	}

	@Test
	public void testAddItemToWg() {
		Mockito.when(this.itemService.saveItem(Mockito.any(Item.class))).thenReturn(this.testItems.getFirst());
		Mockito.when(this.wgRepository.save(Mockito.any(Wg.class))).thenReturn(this.testWgs.getFirst());
		Mockito.when(this.wgRepository.findById(0L)).thenReturn(Optional.of(this.testWgs.getFirst()));
		Wg wg = this.wgService.save(this.testWgs.getFirst());
		List<Item> items = this.wgService.addItemToWg(0, this.testItems.getFirst());
		assertThat(items).isNotNull();
		assertThat(items).isNotEmpty();
		assertThat(items).contains(this.testItems.getFirst());
		assertThat(wg.getItems()).doesNotContain(this.testItems.getLast());
	}

	@Test
	public void testUpdateItem() {
		Mockito.when(this.wgRepository.findById(1L)).thenReturn(Optional.of(this.testWgs.getFirst()));
		Mockito.when(this.itemService.getItemById(1L)).thenReturn(this.testWgs.getFirst().getItems().getFirst());
		Mockito.when(this.itemService.getAllForWg(Mockito.any(Wg.class))).thenReturn(this.testWgs.getFirst().getItems());
		List<Item> items = this.wgService.updateItem(1, 1, this.testItems.getLast());
		assertThat(items).isNotNull();
		assertThat(items).isNotEmpty();
		assertThat(items).contains(this.testItems.getFirst());
	}

	@Test
	public void testUpdateAllItems() {
		Mockito.when(this.wgRepository.findById(1L)).thenReturn(Optional.of(this.testWgs.getFirst()));
		Mockito.when(this.itemService.getItemById(1L)).thenReturn(this.testWgs.getFirst().getItems().getFirst());
		Mockito.when(this.itemService.getAllForWg(Mockito.any(Wg.class))).thenReturn(this.testWgs.getFirst().getItems());
		List<Item> items = this.wgService.updateAllItems(1, List.of(this.testItems.getLast()));
		assertThat(items).isNotNull();
		assertThat(items).isNotEmpty();
		assertThat(items).contains(this.testItems.getFirst());
	}

	@Test
	public void testDeleteItem() {
		Mockito.when(this.wgRepository.findById(1L)).thenReturn(Optional.of(this.testWgs.getFirst()));
		Mockito.when(this.itemService.getItemById(1L)).thenReturn(this.testWgs.getFirst().getItems().getFirst());
		Mockito.when(this.itemService.getAllForWg(Mockito.any(Wg.class))).thenReturn(this.testWgs.getFirst().getItems());
		List<Item> items = this.wgService.deleteItem(1, 1);
		assertThat(items).isNotNull();
		assertThat(items).isNotEmpty();
		assertThat(items).doesNotContain(this.testItems.getFirst());
	}
}