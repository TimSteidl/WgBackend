package de.volkswagen.wgbackend.wg;


import org.springframework.stereotype.Service;

@Service
public class WgService {

	private final WgRepository wgRepository;

	public WgService(WgRepository wgRepository) {
		this.wgRepository = wgRepository;
	}

	public Wg getWgById(String id) {
		if (id.isEmpty()) {
			throw new IllegalArgumentException("Empty Id");
		}

		return this.wgRepository.findWgByProfileId(id);
	}
}