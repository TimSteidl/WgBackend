package de.volkswagen.wgbackend.task;


import org.springframework.stereotype.Service;

@Service
public class TaskService {

	private final TaskRepository taskRepository;

	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}
}