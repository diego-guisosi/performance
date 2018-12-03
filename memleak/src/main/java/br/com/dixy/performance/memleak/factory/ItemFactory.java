package br.com.dixy.performance.memleak.factory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import br.com.dixy.performance.memleak.model.Item;

@Component
public class ItemFactory {

	private final AtomicLong itemIdGenerator = new AtomicLong();

	public List<Item> createItems(Integer numberOfItems) {
		List<Item> items = new ArrayList<>();
		for (int i = 0; i < numberOfItems; i++) {
			Item item = createItem();
			items.add(item);
			System.out.printf("Item %d created at %d\n", item.getId(), item.getInstant().getEpochSecond());
		}
		return items;
	}

	private Item createItem() {
		return new Item(itemIdGenerator.incrementAndGet());
	}

}
