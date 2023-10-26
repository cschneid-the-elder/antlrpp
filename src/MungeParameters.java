
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

