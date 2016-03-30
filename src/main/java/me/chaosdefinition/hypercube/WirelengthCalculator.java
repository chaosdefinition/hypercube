package me.chaosdefinition.hypercube;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Chaos Shen
 *
 */
public class WirelengthCalculator {

	private int dimension;
	private int vertices;
	private List<Integer> initial;
	private int minWirelength;

	private boolean verbose = false;

	/**
	 * Generates the graycode mapping of a hypercube of specified dimension.
	 * 
	 * @param dimension
	 *            the dimension of the hypercube
	 * @return a {@link List} of {@link Integer} representing the mapping
	 */
	public static List<Integer> graycode(int dimension) {
		List<Integer> code = null;

		if (dimension == 0) {
			code = new ArrayList<>();
			code.add(0);
		} else {
			code = graycode(dimension - 1);
			for (int i = code.size() - 1; i >= 0; --i) {
				code.add(code.get(i) + (1 << (dimension - 1)));
			}
		}

		return code;
	}

	/**
	 * Generates the lexicographic mapping of a hypercube of specified
	 * dimension.
	 * 
	 * @param dimension
	 *            the dimension of the hypercube
	 * @return a {@link List} of {@link Integer} representing the mapping
	 */
	public static List<Integer> lexicode(int dimension) {
		List<Integer> code = new ArrayList<>(1 << dimension);

		for (int i = 0; i < (1 << dimension); ++i) {
			code.add(i);
		}

		return code;
	}

	/**
	 * Determines if two vertices have an edge between them.
	 * 
	 * @param v1
	 *            the integer representation of a vertex
	 * @param v2
	 *            the same as {@code v1}
	 * @return {@code true} if the two vertices have an edge between them,
	 *         otherwise {@code false}
	 */
	public static boolean hasEdge(int v1, int v2) {
		/*
		 * An egde exists if only one bit differs, so we do this check by
		 * determing if their xor sum is a power of two.
		 */
		return ((v1 ^ v2) & ((v1 ^ v2) - 1)) == 0;
	}

	/**
	 * Returns the known minimum wirelength of circular mappings of hypercube of
	 * specified dimension, which typically is the wirelength of the graycode
	 * mapping.
	 * 
	 * @param dimension
	 *            the dimension of the hypercube
	 * @return the known minimum circular wirelength
	 */
	public static int knownMinimumWirelength(int dimension) {
		return 3 * (1 << ((dimension << 1) - 3)) - (1 << (dimension - 1));
	}

	/**
	 * Constructs a {@link WirelengthCalculator} for hypercube of specified
	 * dimension.
	 * 
	 * @param dimension
	 *            the dimension of the hypercube
	 */
	public WirelengthCalculator(int dimension) {
		this(dimension, null);
	}

	/**
	 * Constructs a {@link WirelengthCalculator} for hypercube of specified
	 * dimension with a given mapping to start with.
	 * 
	 * @param dimension
	 *            the dimension of the hypercube
	 * @param initial
	 *            the initial mapping to start with
	 */
	public WirelengthCalculator(int dimension, List<Integer> initial) {
		this.dimension = dimension;
		this.vertices = 1 << dimension;
		if (initial != null) {
			if (initial.size() != this.vertices) {
				throw new HypercubeException("Illegal size of initial mapping!");
			}
			/* block to check existence of each vertex */ {
				boolean[] presence = new boolean[this.vertices];
				for (int i : initial) {
					if (i >= 0 && i < this.vertices) {
						presence[i] = true;
					}
				}
				for (boolean b : presence) {
					if (!b) {
						throw new HypercubeException("Illegal value in initial mapping!");
					}
				}
			}
			this.initial = new ArrayList<>(initial);
		} else {
			this.initial = graycode(dimension);
		}

		this.minWirelength = knownMinimumWirelength(dimension);
	}

	/**
	 * Calculates the minimum circular wirelength on all mappings.
	 * 
	 * @return the minimum circular wirelength
	 */
	public int calculateMinimumWirelength() {
		return calculateMinimumWirelength(0, this.vertices);
	}

	public int calculateMinimumWirelength(int start, int end) {
		backtrack(this.initial, start, end);
		return this.minWirelength;
	}

	/**
	 * Calculates the complete circular wirelength of a mapping.
	 * 
	 * @param mapping
	 *            a mapping of hypercube
	 * @return the complete circular wirelength
	 */
	public int calculateWirelength(List<Integer> mapping) {
		return calculateWirelength(mapping, 0, mapping.size());
	}

	/**
	 * Calculates the partial circular wirelength of a mapping.
	 * 
	 * @param mapping
	 *            a mapping of hypercube
	 * @param start
	 *            the starting position in the mapping
	 * @param end
	 *            the ending position in the mapping
	 * @return the partial circular wirelength
	 */
	public int calculateWirelength(List<Integer> mapping, int start, int end) {
		int wirelength = 0;

		for (int i = start; i < end; ++i) {
			for (int j = i + 1; j < end; ++j) {
				if (hasEdge(mapping.get(i), mapping.get(j))) {
					if ((j - i) * 2 > this.vertices) {
						wirelength += this.vertices - (j - i);
					} else {
						wirelength += j - i;
					}
				}
			}
		}

		return wirelength;
	}

	/**
	 * Performs backtracking to generate all the mappings on specified range.
	 * 
	 * @param mapping
	 *            the current mapping of hypercube
	 * @param start
	 *            the starting position of the range
	 * @param end
	 *            the ending position of the range
	 */
	private void backtrack(List<Integer> mapping, int start, int end) {
		if (start >= end) {
			int wirelength = calculateWirelength(mapping);
			if (wirelength <= this.minWirelength) {
				this.minWirelength = wirelength;
				if (this.verbose) {
					System.out.println(mapping);
				}
			}
		} else {
			for (int i = start; i < end; ++i) {
				Collections.swap(mapping, i, start);
				backtrack(mapping, start + 1, end);
				Collections.swap(mapping, i, start);
			}
		}
	}

	/* getters and setters */
	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public int getVertices() {
		return vertices;
	}

	public void setVertices(int vertices) {
		this.vertices = vertices;
	}

	public List<Integer> getInitial() {
		return initial;
	}

	public void setInitial(List<Integer> initial) {
		this.initial = initial;
	}

	public int getMinWirelength() {
		return minWirelength;
	}

	public void setMinWirelength(int minWirelength) {
		this.minWirelength = minWirelength;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
}
