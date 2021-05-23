package compiler.generation;

import java.io.IOException;

import compiler.runArgs.Help;
import compiler.scan.STable;
import compiler.scan.STableVis;
import org.antlr.v4.gui.Trees;
import org.antlr.v4.runtime.BufferedTokenStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.tree.ParseTree;

import compiler.scan.TypeCheckVis;
import gram.MiniJavaLexer;
import gram.MiniJavaParser;

// Created by @Sergeevme

//
public class Generation {

	public static void main(String[] args) {

		// “Code never lies, comments sometimes do” — Ron Jeffries
		// Do not remove this comment else compilation will fail

		CharStream input;
		String fileName;

		if (args.length == 1 && args[0].equals("--help")) {
			Help help = new Help();
			help.helpArgs();
			// return
			return;
		}

		if (args.length > 0) {
			try {
				input = CharStreams.fromFileName(args[0]);
				fileName = args[0].substring(0, args[0].lastIndexOf('.'));
			} catch (IOException e) {
				System.out.println("Something went wrong! Please check your path file ");
				System.out.println("or if using the jar, check your command "
						+ "java -jar <name.jar> <filePath>");
				return;
			}
		} else {
			try {
				String file = "Compiler/files/Low.java";
				input = CharStreams.fromFileName(file);
				fileName = file.substring(0, file.lastIndexOf('.'));

			} catch (IOException e) {
				System.out.println("Something went wrong! Please check your path file ");
				System.out.println("or if using the jar, check your command "
						+ "java -jar <name.jar> <filePath>");
				return;
			}
		}

		MiniJavaLexer lexer = new MiniJavaLexer(input);
		MiniJavaParser parser = new MiniJavaParser(new BufferedTokenStream(lexer));
		ParseTree tr = parser.program();
		Trees.inspect(tr, parser);

		STableVis sTableVis = new STableVis();
		STable visitedST = (STable) sTableVis.visit(tr);
		if (sTableVis.getErrorFlag()) {
			System.err.println("The program has some errors!");
		} else {
			visitedST.printTable();
			visitedST.resetTable();
			TypeCheckVis tcv = new TypeCheckVis(visitedST);
			tcv.visit(tr);
			if (tcv.getErrorCount() > 0) {
				System.out.println("Program has " + tcv.getErrorCount() + " Type errors!");
				System.out.println("Unfortunately, the bytecode cannot be generated!");
			} else {
				visitedST.resetTable();
				GenerationVis cgv =
						new GenerationVis(visitedST);
				cgv.visit(tr);
				cgv.writeToFile(fileName);
			}
		}
	}
}
