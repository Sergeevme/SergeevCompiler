package compiler.scan;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import gram.MiniJavaBaseVisitor;
import gram.MiniJavaParser.*;

@SuppressWarnings("rawtypes")
public class STableVis extends MiniJavaBaseVisitor {
	STable sTable;
	ClassNote currentClass;
	MethodNote currentMethod;
	boolean errorFlag;

	public STableVis() {
		super();
		this.sTable = new STable();
		this.currentClass = null;
		this.currentMethod = null;
		this.errorFlag = false;
	}

	public boolean getErrorFlag() {
		return this.errorFlag;
	}

	@Override
	public Object visitProgram(ProgramContext ctx) {
		int i = 0;
		int n = ctx.getChildCount();
		visitMainClass((MainClassContext) ctx.getChild(i++));
		for (; i < n; i++)
			visitClassDeclaration((ClassDeclarationContext) ctx.getChild(i));
		return sTable;
	}

	@Override
	public Object visitMainClass(MainClassContext ctx) {
		int i = 0;
		i++;  // пропустить 'CLASS'
		String cName = ctx.getChild(i++).getText(); // CLASS NAME

		currentClass = new ClassNote(cName, cName);
		sTable.put(cName, currentClass);  // Поместите запись класса в текущую область видимости
		sTable.enterScope();  // НОВАЯ СФЕРА КЛАССА
		sTable.setCurrentClass(currentClass);  // установить имя новому классу
												    // в текущей области
		i++;  // пропустить '{'
		visitMainMethod((MainMethodContext) ctx.getChild(i++));
		i++;  // пропустить '}'
		sTable.exitScope();
		return null;
	}

	@Override
	public Object visitClassDeclaration(ClassDeclarationContext ctx) {
		int i = 0;
		int n = ctx.getChildCount();

		i++;  // пропустить 'CLASS'
		String cName = ctx.getChild(i++).getText();  // название класса
		currentClass = new ClassNote(cName, cName);
		if (sTable.lookup(cName) != null) {
			errorFlag = true;
			System.err.println("[Duplicated]: Class name \"" + cName
					+ "\" already defined");
		} else {
			sTable.put(cName, currentClass);
			sTable.enterScope();
			sTable.setCurrentClass(currentClass);
		}
		i++;  // пропустить '{'
		for (; i < n - 1; i++) {
			ParseTree child = ctx.getChild(i);
			if (child instanceof FieldDeclarationContext)
				visitFieldDeclaration((FieldDeclarationContext) child);
			else
				visitMethodDeclaration((MethodDeclarationContext) child);
		}
		i++; // пропустить '}'
		sTable.exitScope();
		return null;
	}

	@Override
	public Object visitMainMethod(MainMethodContext ctx) {
		int i = 0;
		currentMethod = new MethodNote("main", null);
		if (sTable.lookup(currentClass.getId() + ".main") != null) {
			errorFlag = true;
			System.err.println("main method already defined!");
		} else {
			sTable.put(currentClass.getId() + ".main", currentMethod);
			currentClass.putOnMethod("main", currentMethod);
			i += 11;   // пропустить 'public' 'static' 'void' 'main' '(' 'String' '['
			           // ']'
			           // identifier ')' '{'
			sTable.enterScope();  // Новая область для сохранения главных методов
			while (ctx.getChild(i) instanceof StatementContext) {
				visitStatement((StatementContext) ctx.getChild(i++));
			}

			i++;  // пропустить '}'
			sTable.exitScope();
		}
		return null;
	}

	@Override
	public Object visitStatement(StatementContext ctx) {
		ParseTree child = ctx.getChild(0);
		visit(child);
		return null;
	}

	// тип идентификатора SC;
	@Override
	public Object visitFieldDeclaration(FieldDeclarationContext ctx) {
		int i = 0;
		String type = (String) visitType((TypeContext) ctx.getChild(i++));
		String name = ctx.getChild(i++).getText();
		i++;  // пропустить SC
		xNote var = new xNote(name, type);
		if (sTable.lookup(name) != null) {
			errorFlag = true;
			System.err.println("[Duplicated] Field Variable \"" + name
					+ "\" already defined");
		} else {
			sTable.put(name, var);
			currentClass.putOnField(name, var);  // Сохраняем переменную в fieldList класса
		}
		return null;
	}

	// тип идентификатора SC;
	@Override
	public Object visitLocalDeclaration(LocalDeclarationContext ctx) {
		int i = 0;
		String type = (String) visitType((TypeContext) ctx.getChild(i++));
		String name = ctx.getChild(i++).getText();
		i++;  // пропустить SC
		xNote var = new xNote(name, type);
		if (sTable.lookup(name) != null) {
			errorFlag = true;
			System.err.println("[Duplicated] Field Variable \"" + name
					+ "\" already defined");
		} else {
			sTable.put(name, var);
			currentMethod.putVar(var); // Сохранить переменную в методе
		}
		return null;
	}

	@Override
	public Object visitMethodDeclaration(MethodDeclarationContext ctx) {
		int i = 0;
		if (ctx.getChild(0) instanceof TerminalNodeImpl
				&& ctx.getChild(0).getText().equals("public")) {
			i++; // пропустить 'public'
		}
		ParseTree methodReturnType = ctx.getChild(i++);
		String returnType;
		if (methodReturnType instanceof TerminalNodeImpl) {
			returnType = null;
		} else { returnType = (String) visitType((TypeContext) methodReturnType); }
		String mName = ctx.getChild(i++).getText();
		if (currentClass.getId().equals(mName)) {
			errorFlag = true;
			System.err
					.println("The method name is the same as class name! we do not have construcotrs in MiniJava");
		}
		i++;  // пропустить '('
		currentMethod = new MethodNote(mName, returnType);
		if (sTable.lookup(currentClass.getId() + "." + mName) != null) {
			errorFlag = true;
			System.err.println("[Duplicated] Method name \"" + mName
					+ "\" already defined");
		} else {
			sTable.put(currentClass.getId() + "." + mName, currentMethod);
			currentClass.putOnMethod(mName, currentMethod);
			sTable.enterScope();
			sTable.setCurrentClass(currentClass);
			if (ctx.getChild(i) instanceof ParameterListContext) {
				visitParameterList((ParameterListContext) ctx.getChild(i++));
			}
			i += 2;  // пропустить ')' '{'
			visitMethodBody((MethodBodyContext) ctx.getChild(i++));
			i++;  // пропустить '}'
			sTable.exitScope();
		}
		return null;
	}

	// 'int' '['']'| 'boolean'| 'int' | 'char' | 'String' | identifier;
	@Override
	public Object visitType(TypeContext ctx) {
		return ctx.getText();
	}

	// parameter(',' parameter)*;
	@Override
	public Object visitParameterList(ParameterListContext ctx) {
		int n = ctx.getChildCount();
		for (int i = 0; i < n; i += 2)
			// пропускается ','s
			visitParameter((ParameterContext) ctx.getChild(i));
		return null;
	}

	// parameter ==> type identifier
	@Override
	public Object visitParameter(ParameterContext ctx) {
		int i = 0;
		String type = (String) visitType((TypeContext) ctx.getChild(i++));
		String name = ctx.getChild(i++).getText();
		xNote var = new xNote(name, type);
		if (sTable.lookup(name) != null) {
			errorFlag = true;
			System.err.println("[Duplicated] parameter name \"" + name
					+ "\'\" already defined");
		} else {
			sTable.put(name, var);
			currentMethod.putParam(var);  // Сохранить параметр в методе
		}
		return null;
	}

	// localDeclaration* statement* (returnStatement)?;
	@Override
	public Object visitMethodBody(MethodBodyContext ctx) {
		int i = 0;
		int n = ctx.getChildCount();
		for (; i < n; i++) {
			ParseTree child = ctx.getChild(i);
			visit(child);
		}
		return null;
	}

	@Override
	public Object visitNestedStatement(NestedStatementContext ctx) {
		int i = 0;
		int n = ctx.getChildCount();
		i++;
		for (; i < n - 1; i++) {
			if (ctx.getChild(i) instanceof StatementContext)
				visitStatement((StatementContext) ctx.getChild(i));
		}
		return null;
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		return ctx.getText();
	}

	@Override
	public Object visitIdentifierExpression(IdentifierExpressionContext ctx) {
		if (ctx.getChildCount() > 1)
			return ctx.getChild(1).getText();
		return ctx.getChild(0).getText();
	}
}
