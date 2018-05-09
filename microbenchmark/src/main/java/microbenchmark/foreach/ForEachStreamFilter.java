package microbenchmark.foreach;

import java.util.List;

import microbenchmark.foreach.model.Person;

public class ForEachStreamFilter implements ForEach {

	@Override
	public List<Person> iterate(List<Person> people) {
		people.stream().filter(person -> !isNull(person.getLastName()))
				.forEach(person -> person.setLastName(person.getLastName().toUpperCase()));
		return people;
	}

}
