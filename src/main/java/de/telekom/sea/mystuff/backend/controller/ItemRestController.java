package de.telekom.sea.mystuff.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.telekom.sea.mystuff.backend.entities.Item;
import de.telekom.sea.mystuff.backend.repository.ItemRepository;

@RestController
@RequestMapping("/api/v1/")
public class ItemRestController {

	private final ItemRepository repository;

	@Autowired
	public ItemRestController(ItemRepository itemRepository) {
		this.repository = itemRepository;
	}

	/*
	 * @GetMapping("huhu") public String huhu() { return "huhu"; }
	 */

	@GetMapping("items")
	public List<Item> getAll() {
		return repository.findAll();
	}

	// Tipps: Ordnen: CTRL + Shift + L

	// Optional: --> Wrapper-Objekt, das ein Item enthalten kann - oder eben auch
	// nicht.

	// Links zum googln:
	// (Optional): --->
	// https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html
	// (Optional-Supplier): --->
	// https://docs.oracle.com/javase/8/docs/api/java/util/Optional.html#orElseThrow-java.util.function.Supplier-

	@GetMapping("items/{id}")
	public Item get(@PathVariable Long id) {
		return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		/*
		 * --> Dieser Teil wurde durch die Lambda version ersetzt!!! //
		 * =============================================================================
		 * ============================
		 * 
		 * Optional<Item> ergebnis = repository.findById(id); return
		 * ergebnis.orElseThrow(new Supplier<ResourceNotFoundException>() {
		 * 
		 * @Override public ResourceNotFoundException get() { return new
		 * ResourceNotFoundException(id); } });
		 */

		/*
		 * ---> alte Version !!! //
		 * =============================================================================
		 * ============================
		 * 
		 * 
		 * //return repository.findById(id); Optional<Item> ergebnis =
		 * repository.findById(id); // weil Objekt: Es ist nie == null --> darum
		 * "isPresent". if (ergebnis.isPresent()) { return ergebnis.get(); }
		 * 
		 * throw new ResourceNotFoundException(id); // throw springt auch aus der
		 * Methode !
		 * 
		 */
	}

	@PostMapping("items")
	@ResponseStatus(HttpStatus.CREATED)
	public Item newItem(@RequestBody Item newItem) {
		return repository.save(newItem);
	}

	@PutMapping("items/{id}")
	public Item replace(@RequestBody Item newItem, @PathVariable Long id) {
		return (Item) repository.findById(id).map(item -> {
			item.setName(newItem.getName());
			item.setAmount(newItem.getAmount());
			item.setLocation(newItem.getLocation());
			item.setDescription(newItem.getDescription());
			item.setLastUsed(newItem.getLastUsed());
			return repository.save(item);

		}).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@DeleteMapping("items/{id}")
	public void delete(@PathVariable Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

}
