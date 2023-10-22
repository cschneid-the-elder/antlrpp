
		//System.out.println(getLine() + ":" + getCharPositionInLine() + "|" + getText() + "|" + " mode " + modeNames[_mode] + " prevMode " + (_modeStack.isEmpty() ? "empty" : modeNames[_modeStack.peek()]));
		switch(_modeStack.peek()) {
			case DSNUTIL_DSN_WS_MODE :
				popMode(); //back to DSNUTIL_DSN_WS_MODE
				popMode(); //back to DSNUTIL_DSN_MODE
				popMode(); //back to DSNUTIL_MODE
				break;
			case DSNUTIL_DSN_MODE :
				popMode(); //back to DSNUTIL_DSN_MODE
				popMode(); //back to DSNUTIL_MODE
				break;
			case DSNUTIL_DB_TS_MODE :
				popMode(); //back to DSNUTIL_DB_TS_MODE
				popMode(); //back to DSNUTIL_MODE
				break;
			default :
				popMode(); //back to "parent" mode (which may be this mode)
				break;
		}

