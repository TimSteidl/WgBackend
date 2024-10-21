package de.volkswagen.wgbackend.wg;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WgRepository extends JpaRepository<Wg, Long> {


	@Query("SELECT w FROM Wg w JOIN w.profiles p WHERE p.googleId = :id")
	Wg findWgByProfileId(@Param("id") String id);
}