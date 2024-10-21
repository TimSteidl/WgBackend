package de.volkswagen.wgbackend;

import de.volkswagen.wgbackend.profile.Profile;
import de.volkswagen.wgbackend.profile.ProfileRepository;
import de.volkswagen.wgbackend.task.Task;
import de.volkswagen.wgbackend.wg.Wg;
import de.volkswagen.wgbackend.wg.WgRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Rollback
@Transactional
public class WgRepositoryIntegrationTest {

	@Autowired
	private WgRepository wgRepository;

	@Autowired
	private ProfileRepository profileRepository;

	@Test
	public void testSaveProfileWithWg() {
		// Erstelle eine WG
		Wg wg = new Wg(new ArrayList<Profile>(), new ArrayList<>(), new ArrayList<>(), "1234");
		wg = this.wgRepository.save(wg);

		// Erstelle ein Profile und setze die WG
		Profile profile = new Profile(wg, new ArrayList<Task>(), "AAA", "Tim", "S", "", "", "");
		wg.setProfiles(List.of(profile));

		Wg finalWg = this.wgRepository.save(wg);
		this.profileRepository.findAll().forEach(profile1 -> assertThat(finalWg).isIn(profile1));

		this.wgRepository.findAll().forEach(wg1 -> assertThat(profile).isIn(wg1));
	}

	@Test
	public void testFindWgByProfileId() {
		// Setup Test Data

		// Erstelle eine WG
		Wg wg = new Wg(new ArrayList<Profile>(), new ArrayList<>(), new ArrayList<>(), "1234");
		wg = this.wgRepository.save(wg);

		// Erstelle ein Profile und setze die WG
		Profile profile = new Profile(wg, new ArrayList<Task>(), "AAA", "Tim", "S", "", "", "");

		// Test: Finde die WG anhand der Profile-ID
		Wg foundWg = this.wgRepository.findWgByProfileId(profile.getGoogleId());

		System.out.println(foundWg + " wg");
		assertThat(foundWg).isNotNull();
		assertThat(foundWg.getId()).isEqualTo(wg.getId());
		assertThat(foundWg.getProfiles()).contains(profile);
	}
}