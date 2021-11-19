package de.hechler.patrick.vm.interfaces;

import de.hechler.patrick.zeugs.interfaces.Stack;

public interface VirtaulMashine <STACK_ENTRY extends VMStackEntry <?>, BREAKPOINT extends VMBreakpoint <?, ?>, COMMAND extends VMCommand> extends Runnable {
	
	@Override
	void run();
	
	void step();
	
	void stepOut();
	
	void stepOver();
	
	void suspend();
	
	void suspendAndWait();
	
	boolean isRunning();
	
	void execute(COMMAND cmd);
	
	Stack <STACK_ENTRY> getStack();
	
	boolean ignoresBreakpoints();
	
	void ignoreBreakpoints(boolean ignore);
	
	BREAKPOINT addBreakpoint(BREAKPOINT breakpoint);
	
	BREAKPOINT removeBreakpoint(BREAKPOINT breakpoint);
	
}
