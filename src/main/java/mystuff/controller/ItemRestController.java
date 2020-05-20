package mystuff.controller;

import java.util.Date;
import java.util.List;

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

import mystuff.entities.Item;
import mystuff.repository.ItemRepository;

@RestController
@RequestMapping("/api/v1/")
public class ItemRestController {
	
	private final ItemRepository repository;
	
	public ItemRestController(ItemRepository itemRepository) {
		this.repository = itemRepository;
	}
	
	
	@GetMapping("items")
	public List<Item> getAll(){
		return repository.findAll();
	}
	
	@GetMapping("items/{id}")
	public Item get(@PathVariable Long id) {
		return repository.findById(id);
		//return ((Object) repository.findById(id)).orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	
	@PostMapping("items")
	@ResponseStatus(HttpStatus.CREATED)
	public Item newItem(@RequestBody Item newItem) {
		return repository.save(newItem);
	}

	/*
	@PutMapping("items/{id}")
	public Item replace(@RequestBody Item newItem, @PathVariable Long id) {
		return repository.findById(id).map(item -> {
			item.setItemName(newItem.getName());
			item.setItemAmount(newItem.getAmount());
			item.setItemLocation(newItem.getLocation());
			item.setItemDescription(newItem.getDescription());
			item.setItemDate(newItem.getItemDate());
			return repository.save(note);
		});
		// }).orElseThrow(() -> new ResourceNotFoundException(id));
		
	}
	
	
	@DeleteMapping("items/{id}")
	public void delete(@PathVariable Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	*/
	
	
}






