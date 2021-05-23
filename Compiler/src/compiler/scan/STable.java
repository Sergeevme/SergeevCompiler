package compiler.scan;

public class STable {

	private final compiler.scan.Scope root; // Корень дерева областей видимости
	private compiler.scan.Scope current;
	
	public STable() {
		root = new compiler.scan.Scope(null);
		current = root;
	}

	public void enterScope() { current = current.nextChild(); }

	public void exitScope() { current = current.getParent(); }

	public void put(String key, Note item) {
		current.put(key, item);
	}

	public Note lookup(String key) {
		return current.watch_up(key);
	}

	public void printTable() {
		System.out.println("Symbol Table:");
		System.out.format("+-----------------------------+---------------------+----------------------------+%n");
		System.out.format("|             ID              |         TYPE        |        SCOPE/NOTE          |%n");
		System.out.format("+-----------------------------+---------------------+----------------------------+%n");
		root.printScope();
		System.out.format("+-----------------------------+---------------------+----------------------------+%n");
	}

	public void resetTable() { current = root; root.resetScope(); }

	public void setCurrentClass(Note cRec) {
		current.setContainingClass(cRec);
	}

	public Note getCurrentClass() {
		return current.getContainingClass();
	}
}
