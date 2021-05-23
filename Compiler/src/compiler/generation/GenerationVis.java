package compiler.generation;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import compiler.scan.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import compiler.scan.MethodNote;
import gram.MiniJavaBaseVisitor;
import gram.MiniJavaParser.*;

@SuppressWarnings("rawtypes")
public class GenerationVis extends MiniJavaBaseVisitor implements Codes {

	private STable tab;  // Из предыдущей итерации
	private MethodNote currentMethod;  // См. visitMethodDecl()
	private String currentClass;  // См. visitClassDecl()
	private File classFile;  // Для сохранения на диске
	private int next;  // счетчик команд

	public GenerationVis(STable visitedST) {
		this.setTab(visitedST);
		this.setClassfile(new File());
		setIC(0);
	}

	@Override
	// 'public'? (type|'void') identifier '(' parameterList? ')' '{' methodBody
	// '}';
	public Object visitMethodDeclaration(MethodDeclarationContext ctx) {
		int i = 0;
		if (ctx.getChild(0) instanceof TerminalNodeImpl
				&& ctx.getChild(0).getText().equals("public")) {
			i++;  // пропустить 'public'
		}
		i++;  // пропустить тип
		String mName = ctx.getChild(i++).getText(); // Method name
		setCurrentMethod((MethodNote) tab.lookup(currentClass + "."
				+ mName));
		currentMethod.setList(currentMethod.getParamList());
		currentMethod.setList(currentMethod.getVarList());
		/* classFile */
		Method method = new Method();
		//method.setParamList(currentMethod.getParamList());
		method.setCatalog(currentMethod.getList());
		for (int k = 0; k < method.getCatalog().size(); k++) {
			// Инициализирование varList с 0 в качестве значения по
			// умолчанию с размером количества параметров и vars
			method.getVarList().add(k, 0);
		}
		classFile.addMethod(currentClass + "." + currentMethod.getId(), method);

		next = 0;
		for (int j = currentMethod.getParamList().size() - 1; j >= 0; j--) {
			// сохранить данные в соответствующем параметре
			addInstruction(STORE, j);
			next++;
		}
		tab.enterScope();  // Область действия метода
		while (!(ctx.getChild(i) instanceof MethodBodyContext)) {
			i++;
		}
		visitMethodBody((MethodBodyContext) ctx.getChild(i));
		tab.exitScope();  // Выход из области действия метода
		return null;
	}

	@Override
	// expression ('.' identifier methodCallParams)
	public Object visitMethCall(MethodCallExpressionContext ctx) {
		int i = 0;
		String className = (String) visit(ctx.getChild(i++));
		ClassNote classRec = (ClassNote) tab.lookup(className);
		MethodNote mRec;
		int n = ctx.getChildCount();
		i = i + 1;  // пропустить '.' после (class/method) реализации
		for (; i < n; i++) {
			String mName = ctx.getChild(i).getText();
			mRec = classRec.getMethodList().get(mName);
			i += 1;  // после имени метода у нас есть methodCallParams в виде
					 // '('(expression(',' expression)*)? ')'
			visitMethodCallParams((MethodCallParamsContext) ctx.getChild(i));
			addInstruction(INVOKE, className + "." + mName);
			next++;
			className = mRec.getType();
		}
		return null;
	}

	@Override
	// identifier EQ expression SC;
	public Object visitVariableStatement(VariableAssignmentStatementContext ctx) {
		String LHS = ctx.getChild(0).getChild(0).getText();
		xNote lookup = (xNote) tab.lookup(LHS);
		visit(ctx.getChild(2));
		addInstruction(STORE, currentMethod.getList().indexOf(lookup));
		next++;
		return null;
	}

	@Override
	// Identifier;
	public Object visitIdentifier(IdentifierContext ctx) {
		String varName = ctx.getText();
		Note varRec = tab.lookup(varName);
		if (varRec == null)
			return null;
		return null;
	}
	
	@Override
	public Object visitThisExpression(ThisExpressionContext ctx) {
		return tab.getCurrentClass().getId();
	}

	@Override
	public Object visitIdentifierExpression(IdentifierExpressionContext ctx) {
		String varName = ctx.getText();
		Note varRec = tab.lookup(varName);
		if (varRec == null)
			return null;
		addInstruction(LOAD, currentMethod.getList().indexOf(varRec));
		next++;
		return null;
	}

	@Override
	public Object visitIntegerLitExpression(IntegerLitExpressionContext ctx) {
		int value = Integer.parseInt(ctx.getText());
		addInstruction(CONST, value);
		next++;
		return null;
	}

	@Override
	public Object visitBoolLitExpression(BoolLitExpressionContext ctx) {
		String value = ctx.getText();
		if (value.equals("true")) {
			addInstruction(CONST, 1);
		}
		if (value.equals("false")) {
			addInstruction(CONST, 0);
		}
		next++;
		return null;
	}

	@Override
	// 'return' expression SC ;
	public Object visitReturnStatement(ReturnStatementContext ctx) {
		visit(ctx.getChild(1));
		addInstruction(RETURN, null);
		next++;
		return null;
	}

	@Override
	// 'while' LP expression RP statement;
	public Object visitWhileStatement(WhileStatementContext ctx) {
		int go_to = next;
		visit(ctx.getChild(2));
		int if_fale = next;
		addInstruction(IF_FALSE, null);
		next++;
		visit(ctx.getChild(4));  // Генерация While-body
		addInstruction(GOTO, go_to);
		next++;
		Method method = classFile.getMethods().get(
				currentClass + "." + currentMethod.getId());
		method.getInstList().get(if_fale).setArgument(next);
		return null;
	}

	@Override
	// 'System.out.println' LP(expression) RP SC;
	public Object visitPrintStatement(PrintStatementContext ctx) {
		visit(ctx.getChild(2));
		addInstruction(PRINT, null);
		next++;
		return null;
	}

	@Override
	// 'if' LP expression RP statement ('else' statement)?;
	public Object visitIfElseStatement(IfElseStatementContext ctx) {
		visit(ctx.getChild(2));  // Генерация условия
		int ifLabel = next;
		addInstruction(IF_FALSE, null);
		next++;
		visit(ctx.getChild(4));  // Генерация if-body
		int gotoLabel = next;
		addInstruction(GOTO, null);
		next++;
		Method method = classFile.getMethods().get(
				currentClass + "." + currentMethod.getId());
		method.getInstList().get(ifLabel).setArgument(next);
		if (ctx.getChildCount() > 4) {
			visit(ctx.getChild(6));  // Генерация else-body
			method.getInstList().get(gotoLabel).setArgument(next);
		}
		return null;
	}

	@Override
	public Object visitLessThanExpression(LessThanExpressionContext ctx) {
		int n = ctx.getChildCount();
		visit(ctx.getChild(0));
		if (n > 3) {
			visit(ctx.getChild(3));
		} else {
			visit(ctx.getChild(2));
		}
		addInstruction(ILT, null);
		next++;
		return null;
	}

	@Override
	// expression TIMES expression
	public Object visitMulExpression(MulExpressionContext ctx) {
		visit(ctx.getChild(0));
		visit(ctx.getChild(2));
		addInstruction(MULTI, null);
		next++;
		return null;
	}

	@Override
	// expression TIMES expression
	public Object visitAddExpression(AddExpressionContext ctx) {
		visit(ctx.getChild(0));
		visit(ctx.getChild(2));
		addInstruction(ADD, null);
		next++;
		return null;
	}

	@Override
	public Object visitDivExpression(DivExpressionContext ctx) {
		visit(ctx.getChild(0));
		visit(ctx.getChild(2));
		addInstruction(DIV, null);
		next++;
		return null;
	}

	@Override
	public Object visitSubExpression(SubExpressionContext ctx) {
		visit(ctx.getChild(0));
		visit(ctx.getChild(2));
		addInstruction(SUB, null);
		next++;
		return null;
	}

	@Override
	public Object visitNotExpression(NotExpressionContext ctx) {
		visit(ctx.getChild(1));
		addInstruction(NO, null);
		next++;
		return null;
	}

	@Override
	// 'class' identifier '{' mainMethod '}';
	public Object visitMainClass(MainClassContext ctx) {
		setCurrentClass(ctx.getChild(1).getText());
		tab.enterScope();
		visitMainMethod((MainMethodContext) ctx.getChild(3));
		tab.exitScope();
		addInstruction(STOP, null);
		next++;
		return null;
	}

	@Override
	// 'public' 'static' 'void' 'main' '(' 'String' ('[' ']'|'...') identifier
	// ')' '{'statement+ '}';
	public Object visitMainMethod(MainMethodContext ctx) {
		setCurrentMethod((MethodNote) tab.lookup(currentClass + ".main"));
		Method method = new Method();
		classFile.addMethod(currentClass + "." + currentMethod.getId(), method);
		classFile.setMainMethod(method);
		tab.enterScope();
		for (int i = 0; i < ctx.getChildCount(); i++) {
			if (ctx.getChild(i) instanceof StatementContext) {
				visitStatement((StatementContext) ctx.getChild(i));
			}
		}
		tab.exitScope();
		return null;
	}

	@Override
	// 'class' identifier '{' fieldDeclaration* methodDeclaration* '}';
	public Object visitClassDeclaration(ClassDeclarationContext ctx) {
		int i = 1;
		int n = ctx.getChildCount();
		currentClass = ctx.getChild(i++).getText(); // Имя класса
		i++;
		tab.enterScope();
		for (; i < n - 1; i++) {
			ParseTree child = ctx.getChild(i);
			if (child instanceof FieldDeclarationContext)
				System.err.println(errorMessage(child)
						+ " In Mini Java We should NOT have Field-Declaration");
			else
				visitMethodDeclaration((MethodDeclarationContext) child);
		}
		tab.exitScope();
		return null;
	}

	@Override
	// 'new' identifier '(' ')' # objectInstantiationExpression
	public Object visitObjectInstantiationExpression(
			ObjectInstantiationExpressionContext ctx) {
		return ctx.getChild(1).getText();
	}

	private String errorMessage(ParseTree ctx) {
		return "[err - @ " + ((ParserRuleContext) ctx).getStart().getLine()
				+ ":"
				+ ((ParserRuleContext) ctx).getStop().getCharPositionInLine()
				+ "] ";
	}

	private void addInstruction(int code, Object arg) {
		Instruction inst = new Instruction(code, arg);
		Method method = classFile.getMethods().get(
				currentClass + "." + currentMethod.getId());
		method.setInstList(inst);
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

	public void setClassfile(File classfile) {
		this.classFile = classfile;
	}

	public void setCurrentClass(String currentClass) {
		this.currentClass = currentClass;
	}

	public void setCurrentMethod(MethodNote currentMethod) {
		this.currentMethod = currentMethod;
	}

	public void setTab(STable tab) {
		this.tab = tab;
	}

	public void setIC(int iC) {
		next = iC;
	}
}
