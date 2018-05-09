package microbenchmark.longsummer;

import org.junit.Before;
import org.junit.Test;

import microbenchmark.longsummer.LongSummer;

public class LongSummerBenchmarkTest {

	private static final long NUMBER_OF_ELEMENTS = 1000000000000L;
	private LongSummer summer;

	@Before
	public void setup() {
		summer = new LongSummer();
	}

	@Test
	public void shouldSumNFirstElements() {
		testSummer(true);
		testSummer(false);
	}

	private void testSummer(boolean isWarmup) {
		long begin = System.currentTimeMillis();
		long sum = summer.sum(NUMBER_OF_ELEMENTS);
		if (!isWarmup) {
			long end = System.currentTimeMillis();
			System.out.println("Elapsed time: " + (end - begin));
			System.out.println("Sum: " + sum);
		}

	}

}
