Attempt at a preprocessor for ANTLR4 lexer grammars.

A challenge for grammar authors is writing non-target language specific code.

Since actions are sometimes necessary, this preprocessor seeks to allow one
to write a grammar that "copies" or "includes" or "imports" text into the
grammar at a specified point.

This specified point is indicated by the arbitrary construct...

    @AntlrPP(_fileName_)

...which will itself be elided.  The embed option (which defaults to _true_)
is used to indicate whether or not _fileName_ will be copied into the target
stream.  If `-embed N` is specified, the `@AntlrPP(...)` construct will not
be included in the target stream and neither will the content of _fileName_.
If `-embed Y` is specified (or `-embed` is not specified at all) then the
the `@AntlrPP(...)` construct will not be included in the target stream, 
the content of _fileName_ will be copied into the target stream, and any
other content present in the actionBlock will be elided.

The _fileName_ must not contain whitespace characters and the ending ) must
be separated from any following characters by at least one whitespace
character.  Yes, I'm using a regex to find the @AntlrPP construct within the
action block because parsing the content of the action block would require 
that this application be aware of every ANTLR target language.

You may notice that the closing '}' for your action blocks don't necessarily
have the indention you originally coded.  This is because I ignore the content
of the action block except for the `@AntlrPP(...)` construct.  I am not going
to mess with the content because that's _yours_ and I was taught at a young
age to leave others' stuff alone.

Code is at a proof of concept stage.  I'm currently (09-Jun-2024) looking at 
constructing edge cases that are weirder than anything I've got in my portfolio.

Syntax:

    usage: java -jar AntlrPP [-embed <arg>] [-fileExt <arg>] [-help] 
           [-inputFile <arg>] [-outputFile <arg>] [-path <arg>]
     -embed <arg>        [Y | n] embed files or just elide embed construct
     -fileExt <arg>      extension to add to included file names, including
                         dot
     -help               print this message
     -inputFile <arg>    name of a single grammar to preprocess
     -outputFile <arg>   name of a file in which to place the preprocessed
                         grammar
     -path <arg>         path where included files are located including
                         trailing file system separator
     
Provision for both a file extension and a path to find "included" files
is provided.  It is possible a developer keeps (e.g.) both the Python and
the Java versions of their ANTLR grammar actions in the same directory,
and thus the extensions are needed.  It is also possible a developer keeps
their ANTLR grammar actions in files with the same names but in different
directories.

