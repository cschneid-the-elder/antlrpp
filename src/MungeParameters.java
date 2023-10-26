
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MungeParameters {

	private Interval interval = null;
	private String fileName = null;
	private int endStartIndex = -1;
	private int line = -1;
	private String tokenName = null;
	
	public MungeParameters(
		Interval interval
		, StringBuilder fileName
		, int endStartIndex
		, String tokenName) {

		this.interval = interval;
		this.fileName = fileName.substring(0).trim();
		this.endStartIndex = endStartIndex;
		this.tokenName = tokenName;
	}

	public MungeParameters(
		Interval interval
		, StringBuilder fileName
		, int endStartIndex
		, int line
		, String tokenName) {

		this.interval = interval;
		this.fileName = fileName.substring(0).trim();
		this.endStartIndex = endStartIndex;
		this.line = line;
		this.tokenName = tokenName;
	}

	public Interval getInterval() {
		return this.interval;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public int getEndStartIndex() {
		return this.endStartIndex;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public String getTokenName() {
		return this.tokenName;
	}

	public String toString() {
		return
			this.tokenName 
			+ " " + this.interval.toString()
			+ " " + this.fileName
			+ " " + Integer.valueOf(this.endStartIndex)
			+ " " + Integer.valueOf(this.line)
			;
	}

}

