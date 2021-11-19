package de.hechler.patrick.vm.interfaces;

import de.hechler.patrick.zeugs.interfaces.Stack;

public interface VirtaulMashine extends Runnable {
	
	@Override
	void run();
	
	void step();
	
	void stepOut();
	
	void stepOver();
	
	void suspend();
	
	void suspendAndWait();
	
	boolean isRunning();
	
	void execute(VMCommand cmd);
	
	Stack<? extends VMStackEntry> getStack();
	
	boolean ignoresBreakpoints();
	
	void ignoreBreakpoints(boolean ignore);
	
	VMBreakpoint addBreakpoint(VMBreakpoint breakpoint);
	
	VMBreakpoint removeBreakpoint(VMBreakpoint breakpoint);
	
}
