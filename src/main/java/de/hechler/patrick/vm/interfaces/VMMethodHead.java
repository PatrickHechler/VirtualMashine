package de.hechler.patrick.vm.interfaces;

import java.util.List;

public interface VMMethodHead <PARAMETER extends VMParameter <?>> {
	
	String name();
	
	List <PARAMETER> params();
	
}
