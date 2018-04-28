package benchmark.micro;

public class FibonacciTest2 {

	// Declaring l as a volatile instance variable, we guarantee that fibImpl1
	// return value will be read
	private volatile double l;

	/*
	 * Microbenchmarks must not include extraneous operations. Since the element
	 * passed to fibImpl1 is fixed, this can be detected by the compiler so that the
	 * loop executes only once. This can be bypassed, generating a random value each
	 * time the iteration occurs. But the time to generate the random value would be
	 * added to the execution time of fibImpl1. This would give us an incorrect time
	 * 
	 * Besides that, Microbenchmarks must measure the correct input. If the random
	 * approach does not limit the generated values, fibImpl1 could end up receiving
	 * numbers that do not represent how the code would really be used (e.g:
	 * negative numbers or numbers greater than 1476, which is the highest fibonacci
	 * number that can be represented by double
	 */
	public void doTest() {

		long then = System.currentTimeMillis();
		for (int i = 0; i < 2; i++) {
			l = fibImpl1(50);
		}
		long now = System.currentTimeMillis();
		System.out.println("Elapsed time: " + (now - then));
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
		FibonacciTest2 test = new FibonacciTest2();
		test.doTest();
	}

}
