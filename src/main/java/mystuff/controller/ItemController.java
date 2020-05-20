package mystuff.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import mystuff.repository.ItemRepository;

@Controller
public class ItemController {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@GetMapping("/api/v1/items")
	public String index(Model model) {
		model.addAllAttributes(itemRepository.findAll());
		//model.addAllAttributes("items", itemRepository.findAll());
		return "items";
	}
	

}
