package br.com.dixy.performance.memleak.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dixy.performance.memleak.factory.ItemFactory;
import br.com.dixy.performance.memleak.model.Item;

@Service
public class ItemService {

	private final ConcurrentMap<Integer, List<Item>> itemsCache = new ConcurrentHashMap<>();

	@Autowired
	private ItemFactory itemFactory;

	public List<Item> findPagesOfItems(Integer pages, Integer itemsPerPage) {
		createPagesOfItems(pages, itemsPerPage);
		return itemsCache.values().stream().limit(pages).flatMap(itemsPage -> itemsPage.stream()).collect(
				Collectors.toList());
	}

	private void createPagesOfItems(Integer pages, Integer itemsPerPage) {
		System.out.printf("Creating %d page(s) with %d items each...\n", pages, itemsPerPage);
		for (int page = 1; page <= pages; page++) {
			createPageOfItems(itemsPerPage, page);
		}
	}

	private void createPageOfItems(Integer itemsPerPage, int page) {
		System.out.printf("Creating page %d of items...\n", page);
		List<Item> items = itemFactory.createItems(itemsPerPage);
		cachePage(page, items);
		System.out.printf("Page %d created succesfully\n", page);
	}

	private void cachePage(int page, List<Item> items) {
		itemsCache.computeIfAbsent(page, i -> new ArrayList<>());
		itemsCache.get(page).addAll(items);
		System.out.printf("Page %d has been cached. (Items: %s)\n", page,
				items.stream().map(Item::getId).collect(Collectors.toList()));
	}

}
