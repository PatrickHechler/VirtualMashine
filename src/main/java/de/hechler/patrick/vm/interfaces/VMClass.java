package de.hechler.patrick.vm.interfaces;

import java.util.Map;

public interface VMClass <METHOD extends VMMethod <?, ?>, METHOD_HEAD extends VMMethodHead <?>, VARIABLE extends VMVariable <?, ?>, VARIABLE_HEAD> {
	
	String name();
	
	Map <METHOD_HEAD, METHOD> methods();
	
	Map <VARIABLE_HEAD, VARIABLE> variables();
	
}
