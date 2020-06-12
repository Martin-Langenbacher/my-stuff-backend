package de.telekom.sea.mystuff.backend.bootstrap;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import de.telekom.sea.mystuff.backend.entities.Item;
import de.telekom.sea.mystuff.backend.repository.ItemRepository;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	// ApplicationListener: --> anderer Name: Observer --> man hängt sich in die Events rein - 
	
	private ItemRepository itemRepository;
	
	
	public DevBootstrap(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }
	
	

	@Override
	public void onApplicationEvent(ContextRefreshedEvent arg0) {
		this.initData();
	}



	private void initData() {
		// Computer, Monitor, Büroklammer...
		
		for (int i = 0; i < 10; i++) {
			Item item1 = new Item("Computer", 2, "2020-05-22", "Büro", "Diesen Computer benutze ich im HomeOffice.");
			Item item2 = new Item("Monitor", 1, "2019-12-14", "Büro", "Monitor zum Computer.");
			Item item3 = new Item("Büroklammern", 122, "2018-01-02", "Büro", "Um etwas zusammen zu heften.");
			Item item4 = new Item("Computer-Maus", 3, "2020-05-27", "Keller", "Hier ist ein etwas längerer Text um das Programm zu testen. Die Maus ist nur ein Item, das eben da ist und nicht benutzt wird. Im Keller ist es auch nicht. Hier ist nun aber wirklich Schluß mit der Erklärung!");
			
			
			this.itemRepository.save(item1);
			this.itemRepository.save(item2);
			this.itemRepository.save(item3);
			this.itemRepository.save(item4);
		}
		
		
		// Tip:
		// Date.valueOf("2019-11-23");

// --> Alternativen:
//		List<Item> newItems = Arrays.asList(item1, item2, item3);
//		itemRepository.saveAll(newItems);
		
		
		// Option: --> in case we have only the normal constructor !!!!!!
		// item1.setAmount(2);
		
		
		
		
	}

}



/* Other example: 
====================================
package com.udemy.guru.spring5webapp.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.udemy.guru.spring5webapp.model.Author;
import com.udemy.guru.spring5webapp.model.Book;
import com.udemy.guru.spring5webapp.model.Publisher;
import com.udemy.guru.spring5webapp.repositories.AuthorRepository;
import com.udemy.guru.spring5webapp.repositories.BookRepository;
import com.udemy.guru.spring5webapp.repositories.PublisherRepository;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private PublisherRepository publisherRepository;

    public DevBootstrap(AuthorRepository authorRepository, BookRepository bookRepository,
            PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        this.initData();
    }

    private void initData() {
        // Eric
        Publisher testPublisher = new Publisher("Andres Books", "España, Murcia");
        Author eric = new Author("Eric", "Evans");
        Book ddd = new Book("Domain Driven Design", "1234", testPublisher);
        eric.getBooks().add(ddd);
        ddd.getAuthors().add(eric);

        this.publisherRepository.save(testPublisher);
        this.authorRepository.save(eric);
        this.bookRepository.save(ddd);

        // Rod
        Publisher testPublisher2 = new Publisher("Angel Books", "España, Madrid");
        Author rod = new Author("Rod", "Johnson");
        Book noEJB = new Book("J2EE Development without EJB", "23444", testPublisher2);
        eric.getBooks().add(noEJB);
        noEJB.getAuthors().add(rod);

        this.publisherRepository.save(testPublisher2);
        this.authorRepository.save(rod);
        this.bookRepository.save(noEJB);
    }

}


*/
