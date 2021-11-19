package de.hechler.patrick.vm.objects;

import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMParameter;


public class ParameterImpl <CLASS extends VMClass <?, ?, ?, ?>> implements VMParameter <CLASS> {
	
	public final CLASS type;
	private String     name;
	
	public ParameterImpl(CLASS type) {
		this(type, null);
	}
	
	public ParameterImpl(CLASS type, String name) {
		this.type = type;
		this.name = name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public CLASS type() {
		return type;
	}
	
	@Override
	public String name() {
		return name;
	}
	
}
