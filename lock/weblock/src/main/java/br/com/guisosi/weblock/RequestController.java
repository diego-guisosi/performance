package br.com.guisosi.weblock;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.guisosi.weblock.domain.Message;
import br.com.guisosi.weblock.domain.MessageUnlock;
import br.com.guisosi.weblock.domain.MessageLock;
import br.com.guisosi.weblock.domain.MessageOnly;

@RestController
public class RequestController {

	private static final long ELAPSED_TIME_THRESHOLD_IN_MILLIS = 10000;
	private final Map<String, Message> messageSourceLock = new ConcurrentHashMap<>();
	private final Map<String, Message> messageSourceUnlock = new ConcurrentHashMap<>();
	private final Map<String, Message> messageSourceOnly = new ConcurrentHashMap<>();

	@RequestMapping(path = "/messageLock/{client}", method = GET)
	public String getMessageLock(@PathVariable(name = "client") String client) throws InterruptedException {
		Message message = findMessageWithLock(client);
		return message.getText();
	}

	private MessageLock findMessageWithLock(String client) throws InterruptedException {
		long currentTimeInMillis = System.currentTimeMillis();
		MessageLock message = (MessageLock) messageSourceLock.computeIfAbsent(client,
				key -> new MessageLock(client, currentTimeInMillis));
		synchronized (message) {
			long elapsedTime = currentTimeInMillis - message.getLastUpdatedInMillis();
			if (elapsedTime > ELAPSED_TIME_THRESHOLD_IN_MILLIS) {
				message.updateText();
			}
		}
		return message;
	}

	@RequestMapping(path = "/messageUnlock/{client}", method = GET)
	public String getMessageUnlock(@PathVariable(name = "client") String client) throws InterruptedException {
		Message message = findMessageWithoutLock(client);
		return message.getText();
	}

	private MessageUnlock findMessageWithoutLock(String client) throws InterruptedException {
		long currentTimeInMillis = System.currentTimeMillis();
		MessageUnlock message = (MessageUnlock) messageSourceUnlock.computeIfAbsent(client,
				key -> new MessageUnlock(client, currentTimeInMillis));

		long elapsedTime = currentTimeInMillis - message.getLastUpdatedInMillis();
		if (elapsedTime > ELAPSED_TIME_THRESHOLD_IN_MILLIS) {
			synchronized (message) {
				if (elapsedTime > ELAPSED_TIME_THRESHOLD_IN_MILLIS) {
					message.updateText();
				}
			}
		}
		return message;
	}

	@RequestMapping(path = "/messageOnly/{client}", method = GET)
	public String getMessageOnly(@PathVariable(name = "client") String client) throws InterruptedException {
		Message message = findMessageOnly(client);
		return message.getText();
	}

	private MessageOnly findMessageOnly(String client) throws InterruptedException {
		long currentTimeInMillis = System.currentTimeMillis();
		MessageOnly message = (MessageOnly) messageSourceOnly.computeIfAbsent(client,
				key -> new MessageOnly(client, currentTimeInMillis));

		long elapsedTime = currentTimeInMillis - message.getLastUpdatedInMillis();
		if (elapsedTime > ELAPSED_TIME_THRESHOLD_IN_MILLIS) {
			message.updateText();
		}
		return message;
	}

}
