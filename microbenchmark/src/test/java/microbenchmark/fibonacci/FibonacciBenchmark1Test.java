package microbenchmark.fibonacci;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import microbenchmark.fibonacci.FibonacciCalculator;

/*
 * Microbenchmarks must use their results. If the code under test only writes
 * the returned value, the compiler can decide not to execute the iteration,
 * since it detects that the written value is never read
 */
public class FibonacciBenchmark1Test {

	private static final int NUMBER_OF_LOOPS = 1;
	private static final int MAXIMUM_NUMBER_TO_CALCULATE = 50;

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
	public void shouldNotCalculateWhenCalculatorResultDoesNotUpdateObjectState() {
		testCalculate();
	}

	private void testCalculate() {
		long then = System.nanoTime();
		for (int i = 0; i < NUMBER_OF_LOOPS; i++) {
			calculator.calculate(input[i]);
		}
		long now = System.nanoTime();
		System.out.println("Elapsed time: " + (now - then));
		assertTrue((now - then) > 0);
	}

}
