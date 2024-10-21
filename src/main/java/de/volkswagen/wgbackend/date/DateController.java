package de.volkswagen.wgbackend.date;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Date")
public class DateController {

	private final DateService dateService;

	public DateController(DateService dateService) {
		this.dateService = dateService;
	}
}