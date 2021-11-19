package de.hechler.patrick.vm.interfaces;

import java.util.List;

public interface VMStackEntry <COMMAND extends VMCommand> {
	
	int instructionPointer();
	
	List <COMMAND> commands();
	
}
