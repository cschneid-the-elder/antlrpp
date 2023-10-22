
import java.util.*;
import java.io.*;
import java.nio.file.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.apache.commons.cli.*;

public class AntlrPP {

	public static String inputFileName = null;
	public static String outputFileName = null;
	public static String pathToFile = null;
	public static String fileExtension = null;
	
	public static void main(String[] args) throws Exception {

		cli(args);
		
		ArrayList<MungeParameters> mungeParameters = new ArrayList<>();
		StringBuilder outputFileContent = new StringBuilder();
		
		CharStream cs = CharStreams.fromFileName(inputFileName);  //load the file

		System.out.println("lexing " + inputFileName);
		ANTLRv4Lexer lexer = new ANTLRv4Lexer(cs);  //instantiate a lexer
		CommonTokenStream tokens = new CommonTokenStream(lexer); //scan stream for tokens

		System.out.println("parsing " + inputFileName);
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
	
		System.out.println("walking parse tree with " + listener.getClass().getName());
	
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
			Path aPath = Paths.get(pathToFile, mp.getFileName() + fileExtension);
			System.out.println("processing " + aPath);
			if (Files.exists(aPath)) {
				if (mp.getFileName().length() > 0) {
					outputFileContent.append(Files.readString(aPath));
				}
			} else {
				System.out.println("unable to find " + aPath);
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
		
		BufferedWriter outputFile = new BufferedWriter(new FileWriter(outputFileName));
		outputFile.write(new String(outputFileContent));
		
		return;
	}

	public static void cli(String[] args) {
		Options options = new Options();
		CommandLineParser parser = new DefaultParser();
		CommandLine line = null;
		HelpFormatter formatter = new HelpFormatter();
		
		Option inputFile = new Option("inputFile", true
			, "name of a single grammar to preprocess");
		Option outputFile = new Option("outputFile", true
			, "name of a file in which to place the preprocessed grammar");
		Option path = new Option("path", true
			, "path where included files are located");
		Option fileExt = new Option("fileExt", true
			, "extension to add to included file names, including dot");
		Option help = new Option("help", false, "print this message");

		options.addOption(inputFile);
		options.addOption(outputFile);
		options.addOption(path);
		options.addOption(fileExt);
		options.addOption(help);

		try {
			line = parser.parse( options, args );
		} catch( ParseException exp ) {
			System.err.println( "Command line parsing failed.  Reason: " + exp.getMessage() );
			System.exit(16);
		}

		if (line.hasOption("help")) {
			formatter.printHelp( "AntlrPP", options, true );
			System.exit(0);
		}

		if (line.hasOption("inputFile")) {
			inputFileName = line.getOptionValue("inputFile");
		} else {
			System.err.println("inputFile option is required");
			formatter.printHelp( "AntlrPP", options, true );
			System.exit(4);
		}

		if (line.hasOption("outputFile")) {
			outputFileName = line.getOptionValue("outputFile");
		} else {
			System.err.println("outputFile option is required");
			formatter.printHelp( "AntlrPP", options, true );
			System.exit(4);
		}

		if (line.hasOption("fileExt")) {
			fileExtension = line.getOptionValue("fileExt");
		}

		if (line.hasOption("path")) {
			pathToFile = line.getOptionValue("path");
		}

		if (!line.hasOption("fileExt") && !line.hasOption("path")) {
			System.err.println("fileExt option or path option, or both is required");
			formatter.printHelp( "AntlrPP", options, true );
			System.exit(4);
		}

	}	
}

