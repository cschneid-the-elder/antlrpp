
		//System.out.println("dsnutil && dsnutilArgc == 2 & \'");
		/*
		Setting this variable to false is necessary here because 
		the EXEC SQL online Utility Control Statement may come back
		through this token if a literal is present in any dynamic
		SQL being processed.
		*/
		dsnutil = false;

