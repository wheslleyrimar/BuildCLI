package org.buildcli.model;

import java.util.HashMap;

public class DependencyHashMap extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;

	public DependencyHashMap() {
		super();
		this.put("groupId", null);
		this.put("artifactId", null);
		this.put("version", null);
		this.put("type", null);
		this.put("scope", null);
		this.put("optional", null);
	}
	
	

}
