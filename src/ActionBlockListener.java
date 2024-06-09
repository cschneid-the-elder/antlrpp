
/*Copyright (C) 2023 Craig Schneiderwent.  All rights reserved.*/

/*
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

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
	//private Pattern pattern = Pattern.compile("\\v?\\h*@AntlrPP\\((?<fileName>\\S*)\\)\\h*");
	private Pattern pattern = Pattern.compile("\\h*@AntlrPP\\((?<fileName>\\S*)\\)\\h*");
	private String currentRuleName = null;

	public ActionBlockListener(
			ArrayList<MungeParameters> mungeParameters
			, Boolean embedFiles
		) {
		super();
		this.mungeParameters = mungeParameters;
		this.embedFiles = embedFiles;
	}

	@Override public void enterLexerRuleSpec(ANTLRv4Parser.LexerRuleSpecContext ctx) { 
		this.currentRuleName = ctx.TOKEN_REF().getSymbol().getText();
	}
	
	@Override public void enterParserRuleSpec(ANTLRv4Parser.ParserRuleSpecContext ctx) {
		this.currentRuleName = ctx.RULE_REF().getSymbol().getText();
	}

	@Override public void enterAction_(ANTLRv4Parser.Action_Context ctx) {
		this.currentRuleName = ctx.identifier().getText();
	}

	@Override public void enterActionBlock(ANTLRv4Parser.ActionBlockContext ctx) {
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
					, ctx.BEGIN_ACTION().getSymbol().getCharPositionInLine()
					, ctx.END_ACTION().getSymbol().getLine()
					, ctx.END_ACTION().getSymbol().getCharPositionInLine()
					, true
					, this.currentRuleName));
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
									, ctx.BEGIN_ACTION().getSymbol().getLine()
									, ctx.BEGIN_ACTION().getSymbol().getCharPositionInLine()
									, ctx.END_ACTION().getSymbol().getLine()
									, ctx.END_ACTION().getSymbol().getCharPositionInLine()
									, false
									, this.currentRuleName));
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
					, this.currentRuleName));
			prevEndStartIndex = endStartIndex + 1;
		}
		
	}

}

