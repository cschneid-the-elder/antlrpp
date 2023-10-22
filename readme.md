Attempt at a preprocessor for ANTLR4 grammars.

A challenge for grammar authors is writing non-target language specific code.

Since actions are sometimes necessary, this preprocessor seeks to allow one
to write a grammar that "copies" or "includes" or "imports" text into the
grammar at a specified point.

Code is at a proof of concept stage.

Syntax:

usage: AntlrPP [-fileExt <arg>] [-help] [-inputFile <arg>] [-outputFile
       <arg>] [-path <arg>]
 -fileExt <arg>      extension to add to included file names, including
                     dot
 -help               print this message
 -inputFile <arg>    name of a single grammar to preprocess
 -outputFile <arg>   name of a file in which to place the preprocessed
                     grammar
 -path <arg>         path where included files are located

Provision for both a file extension and a path to find "included" files
is provided.  It is possible a developer keeps (e.g.) both the Python and
the Java versions of their ANTLR grammar actions in the same directory,
and thus the extensions are needed.  It is also possible a developer keeps
their ANTLR grammar actions in files with the same names but in different
directories.

