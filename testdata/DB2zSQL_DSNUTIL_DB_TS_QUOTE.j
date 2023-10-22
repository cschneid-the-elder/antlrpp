
		if (dsnutil_db_ts_char) {
			dsnutil = false;
			dsnutilArgc = 0;
			dsnutil_dsn_ws_char = false;
			dsnutil_db_ts_char = false;
			setType(DSNUTIL_CLOSE_QUOTE);
			switch(_modeStack.peek()) {
				case DSNUTIL_PAREN_MODE :
					popMode();
					break;
				case DSNUTIL_EXCEPTION_MODE :
					popMode(); //back to DSNUTIL_EXCEPTION_MODE
					popMode(); //back to DSNUTIL_MODE
					popMode(); //back to DEFAULT_MODE
					break;
				default :
					popMode(); //back to DSNUTIL_MODE
					popMode(); //back to DEFAULT_MODE
					break;
			}
		} else {
			pushMode(DSNUTIL_QUOTE_MODE);
			dsnutil_db_ts_char = true;
		}

