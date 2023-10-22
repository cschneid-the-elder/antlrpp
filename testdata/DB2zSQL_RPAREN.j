
		//System.out.println(getLine() + ":" + getCharPositionInLine() + "|" + getText() + "|" + " mode " + modeNames[_mode] + " prevMode " + (_modeStack.isEmpty() ? "empty" : modeNames[_modeStack.peek()]));
		switch (_modeStack.peek()){
			case DEFAULT_MODE :
				popMode();
				break;
			case DSNUTIL_WHEN_MODE :
				popMode();
				popMode();
				break;
			default:
				popMode();
				popMode();
				break;
		}

