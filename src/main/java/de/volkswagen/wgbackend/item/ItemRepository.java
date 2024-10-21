package de.volkswagen.wgbackend.item;


import de.volkswagen.wgbackend.wg.Wg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

	List<Item> findAllByWg(Wg wg);
}