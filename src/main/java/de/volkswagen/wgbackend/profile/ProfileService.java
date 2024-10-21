package de.volkswagen.wgbackend.profile;


import org.springframework.stereotype.Service;

@Service
public class ProfileService {

	private final ProfileRepository profileRepository;

	public ProfileService(ProfileRepository profileRepository) {
		this.profileRepository = profileRepository;
	}
}