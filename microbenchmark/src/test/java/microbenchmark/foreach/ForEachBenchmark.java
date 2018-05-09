package microbenchmark.foreach;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import microbenchmark.foreach.model.Person;

public class ForEachBenchmark {

	private static final long QUANTITY_OF_PEOPLE = 50_000_000L;
	private static final int REMAINDER_OPERAND = 3;

	private volatile List<Person> result;
	private List<Person> people;

	@Before
	public void setup() {
		result = new ArrayList<>();
		people = extractPeople();
	}

	@Test
	public void testForEachEnhanced() {
		warmUpAndTest(new ForEachEnhanced());
	}

	@Test
	public void testForEachStreamFilter() {
		warmUpAndTest(new ForEachStreamFilter());
	}

	@Test
	public void testForEachStream() {
		warmUpAndTest(new ForEachStream());
	}

	@Test
	public void testForEachIterable() {
		warmUpAndTest(new ForEachIterable());
	}

	private void warmUpAndTest(ForEach forEach) {
		warmUpForEach(people, forEach);
		testForEach(people, forEach);
		assertTrue(!this.result.isEmpty());
	}

	private List<Person> extractPeople() {
		List<Person> people = new ArrayList<Person>();
		for (int i = 0; i < QUANTITY_OF_PEOPLE; i++) {
			Person person = createPersonBasedOnRemainderOf(i);
			people.add(person);
		}
		return people;
	}

	private Person createPersonBasedOnRemainderOf(int i) {
		Person person = null;
		if (i % REMAINDER_OPERAND == 0) {
			person = Person.ofFirstNameAndLastName("firstName", "lastName");
		} else {
			person = Person.ofFirstName("firstName");
		}
		return person;
	}

	private void warmUpForEach(List<Person> people, ForEach forEach) {
		this.result = forEach.iterate(people);
	}

	private void testForEach(List<Person> people, ForEach forEach) {
		long before = System.currentTimeMillis();
		this.result = forEach.iterate(people);
		long after = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (after - before));
	}

}
