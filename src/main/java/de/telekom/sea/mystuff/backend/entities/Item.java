package de.telekom.sea.mystuff.backend.entities;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Item {
	
	// variablen	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	int amount;
	String location;
	String description;
	//Date lastUsed
	LocalDate lastUsed;
	
	
	// constructor
	public Item(String name, int amount, String dateString, String location, String description) {
		this.lastUsed = LocalDate.parse(dateString);
		this.amount = amount;
		this.name = name;
		this.description = description;
		this.location = location;
	}
	
	
	/* --> raus wegen NoArgsConstructor!!!!
	public Item() {
		super();
	} */

	
	
	/*
	 * 
	 * public Task build() {
        return new Task(id,summary, description,done, dueDate);
    }
	 * 
	 * 
	 */


	

}


/*
Was macht Lombok:
--> Lombok macht die Annotations im Compile-Step...


*/




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