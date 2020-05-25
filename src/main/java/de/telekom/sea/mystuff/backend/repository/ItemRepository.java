package de.telekom.sea.mystuff.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.telekom.sea.mystuff.backend.entities.Item;

//
//Achtung !  ------> hier muss INTERFACE: "interface" stehen !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	//public List<Item> findById(String items);
	//public Optional<Item> findById(Long id);
	
	// es ist leer, weil vieles bereits im JPA existiert !!! --> Es ist also wichtig!

}






