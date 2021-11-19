package de.hechler.patrick.vm.interfaces;

import java.util.List;

public interface VMMethod <COMMAND extends VMCommand, PARAMETER extends VMParameter> {
	
	String name();
	
	List <COMMAND> commands();
	
	VMMethodHead <PARAMETER> head();
	
}
