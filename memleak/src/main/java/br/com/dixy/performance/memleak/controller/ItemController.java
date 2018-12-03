package br.com.dixy.performance.memleak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dixy.performance.memleak.model.Item;
import br.com.dixy.performance.memleak.service.ItemService;

@RestController
@RequestMapping(value = "/item")
public class ItemController {

	@Autowired
	private ItemService memleakService;

	@GetMapping
	public List<Item> getLeak(@RequestParam(name = "pages") int pages,
			@RequestParam(name = "itemsPerPage") int itemsPerPage) {
		return memleakService.findPagesOfItems(pages, itemsPerPage);
	}

}
