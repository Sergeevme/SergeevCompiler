package compiler.interpreter;

import compiler.generation.Codes;
import compiler.generation.File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Stack;

public class ExeIni implements Codes {
    public final int zero = 0;
    Stack<Integer> data;  // Stack
    Stack<compiler.generation.Method> activ;
    int next = 0;
    File classFile;

    public ExeIni(String name) {
        data = new Stack<>();
        activ = new Stack<>();
        classFile = readFromFile(name);
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
