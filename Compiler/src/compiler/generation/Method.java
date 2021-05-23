package compiler.generation;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import compiler.scan.xNote;

public class Method implements Serializable, Codes {

	@Serial
	private static final long serialVersionUID = 1L;
	private final List<xNote> catalog;  // Список параметров и переменных вместе
	private final List<compiler.generation.Instruction> instList;  // Список инструкций
	private final List<Integer> varList;
	private int PC = 0;  // Счетчик команд

	public Method() {
		catalog = new ArrayList<>();
		instList = new ArrayList<>();
		varList = new ArrayList<>();
	}

	public int getPC() {
		return PC;
	}

	public void setPC(int pc) {
		PC = pc;
	}

	public List<compiler.generation.Instruction> getInstList() {
		return instList;
	}

	public void setInstList(compiler.generation.Instruction inst) {
		instList.add(inst);
	}

	public List<xNote> getCatalog() {
		return catalog;
	}

	public void setCatalog(List<xNote> catalog) {
		this.catalog.addAll(catalog);
	}

	public List<Integer> getVarList() {
		return varList;
	}

	public compiler.generation.Instruction getInstruction(int n) {
		return instList.get(n);
	}

}
