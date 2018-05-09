package microbenchmark.foreach;

import java.util.List;

import microbenchmark.foreach.model.Person;

public interface ForEach {

	List<Person> iterate(List<Person> people);

	default boolean isNull(String lastName) {
		return lastName == null;
	}

}
