package me.chaosdefinition.hypercube.optparse.handler;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.OptionDef;
import org.kohsuke.args4j.spi.OptionHandler;
import org.kohsuke.args4j.spi.Parameters;
import org.kohsuke.args4j.spi.Setter;

/**
 * @author Chaos Shen
 *
 */
public class IntegerArrayOptionHandler extends OptionHandler<Integer> {

	public IntegerArrayOptionHandler(CmdLineParser parser, OptionDef option, Setter<? super Integer> setter) {
		super(parser, option, setter);
	}

	@Override
	public int parseArguments(Parameters params) throws CmdLineException {
		int i = 0;

		for (; i < params.size(); ++i) {
			String param = params.getParameter(i);
			if (param.startsWith("-")) {
				break;
			}
			for (String s : param.split(",| ")) {
				setter.addValue(Integer.parseInt(s));
			}
		}
		
		return i;
	}

	@Override
	public String getDefaultMetaVariable() {
		// TODO Auto-generated method stub
		return null;
	}
}
