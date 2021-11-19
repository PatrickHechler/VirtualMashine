package de.hechler.patrick.vm.objects;

import java.util.List;

import de.hechler.patrick.vm.interfaces.VMCommand;
import de.hechler.patrick.vm.interfaces.VMMethod;
import de.hechler.patrick.vm.interfaces.VMMethodHead;
import de.hechler.patrick.vm.interfaces.VMParameter;


public class MethodImpl <COMMAND extends VMCommand, PARAMETER extends VMParameter<?>> extends MethodHeadImpl <PARAMETER> implements VMMethod <COMMAND, PARAMETER> {
	
	private final List <COMMAND> cmds;
	
	
	public MethodImpl(String name, List<PARAMETER> params, List <COMMAND> cmds) {
		super(name, params);
		this.cmds = cmds;
	}

	public MethodImpl(String name, List <COMMAND> cmds) {
		super(name);
		this.cmds = cmds;
	}
	
	@Override
	public List <COMMAND> commands() {
		return cmds;
	}
	
	@Override
	public VMMethodHead <PARAMETER> head() {
		return this;
	}
	
}
