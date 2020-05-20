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
	
	public List<Item> findByAuthor(String author);
	public Optional<Item> findById(Long id);
	
	
	

}









/*
package checkspringpackage.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import checkspringpackage.entities.Book;



@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	
	public Optional<Book> findByTitle(String title);
	public List<Book> findByAuthor(String author);

}


/*

Anmerkung:
================================================	
1) Repository vergessen...
2) FindBy... funktioniert nicht...!
3) bookRepository.save(book); --> bookRepository unterstrichen: Funktioniert nicht...!

4) bookfound (?) in Eclipse...




*/





