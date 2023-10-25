
import java.util.*;
import java.util.regex.*;
import java.util.logging.Logger;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class ActionBlockListener extends ANTLRv4ParserBaseListener {
	private ArrayList<MungeParameters> mungeParameters;
	private int prevEndStartIndex = 0;
	private Boolean embedFiles = true;
	private Pattern pattern = Pattern.compile("@AntlrPP\\((?<fileName>.*)\\)");

	public ActionBlockListener(
			ArrayList<MungeParameters> mungeParameters
			, Boolean embedFiles
		) {
		super();
		this.mungeParameters = mungeParameters;
		this.embedFiles = embedFiles;
	}

	public void enterActionBlock(ANTLRv4Parser.ActionBlockContext ctx) {
		int beginStopIndex = ctx.BEGIN_ACTION().getSymbol().getStopIndex();
		int endStartIndex = ctx.END_ACTION().getSymbol().getStartIndex();
		StringBuilder actionText = new StringBuilder();

		/*
		Signal that we want to copy everything from the previous end point
		to just after the beginning '{' of this actionBlock.
		*/
		mungeParameters.add(
			new MungeParameters(
				new Interval(prevEndStartIndex, beginStopIndex)
				, new StringBuilder("")
				, -1));
		prevEndStartIndex = beginStopIndex + 1;
		
		/*
		Get the contents of this actionBlock.
		*/
		for (TerminalNode tn: ctx.ACTION_CONTENT()) {
			actionText.append(tn.getSymbol().getText());
		}

		String actionTextString = new String(actionText);
		Matcher matcher = this.pattern.matcher(actionTextString);
		int start = 0;
		int end = 0;
		
		/*
		While we have something in the actionBlock contents that matches
		"@AntlrPP(...)" we want to create instances of mungeParameters to
		signal which portions of the actionBlock contents to copy, which
		to throw away, and the name(s) of file(s) we wish copied into
		the output stream.
		*/
		while(matcher.find()) {
			start = matcher.start() + beginStopIndex;
			end = matcher.end() + beginStopIndex;
			String copyFileName = new String("");
			if (this.embedFiles) {
				copyFileName = matcher.group("fileName");
			}
			mungeParameters.add(
				new MungeParameters(new Interval(prevEndStartIndex, start)
									, new StringBuilder(copyFileName)
									, -1));
			prevEndStartIndex = end + 1;
		}
		mungeParameters.add(
			new MungeParameters(
				new Interval(prevEndStartIndex, endStartIndex)
				, new StringBuilder("")
				, endStartIndex));
		
		prevEndStartIndex = endStartIndex;
	}

}

