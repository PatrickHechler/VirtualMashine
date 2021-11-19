package de.hechler.patrick.vm.objects;

import java.util.Collections;
import java.util.List;

import de.hechler.patrick.vm.interfaces.VMMethodHead;
import de.hechler.patrick.vm.interfaces.VMParameter;

public class MethodHeadImpl <PARAMETER extends VMParameter<?>> implements VMMethodHead <PARAMETER> {
	
	public final String           name;
	public final List <PARAMETER> params;
	
	public MethodHeadImpl(String name) {
		this(name, Collections.emptyList());
	}
	
	public MethodHeadImpl(String name, List <PARAMETER> params) {
		this.name = name;
		this.params = params;
	}
	
	@Override
	public String name() {
		return name;
	}
	
	@Override
	public List <PARAMETER> params() {
		return params;
	}
	
	
}
