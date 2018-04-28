package microbenchmark;

public class FibonacciCalculator {

	public double calculate(int n) {
		if (n < 0)
			throw new IllegalArgumentException("Must be > 0");
		if (n == 0)
			return 0d;
		if (n == 1)
			return 1d;
		double d = calculate(n - 2) + calculate(n - 1);
		if (Double.isInfinite(d))
			throw new ArithmeticException("Overflow");
		return d;
	}

}
