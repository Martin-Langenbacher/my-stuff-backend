package mystuff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mystuff.entities.Item;

//
//Achtung !  ------> hier muss INTERFACE: "interface" stehen !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
	
	//public List<Item> findById(String items);
	//public Optional<Item> findById(Long id);
	
	
	

}






