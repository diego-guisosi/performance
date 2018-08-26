package br.com.guisosi.weblock.domain;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class MessageLock implements Message {

	private final AtomicInteger counter = new AtomicInteger();

	private final String client;
	private String text;
	private long lastUpdatedInMillis;

	public MessageLock(String client, Long lastUpdated) {
		this.client = client;
		this.text = String.format("%s:%d", client, counter.get());
		this.lastUpdatedInMillis = lastUpdated;
	}

	public String getClient() {
		return client;
	}

	@Override
	public String getText() {
		return text;
	}

	public long getLastUpdatedInMillis() {
		return lastUpdatedInMillis;
	}

	public void setLastUpdated(long lastUpdated) {
		this.lastUpdatedInMillis = lastUpdated;
	}

	public void updateText() throws InterruptedException {
		this.text = String.format("%s:%d", client, counter.incrementAndGet());
		lastUpdatedInMillis = System.currentTimeMillis();
		TimeUnit.MILLISECONDS.sleep(500);
	}

}
