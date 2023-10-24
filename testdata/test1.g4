lexer grammar DB2zSQLLexer;

@lexer::members {
	@AntlrPP(file1)
	public String statementTerminator = new String("");
	public int bracketNesting = 0;
	public Boolean dsnutil = false;
	public int dsnutilArgc = 0;
	public Boolean dsnutil_dsn_ws_char = false;
	public Boolean dsnutil_db_ts_char = false;
	public Boolean dsnutilLoad = false;
}

channels { COMMENTS }

fragment A:('a'|'A');
fragment B:('b'|'B');
fragment C:('c'|'C');
fragment D:('d'|'D');
fragment E:('e'|'E');
fragment F:('f'|'F');
fragment G:('g'|'G');
fragment H:('h'|'H');
fragment I:('i'|'I');
fragment J:('j'|'J');
fragment K:('k'|'K');
fragment L:('l'|'L');
fragment M:('m'|'M');
fragment N:('n'|'N');
fragment O:('o'|'O');
fragment P:('p'|'P');
fragment Q:('q'|'Q');
fragment R:('r'|'R');
fragment S:('s'|'S');
fragment T:('t'|'T');
fragment U:('u'|'U');
fragment V:('v'|'V');
fragment W:('w'|'W');
fragment X:('x'|'X');
fragment Y:('y'|'Y');
fragment Z:('z'|'Z');


