package de.volkswagen.wgbackend.task;


import de.volkswagen.wgbackend.exception.NoWgAssignedException;
import de.volkswagen.wgbackend.wg.Wg;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Transactional
	public Task addTask(Wg wg, Task task) {
		if (wg != null) {
			return this.taskRepository.save(task);
		} else {
			throw new NoWgAssignedException("No Wg assigned to this Task.");
		}
	}

	public Task getTaskById(String taskId) {
		return this.taskRepository.findById(Long.valueOf(taskId)).orElseThrow(EntityNotFoundException::new);
	}
}