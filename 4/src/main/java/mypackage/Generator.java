package mypackage;

import java.io.*;

/**
 * Created by root on 01.06.16.
 */
public class Generator {
//    ε
//
//    decls: decl decls | ε;
//    decl: Str vars ';';
//    vars: Str varsE;
//    varsE: ',' STR varsE | ε;

//    javac mypackage/*.java && java mypackage.Generator grammar.mg assignments && javac grammar/*.java && java grammar.Main test

    public static void test() throws IOException {
        InputStream is = new FileInputStream("test.mg");
        GrammarParser grammarParser = new GrammarParser(is);
        grammarParser.parse();
        grammarParser.createLexerAndParserFiles("test", "decls", null);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length < 3 || args[0] == null || args[1] == null) {
            System.err.println("need grammar file & main rule & input");
            System.exit(1);
        }
        InputStream is = new FileInputStream(args[0]);
        GrammarParser grammarParser = new GrammarParser(is);
        grammarParser.parse();
        grammarParser.createLexerAndParserFiles(args[0].split("\\.")[0], args[1], (args[2].isEmpty() ? null : args[2]));
    }
}
