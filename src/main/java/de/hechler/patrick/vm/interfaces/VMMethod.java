package de.hechler.patrick.vm.interfaces;

import java.util.List;

public interface VMMethod {
	
	String name();
	
	List<VMCommand> commands();
	
}
