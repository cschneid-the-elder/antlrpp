
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
	private Pattern pattern = Pattern.compile("\\v?\\h*@AntlrPP\\((?<fileName>\\S*)\\)\\h*");
	private String currentToken = null;

	public ActionBlockListener(
			ArrayList<MungeParameters> mungeParameters
			, Boolean embedFiles
		) {
		super();
		this.mungeParameters = mungeParameters;
		this.embedFiles = embedFiles;
	}

	public void enterLexerRuleSpec(ANTLRv4Parser.LexerRuleSpecContext ctx) { 
		this.currentToken = ctx.TOKEN_REF().getSymbol().getText();
	}
	
	public void enterActionBlock(ANTLRv4Parser.ActionBlockContext ctx) {
		int beginStopIndex = ctx.BEGIN_ACTION().getSymbol().getStopIndex();
		int endStartIndex = ctx.END_ACTION().getSymbol().getStartIndex();
		StringBuilder actionText = new StringBuilder();

		/*
		Get the contents of this actionBlock.
		*/
		for (TerminalNode tn: ctx.ACTION_CONTENT()) {
			actionText.append(tn.getSymbol().getText());
		}

		String actionTextString = new String(actionText);
		Matcher matcher = this.pattern.matcher(actionTextString);
		Boolean foundMatch = matcher.find();

		if (embedFiles && !foundMatch) {
			/*
			No match found means the entire actionBlock will be elided, we
			should notify someone.
			*/
			mungeParameters.add(
				new MungeParameters(
					new Interval(prevEndStartIndex, beginStopIndex)
					, new StringBuilder("")
					, endStartIndex
					, ctx.BEGIN_ACTION().getSymbol().getLine()
					, this.currentToken));
			prevEndStartIndex = endStartIndex;
			return; //I know this is considered bad, but it's clear
		}
		
		int start = 0;
		int end = 0;
		
		/*
		If we have something in the actionBlock contents that matches
		"@AntlrPP(...)" we want to create an instance of mungeParameters to
		signal which portions of the actionBlock contents to copy, which
		to throw away, and the name of the file we wish copied into
		the output stream.

		Signal that we want to copy everything from the previous end point
		to just after the beginning '{' of this actionBlock.
		*/
		if (foundMatch) {
			start = matcher.start() + beginStopIndex;
			end = matcher.end() + beginStopIndex;
			String copyFileName = new String("");
			if (this.embedFiles) {
				copyFileName = matcher.group("fileName");
			}
			mungeParameters.add(
				new MungeParameters(new Interval(prevEndStartIndex, start)
									, new StringBuilder(copyFileName)
									, endStartIndex
									, this.currentToken));
			prevEndStartIndex = end + 1;
		}

		if (this.embedFiles) {
			prevEndStartIndex = endStartIndex;
		} else {
			/*
			Signal that the rest of the actionBlock should be copied into
			the target stream.
			*/
			mungeParameters.add(
				new MungeParameters(
					new Interval(prevEndStartIndex, endStartIndex)
					, new StringBuilder("")
					, endStartIndex
					, this.currentToken));
			prevEndStartIndex = endStartIndex + 1;
		}
		
	}

}

