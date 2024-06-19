
package com.currenciesdirect.gtg.compliance.customchecks.esport;

import java.util.Map;
import java.util.Map.Entry;

/**
 * The Class UpdateRequestBuilder.
 * 
 * @author Rajesh Shinde
 */
public class UpdateQueryBuilder {
	private static final String INLINE_REPLACER = "i#";
	private static final String PARAM_REPLACER = "p#";
	private static final String QUERY_REPLACER = "q#";
	private String script;
	
	/**
	 * Adds the sript.
	 */
	public  void addScript(){
		script = "{\n" +
					" \"script\":\n" +
						" {\n" +
							" "+INLINE_REPLACER+",\n" +
							" "+PARAM_REPLACER+"\n" +
					" },\n" +
					" "+QUERY_REPLACER+"\n"+		
				  "}";
	}
	
	/**
	 * Adds the inline (define substitute name to the field).
	 *
	 * @param inlineMap the inline map
	 */
	public void addInline(Map<String, String> inlineMap) {
		String cons = "ctx._source.";
		StringBuilder inlineBuilder = new StringBuilder();
		String inline = null;
		for (Entry<String, String> entry : inlineMap.entrySet()) {
			inlineBuilder.append(cons).append(entry.getKey()).append("=").append(entry.getValue());
		}
		if (inlineBuilder.length() > 3)
			inline = inlineBuilder.substring(0, inlineBuilder.length() - 1);
		if(script != null) {
		script = script.replace(INLINE_REPLACER, "\"inline\":\"" + inline
				+ "\"");
		}
	}
	
	/**
	 * Adds the params (adds the value that specified in inline as a substitute).
	 *
	 * @param params the params
	 */
	public void addParams(Map<String, String> params) {
		StringBuilder parmBuilder = new StringBuilder();
		parmBuilder.append("\"params\":{\n");
		String paramString = null;
		for (Entry<String, String> entry : params.entrySet()) {
			if (entry.getKey() != null) {
				parmBuilder.append(" \"").append(entry.getKey()).append("\"").append(":").append("\"").append(entry.getValue()).append("\",\n");
			}
		}
		if (parmBuilder.length() > 3)
			paramString = parmBuilder.substring(0, parmBuilder.length() - 2) + "\n";
		paramString += " }";
		if(script != null) {
		script = script.replace(PARAM_REPLACER, paramString);
		}
	}
	
	/**
	 * Adds the conditon.
	 *
	 * @param field the field
	 * @param value the value
	 */
	public void addConditon(String field,String value){
		String query = "\"query\": {\n" +
							" \"term\": {\n";
			query +=" \""+field+"\": \""+value+"\"\n" ;
			query +=						" }\n"+
							" }";
		if(script != null) {
		script = script.replace(QUERY_REPLACER,query);
		}
	}
	
	/**
	 * Builds the.
	 *
	 * @return the string
	 */
	public String build() {
		if (script != null) {
			script = script.replace(INLINE_REPLACER + ",", "");
			if (!script.contains(INLINE_REPLACER))
				script = script.replace(",\n " + PARAM_REPLACER, "");
			script = script.replace(PARAM_REPLACER, "");
			script = script.replace(",\n " + QUERY_REPLACER, "");
		}
		return script;
	}
}
