
import java.util.*;
import java.util.logging.Logger;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class ActionBlockListener extends ANTLRv4ParserBaseListener {
	private ArrayList<MungeParameters> mungeParameters;
	private int prevEndStartIndex = 0;

	public ActionBlockListener(
			ArrayList<MungeParameters> mungeParameters
		) {
		super();
		this.mungeParameters = mungeParameters;
	}

	public void enterActionBlock(ANTLRv4Parser.ActionBlockContext ctx) {
		int beginPosn = ctx.BEGIN_ACTION().getSymbol().getCharPositionInLine();
		int beginLine = ctx.BEGIN_ACTION().getSymbol().getLine();
		int beginStopIndex = ctx.BEGIN_ACTION().getSymbol().getStopIndex();
		int endPosn = ctx.END_ACTION().getSymbol().getCharPositionInLine();
		int endLine = ctx.END_ACTION().getSymbol().getLine();
		int endStartIndex = ctx.END_ACTION().getSymbol().getStartIndex();
		StringBuilder copyFileName = new StringBuilder();
		
		for (TerminalNode tn: ctx.ACTION_CONTENT()) {
			copyFileName.append(tn.getSymbol().getText());
		}

		System.out.println("beginStopIndex = " + beginStopIndex);
		System.out.println("endStartIndex  = " + endStartIndex);
		System.out.println("copyFileName   = " + copyFileName);
		
		mungeParameters.add(new MungeParameters(new Interval(prevEndStartIndex, beginStopIndex), copyFileName, endStartIndex));
		
		prevEndStartIndex = endStartIndex;
	}

}

