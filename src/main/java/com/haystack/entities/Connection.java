package com.haystack.entities;

import java.util.List;

public class Connection extends HaystackEntity {
	
	private String conType;
	private String status;
	
	private List<Context> contexts;
	
	public String getConType() {
		return conType;
	}

	public void setConType(String conType) {
		this.conType = conType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Context> getContexts() {
		return contexts;
	}

	public void setContexts(List<Context> contexts) {
		this.contexts = contexts;
	}
	
	public void addContext(Context context) {
		this.contexts.add(context);
	}
	
	public void removeContext(Integer ctxId) {
		for (Context c : contexts) {
			if (c.getId() == ctxId)
				contexts.remove(c);
		}
	}
	
}
