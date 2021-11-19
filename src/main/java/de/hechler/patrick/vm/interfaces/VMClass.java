package de.hechler.patrick.vm.interfaces;

import java.util.Map;

public interface VMClass <METHOD extends VMMethod <? extends VMCommand, ? extends VMParameter>, METHOD_HEAD extends VMMethodHead <? extends VMParameter>> {
	
	String name();
	
	Map <METHOD_HEAD, METHOD> methods();
	
}
