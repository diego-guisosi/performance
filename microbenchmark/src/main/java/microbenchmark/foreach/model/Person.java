package microbenchmark.foreach.model;

public class Person {

	private String firstName;
	private String lastName;

	private Person(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	private Person(String firstName) {
		this(firstName, null);
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public static Person ofFirstNameAndLastName(String firstName, String lastName) {
		return new Person(firstName, lastName);
	}

	public static Person ofFirstName(String firstName) {
		return new Person(firstName);
	}

}
