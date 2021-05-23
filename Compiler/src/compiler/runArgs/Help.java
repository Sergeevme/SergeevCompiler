package compiler.runArgs;

public class Help {

    public void helpArgs() {
        System.out.println("How ro run generation: java -jar <Generation.jar> <filePath.java>");
        System.out.println("How to run interpreter: java -jar <Interpreter.jar> <filePath.tjp>");
        System.out.println();
        System.out.println("Grammar of language:");

        System.out.println("program: mainClass classDeclaration*;\n" +
                "\n" +
                "mainClass: 'class' identifier '{' mainMethod '}';\n" +
                "mainMethod: 'public' 'static' 'void' 'main' '(' 'String' ('[' ']'|'...') identifier ')' " +
                "'{'statement+ '}';\n" +
                "classDeclaration: 'class' identifier '{' fieldDeclaration* methodDeclaration* '}';\n" +
                "parameter: type identifier;\n" +
                "fieldDeclaration: type identifier  SC;\n" +
                "localDeclaration: type identifier SC;\n" +
                "methodDeclaration: 'public'? (type|'void') identifier '(' parameterList? ')' '{' methodBody '}';\n" +
                "parameterList: parameter(',' parameter)*;\n" +
                "methodBody: localDeclaration* statement* (returnStatement)?;\n" +
                "type: 'int' '['']'| 'boolean'| 'int' | 'char' | 'String' | 'double' | identifier;\n" +
                "identifier: Identifier;");

        System.out.println("doWhileStatement: 'do' statement 'while' LP expression RP SC;\n" +
                "breakStatement: 'break' SC;\n" +
                "continueStatement: 'continue' SC;\n" +
                "arrayAssignmentStatement: identifier LSB expression RSB EQ expression SC;\n" +
                "variableAssignmentStatement: identifier EQ expression SC;\n" +
                "printStatement: 'System.out.println' LP(expression) RP SC;\n" +
                "whileStatement: 'while' LP expression RP statement;\n" +
                "ifElseStatement: 'if' LP expression RP statement ('else' statement)?;\n" +
                "nestedStatement: '{' statement* '}';\n" +
                "returnStatement: 'return' expression SC ;\n" +
                "methodCallStatement: expression SC;");
    }
}
