package de.hechler.patrick.vm.interfaces;


public interface VMVariable <VARIABLE_HEAD, CLASS extends VMClass <?, ?, ?, ?>> {
	
	CLASS type();
	
	String name();
	
}
