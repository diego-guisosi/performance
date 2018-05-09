package microbenchmark.fibonacci;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import microbenchmark.fibonacci.FibonacciCalculator;

/*
 * Microbenchmarks must include a warm-up period, since one of the performance
 * characteristics of Java is that code performs better the more it is executed.
 * Warm-up period will give the compiler a chance to produce optimal code.
 * 
 * If a warm-up period is not provided, the microbenchmark is measuring the
 * performance of compilation instead of the code it is trying to measure
 */
public class FibonacciBenchmark3Test {

	private static final int NUMBER_OF_LOOPS = 1;
	private static final int MAXIMUM_NUMBER_TO_CALCULATE = 50;
	private volatile double fibonacci;
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
	public void shouldPresentCorrectElapsedTimeWhenCalculateExecutes() {
		testCalculate(true);
		testCalculate(false);
	}

	private void testCalculate(boolean isWarmup) {
		long then = System.nanoTime();
		for (int i = 0; i < NUMBER_OF_LOOPS; i++) {
			fibonacci = calculator.calculate(input[i]);
		}
		if (!isWarmup) {
			long now = System.nanoTime();
			System.out.println("Elapsed time: " + (now - then));
			System.out.println(fibonacci);
			assertTrue((now - then) > 0);
		}
	}

}
