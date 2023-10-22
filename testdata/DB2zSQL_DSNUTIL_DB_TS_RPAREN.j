
		//System.out.println(getLine() + ":" + getCharPositionInLine() + "|" + getText() + "|" + " mode " + modeNames[_mode] + " prevMode " + (_modeStack.isEmpty() ? "empty" : modeNames[_modeStack.peek()]));
		dsnutil_db_ts_char = false;
		switch(_modeStack.peek()) {
			case DSNUTIL_PAREN_MODE :
				popMode(); //back to DSNUTIL_PAREN_MODE
				popMode(); //back to DSNUTIL_MODE
				break;
			default :
				popMode(); //back to "parent" mode
				break;
		}

