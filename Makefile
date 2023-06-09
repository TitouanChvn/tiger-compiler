.DEFAULT := main

parser :
	java -jar ./lib/antlr-4.9.2-complete.jar ./tiger.g4 -no-listener -visitor -o ./src/parser

compile :
	javac -cp ./lib/antlr-4.9.2-complete.jar:./src ./src/Main.java -d ./bin

good :
	java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main Tests/Good/declaration_int.tig
run :
	java -cp ./lib/antlr-4.9.2-complete.jar:./bin Main $(target)
