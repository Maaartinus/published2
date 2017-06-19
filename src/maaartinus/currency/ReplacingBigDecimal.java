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
			return n.round(new MathContext(2, RoundingMode.HALF_EVEN));
		}
	}

	public static class Naive {
		static double roundToTwoPlaces(double n) {
			return Math.round(100.0 * n) / 100.0;
		}
	}

	public static class Correct {
		static double roundToTwoPlaces(double n) {
			return Math.round(100.0 * n) / 100.0;
		}
	}

	public void testExact() {
		final BigDecimal n = new BigDecimal("0.615");
		final BigDecimal expected = new BigDecimal("0.62");
		final BigDecimal actual = Exact.roundToTwoPlaces(n);
		assertEquals(expected, actual);
	}

	// Expected to fail, but it doesn't.
	public void testNaive() {
		final double n = 0.615;
		final double expected = 0.62;
		final double actual = Naive.roundToTwoPlaces(n);
		assertEquals(String.valueOf(expected), String.valueOf(actual));
	}

	public void testCorrect() {
		final double n = 0.615;
		final double expected = 0.62;
		final double actual = Correct.roundToTwoPlaces(n);
		assertEquals(String.valueOf(expected), String.valueOf(actual));
	}
}
