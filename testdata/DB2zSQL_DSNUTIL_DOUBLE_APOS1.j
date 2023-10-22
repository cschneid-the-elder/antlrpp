
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
			default :
				popMode(); //back to "parent" mode
				break;
		}

