package com.cloudapp;

import java.util.Map;
import java.util.Set;

/**
 * Scoring Algorithm Functions
 * 
 * @param fileContent
 * @param map
 */
public interface CheckListInterface {

	/**
	 * pacakageType : Check the packaging type of a Java application
	 * 
	 * @param fileContent
	 * @param map
	 */
	public void pacakageType(String fileContent, Map<String, Set<String>> map);

	/**
	 * dbDependency : Check the DB dependency of a Java application
	 * 
	 * @param fileContent
	 * @param map
	 */
	public void dbDependency(String fileContent, Map<String, Set<String>> map);

	/**
	 * messagingEvent : Check the messaging event is present of a Java application
	 * 
	 * @param fileContent
	 * @param map
	 */
	public void messagingEvent(String fileContent, Map<String, Set<String>> map);

	/**
	 * dockerCheck : Check the Docker file is present of a Java application
	 * 
	 * @param downloadUrl
	 * @param fileContent
	 * @param map
	 */
	public void dockerCheck(String downloadUrl, String fileContent, Map<String, Set<String>> map);

}
