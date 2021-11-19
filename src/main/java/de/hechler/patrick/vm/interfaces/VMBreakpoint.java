package de.hechler.patrick.vm.interfaces;


public interface VMBreakpoint {
	
	VMClass type();
	
	VMMethod method();
	
	int index();
	
}
