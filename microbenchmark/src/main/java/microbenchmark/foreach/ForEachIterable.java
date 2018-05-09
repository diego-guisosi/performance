package microbenchmark.foreach;

import java.util.List;

import microbenchmark.foreach.model.Person;

public class ForEachIterable implements ForEach {

	@Override
	public List<Person> iterate(List<Person> people) {
		people.forEach(person -> {
			if (!isNull(person.getLastName())) {
				person.setLastName(person.getLastName().toUpperCase());
			}
		});
		return people;
	}

}
