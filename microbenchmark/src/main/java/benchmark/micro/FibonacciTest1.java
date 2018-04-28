package benchmark.micro;

public class FibonacciTest1 {

	/*
	 * Microbenchmarks must use their results. If the code under test only writes
	 * the returned value, the compiler can decide not to execute the iteration,
	 * since it detects that the written value is never read
	 */
	public void doTest() {

		long then = System.currentTimeMillis();
		for (int i = 0; i < 1; i++) {
			fibImpl1(50);
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
		FibonacciTest1 test = new FibonacciTest1();
		test.doTest();
	}

}
