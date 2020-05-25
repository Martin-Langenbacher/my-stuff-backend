package de.telekom.sea.mystuff.backend.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
// import com.sun.org.apache.xml.internal.utils.URI;
// import com.sun.org.apache.xerces.internal.util.URI;

import java.net.URISyntaxException;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import de.telekom.sea.mystuff.backend.entities.Item;
import de.telekom.sea.mystuff.backend.repository.ItemRepository;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
//import io.swagger.models.HttpMethod;
import org.springframework.http.HttpMethod;




@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ItemRestControllerTest {
	
	private static final String BASE_PATH = "/api/v1/items";
	
	
	@Autowired
	private TestRestTemplate restTemplate;

	
	@Autowired
	private ItemRepository repo;

	@BeforeEach
	void setupRepo() {
		repo.deleteAll();
	}
	


	@Test
	void test() {
		System.out.println("HUHU: " + restTemplate);
		//fail("Not yet implemented");
	}
	

	
	
	
	


	
	
	
	
	
	
	
	

	@Test
	void shouldBeAbleToUploadAnItem() {
		// Given  |  Arrange
		Item lawnMover = buildLawnMower();
		// When  |  Act
		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_PATH,  lawnMover, Item.class);
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getId()).isNotNull();        
	}
	
	
	
	@Test
	void shouldFindOneItem() {		
		// Given  |  Arrange
		Item lawnMower = giveAnInsertedItem().getBody();
		// When  |  Act
		ResponseEntity<Item> response = restTemplate.getForEntity(BASE_PATH + "/" + lawnMower.getId(), Item.class);
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnMower);
	}
	
	

	@Test
	void shouldReadAllItems() {
		// Given  |  Arrange
		Item lawnMower = giveAnInsertedItem().getBody();
		Item lawnTrimmer = giveAnotherInsertedItem().getBody();
		// When  |  Act
		ResponseEntity<Item[]> response = restTemplate.getForEntity(BASE_PATH, Item[].class);  // Achtung: Item ist ein Array - also auch hinten die Item[].class !!!
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).hasSize(2);
		assertThat(response.getBody()[0]).isEqualToComparingFieldByField(lawnMower);
		assertThat(response.getBody()[1]).isEqualToComparingFieldByField(lawnTrimmer);
	}
	
	
	
	@Test
	void shouldFindNoItemForUnknownId() throws URISyntaxException {
		// Given  |  Arrange
		// When  |  Act
		ResponseEntity<Item> response = restTemplate.getForEntity(BASE_PATH + "/4711", Item.class);
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	
	@Test
	void shouldBeAbleToDeleteAnItem() throws URISyntaxException {
		// Given  |  Arrange
		Item lawnMower = giveAnInsertedItem().getBody();
		// When  |  Act
		URI uri = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + lawnMower.getId());
		RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.DELETE, uri);  // RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.DELETE, new URI(restTemplate.getRootUri() + BASE_PATH + "/" +lawnMower.getId()));
		ResponseEntity<String> deleteResponse = restTemplate.exchange(requestEntity, String.class);
		// Then | Assert
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
		ResponseEntity<Item> getResponse = restTemplate.getForEntity(uri, Item.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
		
	
	
	@Test
	void shouldNotBeAbleToDeleteAnItemWithUnknownId() throws URISyntaxException {
		// Given  |  Arrange
		Long unknownId = 479947l;
		// When  |  Act
		URI uri = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + unknownId);
		RequestEntity<String> requestEntity = new RequestEntity<>(HttpMethod.DELETE, uri);
		ResponseEntity<String> deleteResponse = restTemplate.exchange(requestEntity, String.class);
		// Then | Assert
		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	
	
	// TODO / not clear ...!!!
	
	// 1) shouldBeAbleToDeleteAnItem  ===================================>> NOT completelly green !!!
	// 2) shouldBeAbleToReplaceAnItem
	// 3) shouldNotBeAbleToReplaceAnItemWithUnknownId
	
	

	
	

	
	private ResponseEntity<Item> giveAnInsertedItem() {
		Item item = buildLawnMower();
		return restTemplate.postForEntity(BASE_PATH, item, Item.class);
	}

	
	
	private ResponseEntity<Item> giveAnotherInsertedItem() {
		Item item = buildLawnTrimmer();
		return restTemplate.postForEntity(BASE_PATH, item, Item.class);
	}
	
	
	
	private Item buildLawnMower() {
		Item item5 = new Item("Lawn mower", 1, "2019-05-01", "Basement", "Not any comment...");
		return item5;
	}

	
	
	private Item buildLawnTrimmer() {
		Item item9 = Item.builder().name("Lawn trimmer").amount(1).lastUsed(LocalDate.parse("2018-05-01"))  // changed: Date.valueOf("2018-05-01")
				.location("Basement").build();
		return item9;
	}
	
	
	
	// Given  |  Arrange
	
	// When  |  Act
			
	// Then | Assert
	
	
	
	
	
	
	
	
	/* ====================> ALT / Beispiel - to delete !!!!!!! <===================================
	
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
	} */
				
}

	

