package compiler.scan;

import java.io.Serial;
import java.util.ArrayList;
import java.util.List;

public class MethodNote extends Note {

	@Serial
	private static final long serialVersionUID = 1L;
	private final List<xNote> paramList;
	private final List<xNote> varList;
	private final List<xNote> list;

	public MethodNote(String id, String type) {
		super(id, type);
		paramList = new ArrayList<>();
		varList = new ArrayList<>();
		list = new ArrayList<>();
	}
	
	public void putParam(xNote param){
		paramList.add(param);
	}
	
	public void putVar(xNote var){
		varList.add(var);
	}

	public List<xNote> getParamList() {
		return paramList;
	}

	public List<xNote> getVarList() {
		return varList;
	}

	public List<xNote> getList() {
		return list;
	}

	public void setList(List<xNote> list) {
		this.list.addAll(list);
	}

}
