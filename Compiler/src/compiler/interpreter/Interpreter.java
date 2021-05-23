package compiler.interpreter;

public class Interpreter {

	public static void main(String[] args) throws Exception {
		String file;
		if (args.length >= 1) file = args[0];
		else file = "Compiler/files/Low.tjp";
		goStack(file);
	}

	public static void goStack(String file) {
		Execute sergeev = new Execute(file);
		sergeev.executable();
	}
}
