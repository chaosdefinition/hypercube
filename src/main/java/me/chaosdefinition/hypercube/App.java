package me.chaosdefinition.hypercube;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chaos Shen
 *
 */
public class App {

	private static int parseDimension(final String[] args) {
		if (args.length < 1) {
			throw new HypercubeException("Too few arguments!");
		}
		try {
			return Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new HypercubeException("Failed to parse dimension!", e);
		}
	}

	private static List<Integer> parseInitial(final String[] args, int index) {
		List<Integer> initial = null;
		if (args.length > index) {
			initial = new ArrayList<>();
			String[] elements = args[index].split(","); /* comma seperated */
			try {
				for (String element : elements) {
					initial.add(Integer.parseInt(element));
				}
			} catch (NumberFormatException e) {
				throw new HypercubeException("Failed to parse initial!", e);
			}
		}
		return initial;
	}

	private static Integer parseOptionalInteger(final String[] args, int index) {
		Integer val = null;
		if (args.length > index) {
			try {
				val = Integer.parseInt(args[index]);
			} catch (NumberFormatException e) {
				throw new HypercubeException("Failed to parse args[" + index + "]!", e);
			}
		}
		return val;
	}

	public static void main(String[] args) {
		try {
			/*
			 * 1st argument is required, and should be dimension.
			 */
			int dimension = parseDimension(args);

			/*
			 * 2nd argument is optional, specifiying verbosity.
			 */
			boolean verbose = args.length > 1;

			/*
			 * 3rd argument is optional, and should be initial mapping.
			 */
			List<Integer> initial = parseInitial(args, 2);

			/*
			 * 4th and 5th arguments are optional, and are starting and ending
			 * positions.
			 */
			Integer start = parseOptionalInteger(args, 3);
			Integer end = parseOptionalInteger(args, 4);

			WirelengthCalculator calculator = null;
			if (initial != null) {
				calculator = new WirelengthCalculator(dimension, initial);
			} else {
				calculator = new WirelengthCalculator(dimension);
			}
			calculator.setVerbose(verbose);

			int minimum = 0;
			if (start != null && end != null) {
				minimum = calculator.calculateMinimumWirelength(start, end);
				System.out.print("Minimum between " + start + " and " + end + ": ");
				System.out.println(minimum);
			} else {
				minimum = calculator.calculateMinimumWirelength();
				System.out.println("Minimum: " + minimum);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
