package de.telekom.sea.mystuff2.controller;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import de.telekom.sea.mystuff.backend.entities.Item;

public class ItemRestControllerTest {
	
	@LocalServerPort
	int randomServerPort;
	
	@Test
	void testGetAll() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "http://localhost:" + randomServerPort + "api/v1/items";
		URI uri = new URI(baseUrl);
		ResponseEntity<Item[]> actual = restTemplate.getForEntity(uri, Item[].class);
		for (Item item: actual.getBody()) {
			System.out.println(item);
			
		}
	}
	
}
