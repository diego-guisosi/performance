package microbenchmark.longsummer;

public class LongSummer {

	private long result;

	public long sum(long numberOfElements) {
		for (int i = 0; i < numberOfElements; i++) {
			result += i;
		}
		return result;
	}

}
