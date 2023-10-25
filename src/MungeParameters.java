
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MungeParameters {

	private Interval interval = null;
	private String fileName = null;
	private int endStartIndex = -1;
	private int line = -1;
	
	public MungeParameters(
		Interval interval
		, StringBuilder fileName
		, int endStartIndex) {

		this.interval = interval;
		this.fileName = fileName.substring(0).trim();
		this.endStartIndex = endStartIndex;
	}

	public MungeParameters(
		Interval interval
		, StringBuilder fileName
		, int endStartIndex
		, int line) {

		this.interval = interval;
		this.fileName = fileName.substring(0).trim();
		this.endStartIndex = endStartIndex;
		this.line = line;
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

	public String toString() {
		return this.interval.toString()
			+ " " + this.fileName
			+ " " + Integer.valueOf(this.endStartIndex)
			+ " " + Integer.valueOf(this.line)
			;
	}

}

