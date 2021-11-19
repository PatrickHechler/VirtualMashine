package de.hechler.patrick.vm.objects;

import de.hechler.patrick.vm.interfaces.VMBreakpoint;
import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMMethod;

public class BreakpointImpl implements VMBreakpoint {
	
	public final VMClass  type;
	public final VMMethod method;
	public final int      index;
	
	public BreakpointImpl(VMClass type, VMMethod method, int index) {
		this.type = type;
		this.method = method;
		this.index = index;
	}

	@Override
	public VMClass type() {
		return type;
	}
	
	@Override
	public VMMethod method() {
		return method;
	}
	
	@Override
	public int index() {
		return index;
	}
	
}
