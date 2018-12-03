package br.com.dixy.performance.memleak.model;

import java.time.Instant;

public class Item {

	private Long id;
	private String name;
	private Instant instant;

	public Item(Long id) {
		this.id = id;
		this.name = "Item-" + id;
		this.instant = Instant.now();
	}

	public Long getId() {
		return id;
	}

	public Instant getInstant() {
		return instant;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setInstant(Instant instant) {
		this.instant = instant;
	}

	public void setName(String name) {
		this.name = name;
	}

}
