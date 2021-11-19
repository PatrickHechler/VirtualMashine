package de.hechler.patrick.vm.objects;

import de.hechler.patrick.vm.interfaces.VMBreakpoint;
import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMMethod;

public class BreakpointImpl <CLASS extends VMClass <?, ?, ?, ?>, METHOD extends VMMethod <?, ?>> implements VMBreakpoint <CLASS, METHOD> {
	
	public final CLASS  type;
	public final METHOD method;
	public final int    index;
	
	public BreakpointImpl(CLASS type, METHOD method, int index) {
		this.type = type;
		this.method = method;
		this.index = index;
	}
	
	@Override
	public CLASS type() {
		return this.type;
	}
	
	@Override
	public METHOD method() {
		return this.method;
	}
	
	@Override
	public int index() {
		return this.index;
	}
	
}
