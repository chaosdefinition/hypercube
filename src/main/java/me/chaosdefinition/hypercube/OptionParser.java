package me.chaosdefinition.hypercube;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * A command line argument parser for hypercube.
 *
 * @author Chaos Shen
 */
public class OptionParser {

	@Option(name = "-d",
			aliases = "--dimension",
			metaVar = "DIM",
			usage = "specify dimension",
			required = true)
	private int dimension;

	@Option(name = "-i",
			aliases = "--initial",
			metaVar = "LIST",
			usage = "specify initial mapping",
			handler = IntegerArrayOptionHandler.class)
	private List<Integer> initial;

	@Option(name = "-s",
			aliases = "--start",
			metaVar = "START",
			usage = "set starting position")
	private Integer start;

	@Option(name = "-e",
			aliases = "--end",
			metaVar = "END",
			usage = "set ending position")
	private Integer end;

	@Option(name = "-o",
			aliases = "--output",
			metaVar = "FILE",
			usage = "specify output file")
	private String output;

	@Option(name = "-v",
			aliases = "--verbose",
			usage = "verbose mode")
	private boolean verbose = false;

	@Option(name = "-h",
			aliases = "--help",
			usage = "show this message and exit",
			help = true)
	private boolean help;

	/**
	 * Parses the command line arguments and stores the results in its fields.
	 *
	 * @param args
	 *            the original command line arguments
	 */
	public void parse(String[] args) {
		CmdLineParser parser = new CmdLineParser(this);

		try {
			parser.parseArgument(args);
			if (help) {
				System.out.println("Available options:");
				System.out.println();
				parser.printUsage(System.out);
				System.exit(0);
			}
			if (output != null) {
				try {
					System.setOut(new PrintStream(output));
				} catch (FileNotFoundException e) {
					throw new HypercubeException("File not found", e);
				}
			}
		} catch (CmdLineException e) {
			throw new HypercubeException("Failed to parse cmd line!", e);
		}
	}

	public int getDimension() {
		return dimension;
	}

	public List<Integer> getInitial() {
		return initial;
	}

	public Integer getStart() {
		return start;
	}

	public Integer getEnd() {
		return end;
	}

	public boolean isVerbose() {
		return verbose;
	}
}
