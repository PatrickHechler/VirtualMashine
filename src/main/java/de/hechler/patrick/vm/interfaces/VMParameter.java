package de.hechler.patrick.vm.interfaces;


public interface VMParameter <CLASS extends VMClass <?, ?, ?, ?>> {
	
	CLASS type();
	
	String name();
	
}
