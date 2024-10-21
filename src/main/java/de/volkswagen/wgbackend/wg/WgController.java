package de.volkswagen.wgbackend.wg;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Wg")
public class WgController {

	private final WgService wgService;

	public WgController(WgService wgService) {
		this.wgService = wgService;
	}

	@GetMapping("/{id}")
	public Wg getWgById(@PathVariable String id) {
		return this.wgService.getWgById(id);
	}
}