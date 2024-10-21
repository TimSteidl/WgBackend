package de.volkswagen.wgbackend.profile;


import de.volkswagen.wgbackend.task.Task;
import de.volkswagen.wgbackend.wg.Wg;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profile")
public class ProfileController {

	private final ProfileService profileService;

	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping("/{id}")
	public Profile getProfileById(@PathVariable String id) {
		return this.profileService.getProfileById(id);
	}

	@GetMapping("/{id}/tasks")
	public List<Task> getTasksById(@PathVariable String id) {
		return this.profileService.getTasksById(id);
	}

	@GetMapping("/{id}/wg")
	public ResponseEntity<Wg> getWgById(@PathVariable String id) {
		return this.profileService.getWgById(id);
	}

	@PostMapping("/new/{id}")
	public ResponseEntity<Profile> createProfile(@PathVariable long id, @RequestBody Profile profile) {
		return ResponseEntity.ok(this.profileService.createProfile(profile, id));
	}

	@PostMapping("/{id}/tasks")
	public HttpStatus addTask(@PathVariable String id, @RequestBody Task task) {
		return this.profileService.addTask(id, task);
	}

	@DeleteMapping("/{id}/tasks/{taskId}")
	public HttpStatus deleteTask(@PathVariable String id, @PathVariable String taskId) {
		return this.profileService.deleteTask(id, taskId);
	}

	@PutMapping("/{id}/tasks/{taskId}")
	public HttpStatus updateTask(@PathVariable String id, @PathVariable String taskId, @RequestBody Task task) {
		return this.profileService.updateTask(id, taskId, task);
	}
}