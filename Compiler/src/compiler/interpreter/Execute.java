package compiler.interpreter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Stack;

import compiler.generation.Codes;
import compiler.generation.Instruction;
import compiler.generation.Method;
import compiler.generation.File;

public class Execute implements Codes {
	public final int zero = 0;
	Stack<Integer> data;  // Stack
	Stack<compiler.generation.Method> activ;
	int next = 0;
	File classFile;

	public Execute(String name) {
		data = new Stack<>();
		activ = new Stack<>();
		classFile = readFromFile(name);
	}

	public void dataZeroPush() {
		data.push(0);
	}

	public void dataOnePush() {
		data.push(1);
	}

	public void executable() {
		int number;
		int code = zero;
		Method method = classFile.getMainMethod();
		while (code != STOP) {
			Instruction instruction = method.getInstruction(next);
			code = instruction.getCode();
			Object argument = instruction.getArgument();
			switch (code) {
				case CONST: // push целочисленное значение argument
					data.push(Integer.parseInt(argument.toString()));
					break;
				case STORE:
					number = Integer.parseInt(argument.toString());
					method.getVarList().set(number, data.pop());
					break;
				case LOAD:
					number = Integer.parseInt(argument.toString());
					data.push(method.getVarList().get(number));
					break;
				case ADD: // v1 = pop(), v2 = pop(), push(v1+v2)
					data.push(data.pop() + data.pop());
					break;
				case MULTI:
					data.push(data.pop() * data.pop());
					break;
				case SUB:
					Integer n1 = data.pop();
					Integer n2 = data.pop();
					data.push(n2 - n1);
					break;
				case ILT: // v1 = pop() v2= pop() if(v2<v1) push(1) else push(0)
					n1 = data.pop();
					n2 = data.pop();
					if (n2 < n1) dataOnePush();
					else dataZeroPush();
					break;
				case NO:
					if (data.pop() == 0) dataOnePush();
					else dataZeroPush();
					break;
				case AND:
					if (data.pop() * data.pop() == 0)
						dataZeroPush();
					else dataOnePush();
					break;
				case OR:
					if (data.pop() + data.pop() == 0)
						dataZeroPush();
					else dataOnePush();
					break;
				case GOTO: // pc = argument
					number = Integer.parseInt(argument.toString());
					next = number - 1;
					break;  // в конце происходит добавление переключателя, затем я уменьшаю
							// pc-1
				case IF_FALSE:  // v = pop(), let pc = argument if v = 0
					number = Integer.parseInt(argument.toString());
					if (data.pop() == 0)
						next = number - 1;
					break;
				case INVOKE:
					method.setPC(next);
					activ.push(method);
					method = classFile.getMethods().get(argument.toString());
					next = -1;
					break;
				case RETURN:
					method = activ.pop();
					next = method.getPC();
					break;
				case PRINT:
					System.out.println(data.pop());
					break;
			}
			next++;
		}
	}

	public File readFromFile(String name) {
		ObjectInputStream In;
		File file = null;
		try {
			In = new ObjectInputStream(new BufferedInputStream(new FileInputStream(name)));
			file = (File) In.readObject();
			In.close();
		} catch (ClassNotFoundException | IOException e) {
			System.err.println("Wrong path or filename. Please check it out!");
		}
		return file;
	}
}
