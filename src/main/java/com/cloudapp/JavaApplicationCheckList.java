package com.cloudapp;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

@Component
public class JavaApplicationCheckList implements CheckListInterface {

	@Autowired
	GitHubApi gitHubApi;

	/**
	 * check in the pom.xml whether its War/Jar/File
	 * 
	 * @param checkListMap
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void checkListForJava(String repoOwnerName, String repoName, Map<String, Set<String>> checkListMap)
			throws IOException, URISyntaxException {
		List<Map> response = gitHubApi.getContent(repoOwnerName, repoName);
		for (Map fileMetaData : response) {
			// Get file name & raw file download URL from response.
			String downloadUrl = (String) fileMetaData.get("download_url");
			// Fetch pom.xml file for analyzing the deployment strategy in cloud.
			if (downloadUrl != null && downloadUrl.contains("pom")) {
				/*
				 * Get file content as string Using Apache commons IO to read content from the
				 * remote URL. Any other HTTP client library can be used here.
				 */
				String fileContent = IOUtils.toString(new URI(downloadUrl), Charset.defaultCharset());
				pacakageType(fileContent, checkListMap);
				dbDependency(fileContent, checkListMap);
				messagingEvent(fileContent, checkListMap);
			}
			// Fetch Docker file for analyzing the deployment strategy in cloud.
			if (downloadUrl != null && downloadUrl.contains("Docker")) {
				System.out.println("Inside Docker");
				String fileContent = IOUtils.toString(new URI(downloadUrl), Charset.defaultCharset());
				dockerCheck(downloadUrl, fileContent, checkListMap);
			} else {
				checkListMap.get("Java").add("NO_DOCKER");
			}
		}
	}

	/**
	 * The way to check the packaging type of a Java application
	 * 
	 * @param fileContent
	 * @param map
	 * @throws ParserConfigurationException
	 * @throws IOException
	 * @throws SAXException
	 */
	@Override
	public void pacakageType(String fileContent, Map<String, Set<String>> map) {
		if (fileContent.contains("packaging")) {
			map.get("Java").add("PACKAGING");
			System.out.println("Packaging is declared in application : PACKAGING");
		} else {
			System.out.println("No packaging is declared in application, default is jar");
			map.get("Java").add("PACKAGING");
		}
	}

	/**
	 * The way to check the DB connections established in a Java application
	 * 
	 * @param fileContent
	 * @param map
	 */
	@Override
	public void dbDependency(String fileContent, Map<String, Set<String>> map) {
		if (fileContent.contains("mysql-connector-java")) {
			map.get("Java").add("DB_COMPONENT");
			System.out.println("Packaging is declared in application : MYSQL");
		}else if (fileContent.contains("postgresql")) {
			map.get("Java").add("DB_COMPONENT");
			System.out.println("Packaging is declared in application : postgresql");
		}else if (fileContent.contains("mongodb")) {
			map.get("Java").add("DB_COMPONENT");
			System.out.println("Packaging is declared in application : mongodb");
		}else if (fileContent.contains("ojdbc")) {
			map.get("Java").add("DB_COMPONENT");
			System.out.println("Packaging is declared in application : Oracle");
		}else {
			map.get("Java").add("NO_DB_COMPONENT");
			System.out.println("No DB connection established in the application");
		}
	}

	/**
	 * The way to check the any message system established in a Java application
	 * 
	 * @param fileContent
	 * @param map
	 */
	@Override
	public void messagingEvent(String fileContent, Map<String, Set<String>> map) {
		if (fileContent.contains("kafka")) {
			map.get("Java").add("MESSAGE_EVENT");
			System.out.println("Message system established in the application : Apache Kakfa");
		}else if (fileContent.contains("activemq")) {
			map.get("Java").add("MESSAGE_EVENT");
			System.out.println("No message system established in the application : ActiveMQ");
		}else {
			map.get("Java").add("NO_MESSAGE_EVENT");
			System.out.println("No message system established in the application");
		}
	}

	/**
	 * The way to check the DockerFile is there in the Java application
	 * 
	 * @param downloadUrl
	 * @param fileContent
	 * @param map
	 */
	@Override
	public void dockerCheck(String downloadUrl, String fileContent, Map<String, Set<String>> map) {
		if (fileContent.contains("java") && downloadUrl.contains("Docker")) {
			map.get("Java").add("DOCKER");
			System.out.println("Application is building image using Docker");
		} else {
			map.get("Java").add("NO_DOCKER");
			System.out.println("Application is not integrated with Docker");
		}
	}

}
