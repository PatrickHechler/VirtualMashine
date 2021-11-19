package de.hechler.patrick.vm.objects;

import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMVariable;


public class VariableImpl <VARIABLE_HEAD, CLASS extends VMClass <?, ?, ?, ?>> implements VMVariable <VARIABLE_HEAD, CLASS> {
	
	public final CLASS  type;
	public final String name;
	
	public VariableImpl(CLASS type, String name) {
		this.type = type;
		this.name = name;
	}
	
	@Override
	public CLASS type() {
		return this.type;
	}
	
	@Override
	public String name() {
		return this.name;
	}
	
}
