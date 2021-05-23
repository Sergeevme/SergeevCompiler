package compiler.scan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope {

	private int next = 0; // Следующий child
	private Scope parent; // Родительская область
	private final List<Scope> children; // Children область
	private final Map<String, Note> records; // ID

	private Note containingClass;

	public Scope(Object object) {
		children = new ArrayList<>();
		records = new HashMap<>();
		if (object != null)
			parent = (Scope) object;
	}

	public Scope nextChild() {
		// Создает нового ребенка по запросу
		Scope nextC;
		if (next >= children.size()) {
			// Ребенок не существует
			nextC = new Scope(this); // ==> Создать новую область
			children.add(nextC);
		} else {
			// Ребенок существует
			nextC = children.get(next);
		}
		next++;
		return nextC;
	}

	public Scope getParent() {
		return parent;
	}

	public void put(String key, Note item) {
		records.put(key, item);
	}

	public Note watch_up(String key) {
		if (records.containsKey(key))  // Проверить, входит ли в текущую область
			return records.get(key);
		else {  // Перейти к охватывающей / родительской области
			if (parent == null)
				return null;  // Идентификатора нет в таблице
			else
				return parent.watch_up(key);  // Делегировать запрос на вложение
		}
	}

	public void printScope() {
		for (Map.Entry<String, Note> entry : records.entrySet()) {
			String leftAlignFormat = "| %-27s | %-19s | %-27s|%n";
			String key = entry.getKey();
			Note value = entry.getValue();
			String recordClasses = value.getClass().getSimpleName();
			System.out.format(leftAlignFormat, key,value.getType(),recordClasses);
		}
		for (Scope scope : children) {
			scope.printScope();
		}

	}

	public void resetScope() { // Должен вызываться после каждого обхода
		next = 0;
		for (Scope child : children) child.resetScope();
	}

	public void setContainingClass(Note note) {
		containingClass = note;
	}

	public Note getContainingClass() {
		return containingClass;
	}
}
