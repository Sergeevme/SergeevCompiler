package compiler.scan;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNodeImpl;

import gram.MiniJavaBaseVisitor;
import gram.MiniJavaParser.*;
import gram.MiniJavaParser.WhileStatementContext;

@SuppressWarnings("rawtypes")
public class TypeCheckVis extends MiniJavaBaseVisitor {
	STable sTable;
	int errorCount;

	private String errorSend(ParseTree ctx) {
		return "[err#" + this.errorCount + " - @"
				+ ((ParserRuleContext) ctx).getStart().getLine() + ":"
				+ ((ParserRuleContext) ctx).getStop().getCharPositionInLine()
				+ "] ";

	}
	public int getErrorCount(){
		return this.errorCount;
	}

	public TypeCheckVis(STable table) {
		this.sTable = table;
		errorCount = 0;
	}

	// program:mainClass classDeclaration*;
	@Override
	public Object visitProgram(ProgramContext ctx) {
		int i = 0;
		int n = ctx.getChildCount();
		visitMainClass((MainClassContext) ctx.getChild(i++));
		for (; i < n; i++)
			visitClassDeclaration((ClassDeclarationContext) ctx.getChild(i));
		return null;
	}

	// mainClass:'class' identifier '{' mainMethod '}';
	@Override
	public Object visitMainClass(MainClassContext ctx) {
		sTable.enterScope();
		visit(ctx.getChild(3));
		sTable.exitScope();
		return null;
	}
	
	@Override
	public Object visitBreakStatement(BreakStatementContext ctx) {
		ParserRuleContext node = ctx;
		while(node != null) {
			node = node.getParent();
			if(node instanceof WhileStatementContext){
				return null;
			}
		}
		System.err.println(errorSend(ctx) + "Break Statement is not warpped in a \"While\" statement!");
		errorCount++;
		return null;
	}
	@Override
	public Object visitContinueStatement(ContinueStatementContext ctx) {
		ParserRuleContext node = ctx;
		while(node != null){
			node = node.getParent();
			if(node instanceof WhileStatementContext){
				return null;
			}
		}
		System.err.println(errorSend(ctx) + "Continue Statement is not warpped in a \"While\" statement!");
		errorCount++;
		return null;
	}

	// classDeclaration:'class' identifier '{' fieldDeclaration*
	// methodDeclaration* '}';
	@Override
	public Object visitClassDeclaration(ClassDeclarationContext ctx) {
		int i = 3;
		int n = ctx.getChildCount();
		sTable.enterScope();
		for (; i < n - 1; i++) {
			ParseTree child = ctx.getChild(i);
			if (child instanceof FieldDeclarationContext)
				visitFieldDeclaration((FieldDeclarationContext) child);
			else
				visitMethodDeclaration((MethodDeclarationContext) child);
		}
		sTable.exitScope();
		return null;
	}

	// mainMethod:'public' 'static' 'void' 'main' '(' 'String' '[' ']'
	// identifier ')' '{'statement+ '}';
	@Override
	public Object visitMainMethod(MainMethodContext ctx) {
		int i = 11;
		sTable.enterScope();
		while (ctx.getChild(i) instanceof StatementContext) {
			visit(ctx.getChild(i++));
		}
		sTable.exitScope();
		return null;
	}

	// 'System.out.println' LP(expression) RP SC;
	@Override
	public Object visitPrintStatement(PrintStatementContext ctx) {
		String type = (String) visit(ctx.getChild(2));
		if (!(type.equals("int") || type.equals("String") || type
				.equals("char"))) {
			System.err.println(errorSend(ctx)
					+ "Wrong Type in Print Statement");
			errorCount++;
		}
		return null;

	}

	@Override
	// 'while' LP expression RP statement;
	public Object visitWhileStatement(WhileStatementContext ctx) {
		String expressionType = (String) visit(ctx.getChild(2));
		if (!expressionType.equals("boolean")) {
			System.err
					.println(errorSend(ctx)
							+ "Wrong Type in While Condition, should be \" boolean \" ");
			errorCount++;
		}
		visit(ctx.getChild(4));
		return null;
	}
	
	@Override
	// 'do' statement 'while' LP expression RP SC;
	public Object visitDoWhileStatement(DoWhileStatementContext ctx) {
		String expressionType = (String) visit(ctx.getChild(4));
		if (!expressionType.equals("boolean")) {
			System.err.println(errorSend(ctx)
							+ "Wrong Type in While Condition, should be \" boolean \" ");
			errorCount++;
		}
		visit(ctx.getChild(4));
		return null;
	}

	@Override
	// 'if' LP expression RP statement ('else' statement)?
	public Object visitIfElseStatement(IfElseStatementContext ctx) {
		String expressionType = (String) visit(ctx.getChild(2));
		if (!expressionType.equals("boolean")) {
			System.err.println(errorSend(ctx)
					+ "Wrong type in IF Condition. should be \" boolean \"");
			errorCount++;
		}
		visit(ctx.getChild(4));
		if (ctx.getChildCount() > 4) {
			visit(ctx.getChild(6));
		}
		return null;
	}

	@Override
	// identifier LSB expression RSB EQ expression SC
	public Object visitArrayAssignmentStatement(
			ArrayAssignmentStatementContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeIndex = (String) visit(ctx.getChild(2));
		String typeRHS = (String) visit(ctx.getChild(5));
		if (typeLHS == null || typeRHS == null || typeIndex == null) {
			System.err.println(errorSend(ctx)
					+ " a type of null visited in statement \"" + ctx.getText()
					+ "\"");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int[]")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ ctx.getChild(0).getText() + " is not of type 'int[]'");
			errorCount++;
		}
		if (!typeIndex.equals("int")) {
			System.err.println(errorSend(ctx.getChild(2))
					+ " array access index should be an 'int' visited "
					+ typeIndex);
			errorCount++;
		}
		if (!typeRHS.equals("int")) {
			System.err.println(errorSend(ctx.getChild(5))
					+ "varibale with type '" + typeRHS
					+ "' cannot be assigned to 'int[]' ");
			errorCount++;
		}
		return null;
	}

	@Override
	// expression SC
	public Object visitMethodCallStatement(MethodCallStatementContext ctx) {
		visit(ctx.getChild(0));
		return null;
	}

	@Override
	// '{' statement* '}'
	public Object visitNestedStatement(NestedStatementContext ctx) {
		for (int i = 1; i < ctx.getChildCount() - 1; i++) {
			visit(ctx.getChild(i));
		}
		return null;
	}

	@Override
	// type identifier SC;
	public Object visitFieldDeclaration(FieldDeclarationContext ctx) {
		return super.visitFieldDeclaration(ctx);
	}

	@Override
	// methodDeclaration: public (type|'void') identifier '(' parameterList? ')'
	// '{'
	// methodBody '}';
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
		} else {
			returnType = (String) visitType((TypeContext) methodReturnType);
		}
		i += 2; // проходим 'identifier' '('
		sTable.enterScope();
		if (ctx.getChild(i) instanceof ParameterListContext) {
			visitParameterList((ParameterListContext) ctx.getChild(i++));
		}
		i += 2; // проходим ')' '{'
		String actualReturn = (String) visitMethodBody((MethodBodyContext) ctx
				.getChild(i++));
		if (returnType == null && actualReturn == null) {
			sTable.exitScope();
			return null;

		}
		if (returnType == null || actualReturn == null) {
			if (returnType == null) {
				System.err.println(errorSend(ctx.getChild(2))
						+ "'void' method \"" + ctx.getChild(2).getText()
						+ "\" cannot have return statement!");
				errorCount++;
				sTable.exitScope();
				return null;
			}
			System.err.println(errorSend(ctx.getChild(2)) + returnType
					+ " method \"" + ctx.getChild(2).getText()
					+ "\" should have return statement!");
			errorCount++;
			sTable.exitScope();
			return null;

		}
		if (!returnType.equals(actualReturn)) {
			System.err.println(errorSend(ctx.getChild(2))
					+ "difference in declration type '" + returnType
					+ "' and return type '" + actualReturn + "' in Method: "
					+ ctx.getChild(2).getText());
			errorCount++;
		}
		sTable.exitScope();
		return null;
	}

	@Override
	public Object visitParameterList(ParameterListContext ctx) {
		// TODO Auto-generated method stub
		return super.visitParameterList(ctx);
	}

	@Override
	public Object visitType(TypeContext ctx) {
		return ctx.getText();
	}

	// methodBody:localDeclaration* statement* (returnStatement)?;
	@Override
	public Object visitMethodBody(MethodBodyContext ctx) {
		int i = 0;
		while (ctx.getChild(i) instanceof LocalDeclarationContext) {
			visitLocalDeclaration((LocalDeclarationContext) ctx.getChild(i));
			i++;
		}
		while (ctx.getChild(i) instanceof StatementContext) {
			if (ctx.getChild(i).getChild(0) instanceof ReturnStatementContext) {
				return visitReturnStatement((ReturnStatementContext) ctx
						.getChild(i).getChild(0));
			} else
				visitStatement((StatementContext) ctx.getChild(i));
			i++;
		}

		return null;
	}

	// returnStatement: 'return' expression SC ;
	@Override
	public Object visitReturnStatement(ReturnStatementContext ctx) {
		return visit(ctx.getChild(1));
	}

	@Override
	public Object visitIdentifier(IdentifierContext ctx) {
		if (sTable.lookup(ctx.getText()) == null) {
			System.err.println(errorSend(ctx) + "Undifined ID: "
					+ ctx.getText());
			errorCount++;
			return null;
		}
		return sTable.lookup(ctx.getText()).getType();
	}

	@Override
	public Object visitIdentifierExpression(IdentifierExpressionContext ctx) {
		if (ctx.getChildCount() > 1) {
			return visitIdentifier((IdentifierContext) ctx.getChild(1));
		}
		return visitIdentifier((IdentifierContext) ctx.getChild(0));
	}

	// 'new' 'int' LSB expression RSB
	@Override
	public Object visitArrayInstantiationExpression(
			ArrayInstantiationExpressionContext ctx) {
		String type = (String) visitType((TypeContext) ctx.getChild(1));
		if (type != null) {
			if (type.equals("int"))
				return "int[]";
		}
		System.err.println(errorSend(ctx.getChild(1))
				+ "in MiniJava only 'int[]' arrays are acceptable");
		errorCount++;
		return null;
	}

	// 'new' identifier '(' ')'
	@Override
	public Object visitObjectInstantiationExpression(
			ObjectInstantiationExpressionContext ctx) {
		/*
		String type = (String) visitIdentifier((IdentifierContext) ctx
				.getChild(1));
		return type;
		*/
		return visitIdentifier((IdentifierContext) ctx.getChild(1));
	}

	// expression '.length'
	@Override
	public Object visitDotlengthExpression(DotlengthExpressionContext ctx) {
		String type = (String) visit(ctx.getChild(0));
		if (type != null) {
			if (type.equals("int[]") || type.equals("String"))
				return "int";
		}
		System.err
				.println(errorSend(ctx.getChild(0))
						+ "Error at "
						+ ctx.getChild(0).getText()
						+ ": \".length\" is applicable on \"int[]\" and \"String\" types only");
		errorCount++;
		return null;
	}

	@Override
	// expression '.chatAt' '(' expression ')'
	public Object visitDotcharatExpression(DotcharatExpressionContext ctx) {
		String type1 = (String) visit(ctx.getChild(0));// name
		String type2 = (String) visit(ctx.getChild(3)); // index
		if (type1 == null || type2 == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " has type of null");
			errorCount++;
			return null;
		}
		if (!type1.equals("String")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ ctx.getChild(0).getText() + " is not a String");
			errorCount++;
			return null;
		}
		if (!type2.equals("int")) {
			System.err.println(errorSend(ctx.getChild(3))
					+ ctx.getChild(3).getText() + " is not an integer");
			errorCount++;
			return null;
		}
		return "char";
	}

	@Override
	public Object visitStringExpression(StringExpressionContext ctx) {
		return "String";
	}

	@Override
	public Object visitCharacterExpression(CharacterExpressionContext ctx) {
		return "char";
	}

	@Override
	public Object visitIntegerLitExpression(IntegerLitExpressionContext ctx) {
		return "int";
	}

	@Override
	public Object visitBoolLitExpression(BoolLitExpressionContext ctx) {
		return "boolean";
	}

	@Override
	// expression ('.' identifier methodCallParams)+
	public Object visitMethCall(MethodCallExpressionContext ctx) {
		int i = 0;
		String className = (String) visit(ctx.getChild(i));
		ClassNote classRec = (ClassNote) sTable.lookup(className);
		if (classRec == null) {
			System.err.println(errorSend(ctx.getChild(0))
					+ " Undifined Class Name: \"" + className + "\"");
			errorCount++;
			return null;
		}
		MethodNote mRec = null;
		int n = ctx.getChildCount();
		i += 2; // пропустить '.' после (class/method) реализации
		for (; i < n; i++) { // у нас может быть вызвана цепочка или .length() или
							 // .charAt() после метода
			String mName = ctx.getChild(i).getText();
			mRec = classRec.getMethodList().get(mName);
			if (mRec == null) { // Неопределенное имя
				System.err.println(errorSend(ctx.getChild(i))
						+ " Undifined Method Name: \"" + mName
						+ "\" in class \"" + className + "\"");
				errorCount++;
				return null;
			}
			List<xNote> paramList = mRec.getParamList();
			i += 1; // после имени метода у нас есть methodCallParams вида
					// '('(expression(',' expression)*)? ')'
			ArrayList<ParseTree> methodCallParams = visitMethodCallParams((MethodCallParamsContext) ctx
					.getChild(i));
			if (paramList.size() != methodCallParams.size()) {
				System.err.println(errorSend(ctx.getChild(i))
						+ "Wrong number of parameters in calling " + mName);
				errorCount++;
			} else {
				for (int j = 0; j < methodCallParams.size(); j++) {
					String methodCallParamType = (String) visit(methodCallParams
							.get(j));
					String actualType = paramList.get(j).getType();
					if (!actualType.equals(methodCallParamType)) {
						System.err.println(errorSend(ctx.getChild(i))
								+ "Parameter type error in calling \"" + mName
								+ "\" parameter " + (j + 1) + " should be, "
								+ actualType + " but visited, "
								+ methodCallParamType);
						errorCount++;
					}
				}
			}
		}
		assert mRec != null;
		return mRec.getType();
	}

	@Override
	public Object visitThisExpression(ThisExpressionContext ctx) {
		String type = sTable.getCurrentClass().getType();
		return type;
	}

	@Override
	// '('(expression(',' expression)*)? ')'
	public ArrayList<ParseTree> visitMethodCallParams(
			MethodCallParamsContext ctx) {
		int n = ctx.getChildCount();
		ArrayList<ParseTree> children = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			ParseTree child = ctx.getChild(i);
			if (child instanceof TerminalNodeImpl
					&& (child.getText().equals("(")
							|| child.getText().equals(",") || child.getText()
							.equals(")")))
				continue;
			children.add(child);
		}
		return children;
	}

	@Override
	// expression LSB expression RSB
	public Object visitArrayAccessExpression(ArrayAccessExpressionContext ctx) {

		String type1 = (String) visit(ctx.getChild(0)); // имя
		String type2 = (String) visit(ctx.getChild(2)); // индекс
		if (type1 == null || type2 == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " has type of null");
			errorCount++;
			return null;
		}
		if (!type1.equals("int[]")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ ctx.getChild(0).getText() + "is not of type \"int[]\"");
			errorCount++;
			return null;
		}
		if (!type2.equals("int")) {
			System.err.println(errorSend(ctx.getChild(2))
					+ ctx.getChild(2).getText() + "is not an \"int\"");
			errorCount++;
			return null;
		}
		return "int";
	}

	@Override
	// expression PLUS expression
	public Object visitAddExpression(AddExpressionContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0))
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int") && !typeRHS.equals("int")
				&& !typeLHS.equals("String") && !typeRHS.equals("String")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ "Addition can be done on String or int types only!");
			errorCount++;
		}
		if (!typeLHS.equals(typeRHS)) {
			System.err.println(errorSend(ctx.getChild(0))
					+ "LHS and RHS should have same type (int or String)");
			errorCount++;
			return null;
		}

		return typeLHS;
	}

	@Override
	// identifier EQ expression SC
	public Object visitVariableStatement(
			VariableAssignmentStatementContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals(typeRHS)) {
			System.err.println(errorSend(ctx.getChild(0))
					+ " assignment LHS and RHS are not compatibale");
		}
		return typeLHS;
	}

	@Override
	// expression DIV expression
	public Object visitDivExpression(DivExpressionContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int") || !typeRHS.equals("int")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ "division can be done on int types only!");
			errorCount++;
		}
		return typeLHS;
	}

	@Override
	// expression TIMES expression
	public Object visitMulExpression(MulExpressionContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int") || !typeRHS.equals("int")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ "multiplication can be done on int types only!");
			errorCount++;
		}
		return typeLHS;
	}

	@Override
	public Object visitSubExpression(SubExpressionContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int") || !typeRHS.equals("int")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ "subtitution can be done on int types only!");
			errorCount++;
		}
		return typeLHS;
	}

	@Override
	public Object visitLessThanExpression(LessThanExpressionContext ctx) {
		int n = ctx.getChildCount();
		String typeRHS;

		String typeLHS = (String) visit(ctx.getChild(0));
		if (n > 3) {
			typeRHS = (String) visit(ctx.getChild(3));
		} else {
			typeRHS = (String) visit(ctx.getChild(2));
		}
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int") || !typeRHS.equals("int")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ "<, <= operations can be done on 'int' types only!");
			errorCount++;
			return null;
		}
		return "boolean";
	}

	@Override
	public Object visitGreaterthanExpression(GreaterthanExpressionContext ctx) {
		int n = ctx.getChildCount();
		String typeRHS;
		String typeLHS = (String) visit(ctx.getChild(0));
		if (n > 3) {
			typeRHS = (String) visit(ctx.getChild(3));
		} else {
			typeRHS = (String) visit(ctx.getChild(2));
		}
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("int") || !typeRHS.equals("int")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ ">, >= operations can be done on 'int' types only!");
			errorCount++;
			return null;
		}
		return "boolean";
	}

	@Override
	public Object visitAndExpression(AndExpressionContext ctx) {

		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (typeLHS.equals("boolean") && typeRHS.equals("boolean")) {
			return "boolean";

		} else {
			System.err.println(errorSend(ctx.getChild(0))
					+ "&& operation can be done on 'boolean' types only!");
			errorCount++;
			return null;
		}
	}

	@Override
	public Object visitEqualityExpression(EqualityExpressionContext ctx) {

		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(3));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals(typeRHS)) {
			System.err.println(errorSend(ctx.getChild(0))
					+ ctx.getChild(0).getText() + " has type of " + typeRHS
					+ " where " + ctx.getChild(2).getText() + " has type of "
					+ typeLHS);
			errorCount++;
			return null;
		}

		return "boolean";
	}

	@Override
	// expression OR expression
	public Object visitOrExpression(OrExpressionContext ctx) {
		String typeLHS = (String) visit(ctx.getChild(0));
		String typeRHS = (String) visit(ctx.getChild(2));
		if (typeLHS == null || typeRHS == null) {
			System.err.println(errorSend(ctx.getChild(0)) + "either "
					+ ctx.getChild(0).getText() + " or "
					+ ctx.getChild(2).getText() + " Has type of null");
			errorCount++;
			return null;
		}
		if (!typeLHS.equals("boolean") || !typeRHS.equals("boolean")) {
			System.err.println(errorSend(ctx.getChild(0))
					+ " || operation can be done on 'boolean' types only!");
			errorCount++;
			return null;
		}
		return typeLHS;
	}

	@Override
	// NOT expression
	public Object visitNotExpression(NotExpressionContext ctx) {

		String type = (String) visit(ctx.getChild(1));
		if (type == null) {
			System.err.println(errorSend(ctx.getChild(1))
					+ " type of expression after '!' is null");
			errorCount++;
			return null;
		}
		if (!type.equals("boolean")) {
			System.err.println(errorSend(ctx.getChild(1))
					+ " logical '!' can be done on 'boolean' types only!");
			errorCount++;
			return null;
		}
		return type;
	}

	@Override
	// '(' expression ')'
	public Object visitParenthesesExpression(ParenthesesExpressionContext ctx) {
		return visit(ctx.getChild(1));
	}

}
