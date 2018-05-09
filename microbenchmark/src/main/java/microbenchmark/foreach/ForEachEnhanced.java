package microbenchmark.foreach;

import java.util.List;

import microbenchmark.foreach.model.Person;

public class ForEachEnhanced implements ForEach {

	@Override
	public List<Person> iterate(List<Person> people) {
		for (Person person : people) {
			if (!isNull(person.getLastName())) {
				person.setLastName(person.getLastName().toUpperCase());
			}
		}
		return people;
	}

}
