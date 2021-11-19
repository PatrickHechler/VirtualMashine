package de.hechler.patrick.vm.interfaces;

import java.util.Map;

public interface VMClass {
	
	String name();
	
	Map <VMMethodHead, VMMethod> methods();
	
}
