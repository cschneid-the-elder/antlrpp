
import java.util.*;
import java.io.*;
import java.nio.file.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class AntlrPP {

	public static void main(String[] args) throws Exception {

		String inputFileName = args[0];
		String pathToFile = args[1];
		String fileExt = args[2];
		ArrayList<MungeParameters> mungeParameters = new ArrayList<>();
		StringBuilder outputFileContent = new StringBuilder();
		
		CharStream cs = CharStreams.fromFileName(inputFileName);  //load the file

		ANTLRv4Lexer lexer = new ANTLRv4Lexer(cs);  //instantiate a lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens

		ANTLRv4Parser parser = new ANTLRv4Parser(tokens);  //parse the tokens	

		ParseTree tree = null;
		try {
			tree = parser.grammarSpec(); // parse the content and get the tree
		} catch(Exception e) {
			System.out.println("Parser error " + e);
			System.exit(12);
		}
		
		ParseTreeWalker walker = new ParseTreeWalker();
	
		/*
		This listener accumulates instances of Interval which indicate which
		segments of the CharStream should be copied verbatim to the output
		and String instances which are names of files whose contents is to be 
		copied verbatim into the output.  This information is stored in the
		ArrayList of instances of MungeParameters.
		*/
		ActionBlockListener listener = new ActionBlockListener(mungeParameters);
	
		System.out.println("----------walking tree with " + listener.getClass().getName());
	
		try {
			walker.walk(listener, tree);
		} catch(Exception e) {
			System.out.println(listener.getClass().getName() + " error " + e);
			System.exit(12);
		}

		int csEOF = cs.index();  //this should be the index of the EOF marker

		/*
		Loop through the mungeParameters and copy the data to the output (a StringBuilder).
		*/
		for (MungeParameters mp: mungeParameters) {
			outputFileContent.append(cs.getText(mp.getInterval()));
			if (mp.getFileName().length() > 0) {
				outputFileContent.append(Files.readString(Paths.get(pathToFile, mp.getFileName() + fileExt)));
			}
		}

		/*
		Now get the last part of the input file, that which comes after the last
		actionBlock.
		*/
		int mpSize = mungeParameters.size();
		mpSize--;
		int lastStart = mungeParameters.get(mpSize).getEndStartIndex();
		outputFileContent.append(cs.getText(new Interval(lastStart, csEOF)));
		
		System.out.println("file content = |" + outputFileContent + "|");
		return;
	}
	
}

