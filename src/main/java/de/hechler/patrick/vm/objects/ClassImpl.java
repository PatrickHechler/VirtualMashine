package de.hechler.patrick.vm.objects;

import java.util.Map;

import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMMethod;
import de.hechler.patrick.vm.interfaces.VMMethodHead;
import de.hechler.patrick.vm.interfaces.VMVariable;


public class ClassImpl <METHOD extends VMMethod <?, ?>, METHOD_HEAD extends VMMethodHead <?>, VARIABLE_HEAD, VARIABLE extends VMVariable <VARIABLE_HEAD, VMClass <?, ?, ?, ?>>>
	implements VMClass <METHOD, METHOD_HEAD, VARIABLE, VARIABLE_HEAD> {
	
	public final String                         name;
	private final Map <METHOD_HEAD, METHOD>     methods;
	private final Map <VARIABLE_HEAD, VARIABLE> vars;
	
	public ClassImpl(String name, Map <METHOD_HEAD, METHOD> methods, Map <VARIABLE_HEAD, VARIABLE> vars) {
		this.name = name;
		this.methods = methods;
		this.vars = vars;
	}
	
	@Override
	public String name() {
		return this.name;
	}
	
	@Override
	public Map <METHOD_HEAD, METHOD> methods() {
		return this.methods;
	}
	
	@Override
	public Map <VARIABLE_HEAD, VARIABLE> variables() {
		return this.vars;
	}
	
}
