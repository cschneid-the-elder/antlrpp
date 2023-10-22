
		if (dsnutil_dsn_ws_char) {
			/*
			We are not within apostrophes, this is the
			closing apostrophe of the whole third
			parameter for SYSPROC.DSNUTILx.
			*/
			dsnutil = false;
			dsnutilArgc = 0;
			dsnutil_dsn_ws_char = false;
			setType(DSNUTIL_CLOSE_APOS);
			popMode(); //back to DSNUTIL_DSN_MODE
			popMode(); //back to DSNUTIL_MODE
			popMode(); //back to DEFAULT_MODE
		} else {
			pushMode(DSNUTIL_APOS_MODE); //we don't come back here
		}

