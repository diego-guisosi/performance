package benchmark.micro;

import java.util.Random;

/*
 * Microbenchmarks must include a warm-up period, since one of the performance
 * characteristics of Java is that code performs better the more it is executed.
 * Warm-up period will give the compiler a chance to produce optimal code.
 * 
 * If a warm-up period is not provided, the microbenchmark is measuring the
 * performance of compilation instead of the code it is trying to measure
 */
public class FibonacciTest3 {

	private volatile double fibonacci;
	private int numberOfLoops;
	private int[] input;

	private FibonacciTest3(int n) {
		numberOfLoops = n;
		input = new int[numberOfLoops];
		Random random = new Random();
		for (int i = 0; i < numberOfLoops; i++) {
			input[i] = random.nextInt(100);
		}
	}

	public void doTest(boolean isWarmup) {
		long then = System.currentTimeMillis();
		for (int i = 0; i < numberOfLoops; i++) {
			fibonacci = fibImpl1(input[i]);
		}
		if (!isWarmup) {
			long now = System.currentTimeMillis();
			System.out.println("Elapsed time: " + (now - then));
		}
	}

	private double fibImpl1(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Must be > 0");
		if (n == 0)
			return 0d;
		if (n == 1)
			return 1d;
		double d = fibImpl1(n - 2) + fibImpl1(n - 1);
		if (Double.isInfinite(d))
			throw new ArithmeticException("Overflow");
		return d;
	}

	public static void main(String[] args) {
		FibonacciTest3 test = new FibonacciTest3(1);
		test.doTest(true);
		test.doTest(false);
	}

}
