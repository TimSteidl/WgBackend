package de.volkswagen.wgbackend.profile;


import de.volkswagen.wgbackend.task.Task;
import de.volkswagen.wgbackend.task.TaskService;
import de.volkswagen.wgbackend.wg.Wg;
import de.volkswagen.wgbackend.wg.WgService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ProfileService {

	private final ProfileRepository profileRepository;
	private final TaskService taskService;
	private final WgService wgService;

	public ProfileService(ProfileRepository profileRepository, TaskService taskService, WgService wgService) {
		this.profileRepository = profileRepository;
		this.taskService = taskService;
		this.wgService = wgService;
	}

	public Profile getProfileById(String id) {
		return this.profileRepository.findByGoogleId(id).orElseThrow(EntityNotFoundException::new);
	}

	public List<Task> getTasksById(String id) {
		return this.getProfileById(id).getTasks();
	}

	public ResponseEntity<Wg> getWgById(String id) {
		Profile profile = this.getProfileById(id);
		if (profile.getWg() == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} else {
			return ResponseEntity.ok(profile.getWg());
		}
	}

	@Transactional
	public HttpStatus addTask(String id, Task task) {
		Profile profile = this.getProfileById(id);
		profile.checkIfWgIsAssigned();
		Task newTask = this.taskService.addTask(profile.getWg(), task);
		profile.getTasks().add(newTask);
		this.profileRepository.save(profile);
		return HttpStatus.CREATED;
	}

	public HttpStatus deleteTask(String id, String taskId) {
		Profile profile = this.getProfileById(id);
		profile.checkIfWgIsAssigned();
		Task task = this.taskService.getTaskById(taskId);
		profile.getTasks().remove(task);
		this.profileRepository.save(profile);
		return HttpStatus.ACCEPTED;
	}

	public HttpStatus updateTask(String id, String taskId, Task task) {
		Profile profile = this.getProfileById(id);
		profile.checkIfWgIsAssigned();
		Task oldTask = this.taskService.getTaskById(taskId);
		if (!profile.equals(task.getProfile())) {
			Profile newProfile = this.getProfileById(task.getProfile().getGoogleId());
			oldTask.setProfile(newProfile);
			//TODO Add email notification if enabled by the newly assigned profile.
		}
		oldTask.setColor(task.getColor());
		oldTask.setDays(task.getDays());
		oldTask.setDescription(task.getDescription());
		return HttpStatus.ACCEPTED;
	}

	public Profile createProfile(Profile profile, long wgId) {
		Wg wg = profile.getWg();
		if (wg == null) {
			this.wgService.getWgById(wgId).ifPresentOrElse(
					profile::setWg,
					() -> {
						profile.setWg(
								this.wgService.save(
										new Wg(List.of(profile), new ArrayList<>(), new ArrayList<>(),
												this.wgService.generateWgPassword())
								)
						);
					});
		}
		profile.checkIfWgIsAssigned();
		return this.profileRepository.save(profile);
	}
}