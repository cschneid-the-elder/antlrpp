
		String text = getText();
		String textStripped = text.stripTrailing();
		statementTerminator = new String(textStripped.substring(textStripped.length() - 1));

