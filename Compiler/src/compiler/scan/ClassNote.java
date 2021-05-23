package compiler.scan;

import java.io.Serial;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClassNote extends Note {
	@Serial
	private static final long serialVersionUID = 1L;
	private final Map<String, MethodNote> methodList;
	private final Map<String, xNote> fieldList;

	public ClassNote(String name, String type) {
		super(name, type);
		methodList = new LinkedHashMap<>();
		fieldList = new LinkedHashMap<>();
	}

	public void putOnField(String fName, xNote field) {
		fieldList.put(fName, field);
	}

	public void putOnMethod(String mName, MethodNote currentMethod) {
		methodList.put(mName, currentMethod);
	}

	public Map<String, MethodNote> getMethodList() {
		return methodList;
	}
}
