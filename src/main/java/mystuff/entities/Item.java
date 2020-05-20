package mystuff.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Item {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	int amount;
	String location;
	String description;
	Date lastUsed;

}




/*

	// ManyToOne: --> book
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	
	
	// OneToMany: --> borrow
	@OneToMany(mappedBy = "person")
	private List<Borrow> borrows;

}

*/