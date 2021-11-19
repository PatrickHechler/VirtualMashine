package de.hechler.patrick.vm.interfaces;


public interface VMBreakpoint<CLASS extends VMClass <?, ?, ?, ?>, METHOD extends VMMethod <?, ?>> {
	
	CLASS type();
	
	METHOD method();
	
	int index();
	
}
