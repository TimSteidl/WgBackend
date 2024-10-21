package de.volkswagen.wgbackend.wg;


import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class WgService {

	private final WgRepository wgRepository;

	public WgService(WgRepository wgRepository) {
		this.wgRepository = wgRepository;
	}

	public Optional<Wg> getWgById(long id) {
		return this.wgRepository.findById(id);
	}

	public String generateWgPassword() {
		return new Random().ints(10, 0, 9).mapToObj(String::valueOf).collect(Collectors.joining()) + "$";
	}

	@Transactional
	public Wg save(Wg wg) {
		if (wg.getWgPassword() == null) {
			wg.setWgPassword(this.generateWgPassword());
		}
		if (wg.getProfiles().isEmpty()) {
			throw new IllegalArgumentException("Can't save a Wg without at least one Profile.");
		}
		return this.wgRepository.save(wg);
	}
}