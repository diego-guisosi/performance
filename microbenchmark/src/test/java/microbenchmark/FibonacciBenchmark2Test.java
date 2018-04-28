package microbenchmark;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

/*
 * Microbenchmarks must not include extraneous operations. Since the element
 * passed to calculator is fixed, this can be detected by the compiler so that the
 * loop executes only once. This can be bypassed, generating a random value each
 * time the iteration occurs. But the time to generate the random value would be
 * added to the execution time of calculator. This would give us an incorrect time
 * 
 * Besides that, Microbenchmarks must measure the correct input. If the random
 * approach does not limit the generated values, calculator could end up receiving
 * numbers that do not represent how the code would really be used (e.g:
 * negative numbers or numbers greater than 1476, which is the highest fibonacci
 * number that can be represented by double)
 */
public class FibonacciBenchmark2Test {

	private static final int NUMBER_OF_LOOPS = 1;
	private static final int MAXIMUM_NUMBER_TO_CALCULATE = 50;

	// Declaring result as a volatile instance variable, we guarantee that
	// calculator
	// return value will be read
	private volatile double result;
	private int[] input;
	private FibonacciCalculator calculator;

	@Before
	public void setup() {
		input = new int[NUMBER_OF_LOOPS];
		Random random = new Random();
		for (int i = 0; i < NUMBER_OF_LOOPS; i++) {
			input[i] = random.nextInt(MAXIMUM_NUMBER_TO_CALCULATE);
		}
		calculator = new FibonacciCalculator();
	}

	@Test
	public void shouldCalculateWhenCalculatorResultUpdateObjectState() {
		testCalculate();
	}

	private void testCalculate() {
		long then = System.nanoTime();
		for (int i = 0; i < NUMBER_OF_LOOPS; i++) {
			result = calculator.calculate(input[i]);
		}
		long now = System.nanoTime();
		System.out.println("Elapsed time: " + (now - then));
		assertTrue((now - then) > 0);
	}

}
