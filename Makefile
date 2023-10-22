JC = javac
CP = "./class:./lib/commons-cli-1.4.jar:./lib/antlr-4.11.1-complete.jar"
JCOPT = -d ./class -g -Xlint -cp $(CP)
#JCOPT = -d ./class -g -cp $(CP)
JCOPT1 = -d ./class -cp $(CP)
AOPT = -visitor -listener
#AOPT = -o ./src -lib ./src -visitor -listener

./class/%.class: ./src/%.java
	echo `date` $< >>build.log
	$(JC) $(JCOPT) $<

./src/%.tokens: ./src/%.g4
	echo `date` $< >>build.log
	java -jar ./lib/antlr-4.11.1-complete.jar $(AOPT) $<
	$(JC) $(JCOPT1) ./src/$**.java
	

all: ./class/LexerAdaptor.class ./src/ANTLRv4Lexer.tokens ./src/ANTLRv4Parser.tokens ./class/MungeParameters.class ./class/ActionBlockListener.class ./class/AntlrPP.class

testrig:
	echo `date` $@ $(n) >> build.log
	java -cp ./class:.:./lib/antlr-4.11.1-complete.jar org.antlr.v4.gui.TestRig ANTLRv4 grammarSpec -gui -tokens < ./testdata/$(n)

testtree:
	echo `date` $@ $(n) >> build.log
	java -cp ./class:.:./antlr-4.11.1-complete.jar org.antlr.v4.gui.TestRig ANTLRv4 startRule -tree -tokens < ./testdata/$(n)

test:
ifeq ($(strip $(n)),)
	echo `date` $@ >> build.log
	echo `date`
	./src/tester all
	echo `date`
else
	echo `date` $@ $(n) >> build.log
	./src/tester $(n)
endif

jar:
	echo `date` $@ >> build.log
	jar cfm AntlrPP.jar manifest -C class .


./src/ANTLRv4Lexer.tokens: 

./src/ANTLRv4Parser.tokens: ./src/ANTLRv4Lexer.tokens

./class/LexerAdaptor.class: 
