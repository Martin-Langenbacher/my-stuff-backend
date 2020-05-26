package de.telekom.sea.mystuff.backend.controller;

// import static --> einzelne Methoden können importiert werden (nicht Klassen); ansonsten müsste man die Klasse davor schreiben...
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
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
	void testHuHu() {
		System.out.println("HUHU: " + restTemplate);
		//fail("Not yet implemented");
	}
	
	

	
	@Test
	void shouldBeAbleToUploadAnItem() throws URISyntaxException {
		// Given  |  Arrange
		Item lawnMover = buildLawnMower();
		// When  |  Act
		ResponseEntity<Item> response = restTemplate.postForEntity(BASE_PATH,  lawnMover, Item.class);
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(response.getBody().getId()).isNotNull();  
		
		lawnMover.setId(response.getBody().getId());
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnMover);
		
		assertThat(response.getBody()).isEqualToComparingOnlyGivenFields(lawnMover, "name", "description", "location", "amount", "lastUsed");		
	}
	
	
	
	
	@Test
	void shouldFindOneItem() throws URISyntaxException {		
		// Given  |  Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		// When  |  Act
		ResponseEntity<Item> response = restTemplate.getForEntity(BASE_PATH + "/" + lawnMower.getId(), Item.class);
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnMower);
	}
	
	
	

	@Test
	void shouldReadAllItems() throws URISyntaxException {
		// Given  |  Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
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
		Item lawnMower = givenAnInsertedItem().getBody();
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
	
	
	
	
	/* Baeldung-Tipps:
	https://www.baeldung.com/spring-resttemplate-post-json
	*/

	
	@Test
	void shouldBeAbleToReplaceAnItem() throws URISyntaxException {
		// Given  |  Arrange
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> START <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		Item insertedItem = givenAnInsertedItem().getBody(); // LawnMower
		Item newItem = buildLawnTrimmer();  // LawnTrimmer
		// When  |  Act
		URI uri = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + insertedItem.getId());
		
		
		// Was passiert bei RequestEntity? 
		// ==> Extension of HttpEntity that adds a method and uri. Used in RestTemplate and @Controller methods. In RestTemplate, this class is used as parameter in exchange():   
		// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/RequestEntity.html
		
		
		RequestEntity<Item> requestEntity = new RequestEntity<>(newItem, HttpMethod.PUT, uri);     // RequestEntity<Item> requestEntity = new RequestEntity<>(newItem, HttpMethod.PUT, new URI(restTemplate.getRootUri() + BASE_PATH + "/" + insertedItem.getId()));
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Uri ist der Pfad: " +uri.toString());
		System.out.println("Inserted-item-getID  --> " +insertedItem.getId());
		System.out.println("new-item-getID --> " +newItem.getId());
		System.out.println("requestEntity-toString: --> " +requestEntity.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Was ist das requestEntity? (getName) ===>" + requestEntity.getBody().getName());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> requestEntity? ID ===>" + requestEntity.getBody().getId());
		
		
		
		// Was passiert bei ResponseEntity? 
		// ==> Extension of HttpEntity that adds a HttpStatus status code. Used in RestTemplate as well @Controller methods. In RestTemplate, this class is returned by getForEntity() and exchange(): 
		// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/ResponseEntity.html
		
		ResponseEntity<Item> putResponse = restTemplate.exchange(requestEntity, Item.class);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> putResponse: " +putResponse.getBody().getName());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> putResponse: " +putResponse.getBody().getId());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> restTemplate: " +restTemplate.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> requestEntity: " +requestEntity.getBody().getName());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> requestEntity: " +requestEntity.getBody().getId());
		

		
		// Then | Assert
		assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		// schaue nach, ob das Item geändert wurde:
		// setze generierte ID im neuen Item (lawnTrimmer): 
		newItem.setId(insertedItem.getId());
		assertThat(putResponse.getBody()).isEqualToComparingFieldByField(newItem);
		
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> END <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		
	}
	
	
	
	// Wie hat es Arne gemacht ???
	@Test
	void shouldBeAbleToReplaceAnItem2() throws URISyntaxException {
		// Given  |  Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		Item lawnTrimmer = buildLawnTrimmer();
		// When  |  Act
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + lawnMower.getId());
		HttpEntity<Item> requestUpdate = new HttpEntity<Item>(lawnTrimmer, headers);
		ResponseEntity<Item> response = restTemplate.exchange(uri, HttpMethod.PUT, requestUpdate, Item.class);
		lawnTrimmer.setId(lawnMower.getId());		
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnTrimmer);
	}
	
	
	
	
	
	// Element soll geändert werden - aber es gibt dieses Element NICHT; darum muss es abgelehtn werden!!!
	@Test
	void shouldNotBeAbleToReplaceAnItemWithUnknownId() throws URISyntaxException {
		// Given  |  Arrange
		// Item insertedItem ==> Not needed! 
		Item newItem = buildLawnTrimmer();  // LawnTrimmer
		// When  |  Act	
		RequestEntity<Item> requestEntity = new RequestEntity<>(newItem, HttpMethod.PUT,
				new URI(restTemplate.getRootUri() + BASE_PATH + "/12345"));
		ResponseEntity<Item> putResponse = restTemplate.exchange(requestEntity, Item.class);
		// Then | Assert
		assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}
	
	
	
	
	@Test
	void shouldNotBeAbleToReplaceAnItemWithUnknownId2() throws URISyntaxException {
		// Given  |  Arrange
		Item lawnMower = givenAnInsertedItem().getBody();
		Item lawnTrimmer = buildLawnTrimmer();
		// When  |  Act	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		URI uri = new URI(restTemplate.getRootUri() + BASE_PATH + "/" + lawnMower.getId());
		HttpEntity<Item> requestUpdate = new HttpEntity<Item>(lawnTrimmer, headers);
		ResponseEntity<Item> response = restTemplate.exchange(uri, HttpMethod.PUT, requestUpdate, Item.class);
		lawnTrimmer.setId(lawnMower.getId());
		// Then | Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);		
		assertThat(response.getBody()).isEqualToComparingFieldByField(lawnTrimmer);
	}
	
	
	
	
	
	
	private ResponseEntity<Item> givenAnInsertedItem() {
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
}



	// Given  |  Arrange  |  Aufbauen
	
	// When  |  Act  |  Ausführen
			
	// Then | Assert  |  Ergebnis prüfen...
	
//====================================================================================================================================================	
	
	// Erklärungen: Was passiert hier: "private TestRestTemplate restTemplate;" ?
	// ==> RestTemplate: Methoden werden aufgerufen --> Rest-Client
		
//====================================================================================================================================================						

	/* Wie bekomme ich die komplette URL von @SpringBootTest ? 
	
	--> get complete URL ==> getRootURI-Aufruf...              */
	
//====================================================================================================================================================	
	
	// HttpMethod: --> Man kann es im Browser gut sehen...
	// 
	// curl, curl -v, curl -vvv, 
	// Server starten; dann swagger... , dann DELETE, dann Tryout, id=1, dann Curl schauen... curl-raus-kopieren...
	// 
	// curl -vvv -X DELETE ... 
	// mit netcat (nc) ausprobieren...
	


	

