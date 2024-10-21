package de.volkswagen.wgbackend.wg;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/Wg")
public class WgController {

	private final WgService wgService;

	public WgController(WgService wgService) {
		this.wgService = wgService;
	}

	@GetMapping("/{id}")
	public Optional<Wg> getWgById(@PathVariable long id) {
		return this.wgService.getWgById(id);
	}
}