package maaartinus.currency;

import static com.google.common.base.Preconditions.checkArgument;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import junit.framework.TestCase;

public class ReplacingBigDecimal extends TestCase {
	public static class Exact {
		/**
		 * Given an input which has three decimal places,
		 * round it to two decimal places using HALF_EVEN.
		 */
		static BigDecimal roundToTwoPlaces(BigDecimal n) {
			// To make sure, that the input has three decimal places.
			checkArgument(n.scale() == 3);
			final BigDecimal result = n.setScale(2, RoundingMode.HALF_EVEN);
			return result;
		}
	}

	public static class Naive {
		static double roundToTwoPlaces(double n) {
			return Math.round(100.0 * n) / 100.0;
		}
	}

	public static class Correct {
		static double roundToTwoPlaces(double n) {
			final long m = Math.round(1000.0 * n);
			final double x = 0.1 * m;
			final long r = (long) Math.rint(x);
			return r / 100.0;
		}
	}

	public void testExact() {
		final BigDecimal n = new BigDecimal("0.615");
		final BigDecimal expected = new BigDecimal("0.62");
		final BigDecimal actual = Exact.roundToTwoPlaces(n);
		assertEquals(expected, actual);
	}

	// Failures are expected!
	public void testNaive() {
		for (int i=0; i<10000; ++i) {
			final BigDecimal n = BigDecimal.valueOf(5 + 10*i, 3);
			final double expected = Exact.roundToTwoPlaces(n).doubleValue();
			final double actual = Naive.roundToTwoPlaces(n.doubleValue());
			assertEquals(String.valueOf(expected), String.valueOf(actual));
		}
	}

	public void testCorrect() {
		for (int i=0; i<10000; ++i) {
			final BigDecimal n = BigDecimal.valueOf(5 + 10*i, 3);
			final double expected = Exact.roundToTwoPlaces(n).doubleValue();
			final double actual = Correct.roundToTwoPlaces(n.doubleValue());
			assertEquals(String.valueOf(expected), String.valueOf(actual));
		}
	}
}
