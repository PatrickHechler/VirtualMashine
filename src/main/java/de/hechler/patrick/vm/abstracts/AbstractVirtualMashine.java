package de.hechler.patrick.vm.abstracts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hechler.patrick.vm.interfaces.VMBreakpoint;
import de.hechler.patrick.vm.interfaces.VMClass;
import de.hechler.patrick.vm.interfaces.VMCommand;
import de.hechler.patrick.vm.interfaces.VMMethod;
import de.hechler.patrick.vm.interfaces.VMParameter;
import de.hechler.patrick.vm.interfaces.VMStackEntry;
import de.hechler.patrick.vm.interfaces.VirtaulMashine;
import de.hechler.patrick.zeugs.interfaces.Stack;
import de.hechler.patrick.zeugs.objects.StackImpl;

public abstract class AbstractVirtualMashine <COMMAND extends VMCommand, STACK_ENTRY extends VMStackEntry <COMMAND>, CLASS extends VMClass <?, ?, ?, ?>, PARAMETER extends VMParameter <CLASS>,
	METHOD extends VMMethod <COMMAND, PARAMETER>, BREAKPOINT extends VMBreakpoint <CLASS, METHOD>>
	implements VirtaulMashine <STACK_ENTRY, BREAKPOINT, COMMAND> {
	
	protected final Map <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>> breakpoints;
	protected final Stack <STACK_ENTRY>                                  stack;
	protected volatile boolean                                           ignoreBreakpoints;
	protected volatile boolean                                           runInDiffrentThreats;
	protected volatile VMState                                           state;
	
	
	
	@SuppressWarnings("unchecked")
	protected AbstractVirtualMashine() {
		this((Class <STACK_ENTRY>) (Class <? extends VMStackEntry <?>>) VMStackEntry.class);
	}
	
	protected AbstractVirtualMashine(Class <STACK_ENTRY> stackCls) {
		this(new HashMap <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>>(), new StackImpl <>(stackCls), false, true);
	}
	
	protected AbstractVirtualMashine(Map <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>> breakpoints, Stack <STACK_ENTRY> stack) {
		this(new HashMap <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>>(), stack, false, true);
	}
	
	protected AbstractVirtualMashine(Class <STACK_ENTRY> stackcls, boolean ignoreBreakpoints, boolean runInDiffrentThreats) {
		this(new HashMap <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>>(), new StackImpl <>(stackcls), ignoreBreakpoints, runInDiffrentThreats);
	}
	
	@SuppressWarnings("unchecked")
	protected AbstractVirtualMashine(boolean ignoreBreakpoints, boolean runInDiffrentThreats) {
		this(new HashMap <CLASS, Map <METHOD, Map <Integer, BREAKPOINT>>>(), (Stack <STACK_ENTRY>) (Stack <? extends VMStackEntry <?>>) new StackImpl <>(VMStackEntry.class), ignoreBreakpoints,
			runInDiffrentThreats);
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
	public BREAKPOINT addBreakpoint(BREAKPOINT breakpoint) {
		VMClass <?, ?, ?, ?> type = breakpoint.type();
		Map <METHOD, Map <Integer, BREAKPOINT>> map;
		if (this.breakpoints.containsKey(type)) {
			map = this.breakpoints.get(type);
		} else {
			map = new HashMap <>();
			this.breakpoints.put((CLASS) type, map);
		}
		Map <Integer, BREAKPOINT> map2;
		VMMethod <?, ?> method = breakpoint.method();
		if (map.containsKey(method)) {
			map2 = map.get(method);
		} else {
			map2 = new HashMap <>();
			map.put((METHOD) method, map2);
		}
		return map2.put(breakpoint.index(), (BREAKPOINT) breakpoint);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public BREAKPOINT removeBreakpoint(BREAKPOINT breakpoint) {
		VMClass <?, ?, ?, ?> type = breakpoint.type();
		Map <METHOD, Map <Integer, BREAKPOINT>> map;
		if (this.breakpoints.containsKey(type)) {
			map = this.breakpoints.get(type);
		} else {
			map = new HashMap <>();
			this.breakpoints.put((CLASS) type, map);
		}
		Map <Integer, BREAKPOINT> map2;
		VMMethod <?, ?> method = breakpoint.method();
		if (map.containsKey(method)) {
			map2 = map.get(method);
		} else {
			map2 = new HashMap <>();
			map.put((METHOD) method, map2);
		}
		return map2.remove(breakpoint.index());
	}
	
	protected void run0() {
		int stacksize = -1;
		while (true) {
			switch (this.state) {
			case running:
				while (this.state == VMState.running) {
					executeNext();
				}
				break;
			case stepOut:
				stacksize = this.stack.size();
				do {
					executeNext();
					if (this.stack.size() < stacksize) {
						this.state = VMState.suspended;
						return;
					}
				} while (this.state == VMState.stepOut);
				break;
			case stepOver:
				stacksize = this.stack.size();
				executeNext();
				while (this.stack.size() > stacksize) {
					if (this.state != VMState.stepOver) {
						break;
					}
					executeNext();
				}
				if (this.state != VMState.stepOver) {
					break;
				}
				this.state = VMState.suspended;
				return;
			case step:
				executeNext();
				if (this.state != VMState.step) {
					break;
				}
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
		}
	}
	
	protected void executeNext() {
		VMStackEntry <COMMAND> entry = this.stack.peek();
		List <COMMAND> cmds = entry.commands();
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
