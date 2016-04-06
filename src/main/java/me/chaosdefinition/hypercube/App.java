package me.chaosdefinition.hypercube;

import java.util.List;

/**
 * Application starting point.
 *
 * @author Chaos Shen
 */
public class App {

	public static void main(String[] args) {
		try {
			Options options = new Options();
			options.parse(args);

			int dimension = options.getDimension();
			List<Integer> initial = options.getInitial();
			Integer start = options.getStart(), end = options.getEnd();
			boolean verbose = options.isVerbose();

			WirelengthCalculator calculator = null;
			if (options.getInitial() != null) {
				calculator = new WirelengthCalculator(dimension, initial);
			} else {
				calculator = new WirelengthCalculator(dimension);
			}
			calculator.setVerbose(verbose);

			int minimum = 0;
			if (start != null && end != null &&
					start >= 0 && start < (1 << dimension) &&
					end >= 0 && end < (1 << dimension)) {
				minimum = calculator.calculateMinimumWirelength(start, end);
				System.out.print("Minimum between " + start + " and " + end + ": ");
				System.out.println(minimum);
			} else {
				minimum = calculator.calculateMinimumWirelength();
				System.out.println("Minimum: " + minimum);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
