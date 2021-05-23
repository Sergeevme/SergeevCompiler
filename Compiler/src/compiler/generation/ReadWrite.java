package compiler.generation;

import java.io.*;

public class ReadWrite {

    File classFile;

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

    public void writeToFile(String fileName) {
        FileOutputStream fileToGo;
        ObjectOutputStream objectOut;
        try {
            java.io.File file = new java.io.File(fileName + ".tjp");
            file.getParentFile().mkdirs();
            fileToGo = new FileOutputStream(file,false);
            objectOut = new ObjectOutputStream(fileToGo);
            objectOut.writeObject(classFile);
            objectOut.close();
        } catch (Exception e) {
            System.err.println("Error in creating '" + fileName + ".tjp' ");
            e.printStackTrace();
        }
        System.out.println("\n" + fileName+ ".tjp was created!");
    }
}
