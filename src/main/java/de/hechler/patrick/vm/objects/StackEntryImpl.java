package de.hechler.patrick.vm.objects;

import java.util.List;

import de.hechler.patrick.vm.interfaces.VMCommand;
import de.hechler.patrick.vm.interfaces.VMStackEntry;


public class StackEntryImpl <COMMAND extends VMCommand> implements VMStackEntry <COMMAND> {
	
	private List <COMMAND> cmds;
	private int            ip;
	
	public StackEntryImpl(List <COMMAND> cmds) {
		this(cmds, 0);
	}
	
	public StackEntryImpl(List <COMMAND> cmds, int ip) {
		this.cmds = cmds;
		this.ip = ip;
	}
	
	public void setInstructionPointer(int ip) {
		this.ip = ip;
	}
	
	@Override
	public int instructionPointer() {
		return this.ip;
	}
	
	@Override
	public List <COMMAND> commands() {
		return this.cmds;
	}
	
}
