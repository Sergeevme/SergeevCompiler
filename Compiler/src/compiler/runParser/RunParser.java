package compiler.runParser;

import java.io.IOException;

import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import gram.MiniJavaLexer;
import gram.MiniJavaParser;

public class RunParser {

    public static void main(String[] args) throws Exception {
        CharStream input = null;
        try {
            input = CharStreams.fromFileName("./Compiler/files/While.java");
        } catch (IOException e) { e.printStackTrace(); }

        MiniJavaLexer LEX = new MiniJavaLexer(input);
        MiniJavaParser PARS = new MiniJavaParser(new BufferedTokenStream(LEX));
        ParseTree tree = PARS.program();

        Trees.inspect(tree, PARS);
        System.out.println("Complete!");
    }
}
