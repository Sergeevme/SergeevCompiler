package compiler.generation;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class File implements Serializable, Codes {

	@Serial
	private static final long serialVersionUID = 1L;
	private final Map<String, Method> methods;
	private Method mainMethod;

	public File() {
		methods = new LinkedHashMap<>();
		mainMethod = new Method();
	}

	public Map<String, Method> getMethods() {
		return methods;
	}

	public void addMethod(String name, Method method) {
		methods.put(name, method);
	}

	public Method getMainMethod() {
		return mainMethod;
	}

	public void setMainMethod(Method method) {
		mainMethod = method;
	}

}
