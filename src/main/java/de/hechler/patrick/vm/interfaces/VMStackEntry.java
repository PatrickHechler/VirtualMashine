package de.hechler.patrick.vm.interfaces;

import java.util.List;

public interface VMStackEntry {
	
	int instructionPointer();
	
	List<VMCommand> commands();
	
}
