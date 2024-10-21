package de.volkswagen.wgbackend.date;


import org.springframework.stereotype.Service;

@Service
public class DateService {

	private final DateRepository dateRepository;

	public DateService(DateRepository dateRepository) {
		this.dateRepository = dateRepository;
	}
}