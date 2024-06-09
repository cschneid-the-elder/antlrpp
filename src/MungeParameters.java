
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

import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class MungeParameters {

	private Interval interval = null;
	private String fileName = null;
	private int endStartIndex = -1;
	private int startLine = -1;
	private int startPosn = -1;
	private int endLine = -1;
	private int endPosn = -1;
	private String ruleName = null;
	private Boolean entireActionBlockElided = false;
	
	public MungeParameters(
		Interval interval
		, StringBuilder fileName
		, int endStartIndex
		, String ruleName) {

		this.interval = interval;
		this.fileName = fileName.substring(0).trim();
		this.endStartIndex = endStartIndex;
		this.ruleName = ruleName;
	}

	public MungeParameters(
		Interval interval
		, StringBuilder fileName
		, int endStartIndex
		, int startLine
		, int startPosn
		, int endLine
		, int endPosn
		, Boolean entireActionBlockElided
		, String ruleName) {

		this.interval = interval;
		this.fileName = fileName.substring(0).trim();
		this.endStartIndex = endStartIndex;
		this.startLine = startLine;
		this.startPosn = startPosn;
		this.endLine = endLine;
		this.endPosn = endPosn;
		this.ruleName = ruleName;
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
	
	public int getStartLine() {
		return this.startLine;
	}
	
	public int getStartPosn() {
		return this.startPosn;
	}
	
	public String getRuleName() {
		return this.ruleName;
	}

	public Boolean getEntireActionBlockElided() {
		return this.entireActionBlockElided;
	}
	
	public String toString() {
		return
			this.ruleName 
			+ " " + this.interval.toString()
			+ " " + this.fileName
			+ " " + Integer.valueOf(this.endStartIndex)
			+ " " + Integer.valueOf(this.startLine)
			+ " " + Integer.valueOf(this.startPosn)
			+ " " + Integer.valueOf(this.endLine)
			+ " " + Integer.valueOf(this.endPosn)
			;
	}

}

