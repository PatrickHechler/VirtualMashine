package de.hechler.patrick.vm.abstracts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hechler.patrick.vm.interfaces.VMBreakpoint;
import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMCommand;
import de.hechler.patrick.vm.interfaces.VMMethod;
import de.hechler.patrick.vm.interfaces.VMStackEntry;
import de.hechler.patrick.vm.interfaces.VirtaulMashine;
import de.hechler.patrick.zeugs.interfaces.Stack;
import de.hechler.patrick.zeugs.objects.StackImpl;

public abstract class AbstractVirtualMashine <STACK_ENTRY extends VMStackEntry, BREAKPOINT extends VMBreakpoint, CLASS extends VMClass, METHOD extends VMMethod>
	implements VirtaulMashine {
	
	protected final Map <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>> breakpoints;
	protected final Stack <STACK_ENTRY>                                  stack;
	protected volatile boolean                                           ignoreBreakpoints;
	protected volatile boolean                                           runInDiffrentThreats;
	protected volatile VMState                                           state;
	
	
	
	@SuppressWarnings("unchecked")
	protected AbstractVirtualMashine() {
		this((Class <STACK_ENTRY>) VMStackEntry.class);
	}
	
	protected AbstractVirtualMashine(Class <STACK_ENTRY> stackCls) {
		this(new HashMap <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>>(), new StackImpl <>(stackCls), false,
			true);
	}
	
	protected AbstractVirtualMashine(Map <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>> breakpoints, Stack <STACK_ENTRY> stack, boolean ignoreBreakpoints, boolean runInDiffrentThreats) {
		this.breakpoints = breakpoints;
		this.stack = stack;
		this.ignoreBreakpoints = ignoreBreakpoints;
		this.runInDiffrentThreats = runInDiffrentThreats;
		this.state = VMState.suspended;
	}
	
	
	
	@Override
	public void run() {
		boolean start = true;
		if (this.state != VMState.suspended) {
			start = false;
		}
		this.state = VMState.running;
		if (start) {
			if (this.runInDiffrentThreats) {
				new Thread(this::run0).start();
			} else {
				run0();
			}
		}
	}
	
	@Override
	public void step() {
		boolean start = true;
		if (this.state != VMState.suspended) {
			start = false;
		}
		this.state = VMState.step;
		if (start) {
			if (this.runInDiffrentThreats) {
				new Thread(this::run0).start();
			} else {
				run0();
			}
		}
	}
	
	@Override
	public void stepOut() {
		boolean start = true;
		if (this.state != VMState.suspended) {
			start = false;
		}
		this.state = VMState.stepOut;
		if (start) {
			if (this.runInDiffrentThreats) {
				new Thread(this::run0).start();
			} else {
				run0();
			}
		}
	}
	
	@Override
	public void stepOver() {
		boolean start = true;
		if (this.state != VMState.suspended) {
			start = false;
		}
		this.state = VMState.stepOver;
		if (start) {
			if (this.runInDiffrentThreats) {
				new Thread(this::run0).start();
			} else {
				run0();
			}
		}
	}
	
	@Override
	public void suspend() {
		if (this.state != VMState.suspended) {
			this.state = VMState.suspending;
		}
	}
	
	@Override
	public void suspendAndWait() {
		while (this.state != VMState.suspended) {
			this.state = VMState.suspending;
			try {
				Thread.sleep(0);
			} catch (InterruptedException e) {}
		}
	}
	
	@Override
	public boolean isRunning() {
		return this.state != VMState.suspended;
	}
	
	@Override
	public Stack <STACK_ENTRY> getStack() {
		return this.stack;
	}
	
	@Override
	public boolean ignoresBreakpoints() {
		return this.ignoreBreakpoints;
	}
	
	@Override
	public void ignoreBreakpoints(boolean ignore) {
		this.ignoreBreakpoints = ignore;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public BREAKPOINT addBreakpoint(VMBreakpoint breakpoint) {
		Map <METHOD, Map <Integer, BREAKPOINT>> map = this.breakpoints.get(breakpoint.type());
		Map <Integer, BREAKPOINT> map2 = map.get(breakpoint.method());
		return map2.put(breakpoint.index(), (BREAKPOINT) breakpoint);
	}
	
	@Override
	public BREAKPOINT removeBreakpoint(VMBreakpoint breakpoint) {
		Map <METHOD, Map <Integer, BREAKPOINT>> map = this.breakpoints.get(breakpoint.type());
		Map <Integer, BREAKPOINT> map2 = map.get(breakpoint.method());
		return map2.remove(breakpoint.index());
	}
	
	protected void run0() {
		int stacksize = -1;
		VMState lastState = null;
		while (true) {
			VMState newState = this.state;
			if (lastState != newState) {
				stacksize = -1;
			}
			switch (newState) {
			case running:
				while (this.state == VMState.running) {
					executeNext();
				}
				break;
			case stepOut:
				if (VMState.stepOut != lastState) {
					stacksize = this.stack.size();
				}
				executeNext();
				if (this.stack.size() < stacksize) {
					this.state = VMState.suspended;
					return;
				}
				break;
			case stepOver:
				stacksize = this.stack.size();
				executeNext();
				while (this.stack.size() > stacksize && this.state == VMState.stepOver) {
					executeNext();
				}
				this.state = VMState.suspended;
				return;
			case step:
				executeNext();
				this.state = VMState.suspended;
				return;
			case suspending:
				this.state = VMState.suspended;
				return;
			case suspended:
				throw new IllegalStateException("suspended in running");
			default:
				throw new InternalError("unknown state: " + this.state.name());
			}
			lastState = newState;
		}
	}
	
	protected void executeNext() {
		VMStackEntry entry = this.stack.peek();
		List <VMCommand> cmds = entry.commands();
		int ip = entry.instructionPointer();
		this.execute(cmds.get(ip));
	}
	
	public static enum VMState {
		
		running,
		
		suspending,
		
		suspended,
		
		stepOut,
		
		stepOver,
		
		step,
	
	}
	
}
