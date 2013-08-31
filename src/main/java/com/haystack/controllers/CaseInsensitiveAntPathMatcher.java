package com.haystack.controllers;

import java.util.Map;

import org.springframework.util.AntPathMatcher;

public class CaseInsensitiveAntPathMatcher extends AntPathMatcher {

	@Override
	protected boolean doMatch(String pattern, String path, 
							  boolean fullMatch, Map<String, String> uriTemplateVariables) {
		
		 return super.doMatch(pattern.toLowerCase(), path.toLowerCase(), 
				 		   	  fullMatch, uriTemplateVariables);
	}
	
	@Override
	public boolean match(String pattern, String string) {
	    return super.match(pattern.toLowerCase(), string.toLowerCase());
	}
	
}
