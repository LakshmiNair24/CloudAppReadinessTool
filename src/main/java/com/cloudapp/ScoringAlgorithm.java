package com.cloudapp;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

public class ScoringAlgorithm {

	@Autowired
	GitHubApi gitHubApi;

	@Autowired
	JavaApplicationCheckList javaApplicationCheckList;

	public Map<String, Set<String>> checkListMap = new HashMap<>();
	private static String REPO_OWNER_NAME = "shashirajraja";
	private static String REPO_NAME = "onlinebookstore";

	/**
	 * Check the application programming language
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public void baseCriteria() throws ClientProtocolException, IOException, URISyntaxException {
		String programmingLanguage = gitHubApi.getAppProgramLanguage(REPO_OWNER_NAME, REPO_NAME);
		if (programmingLanguage.equals("Java")) {
			checkListMap.put("Java", new HashSet<>());
			scoreForJava();
			System.out.println("Its a " + programmingLanguage + " application");
		}
	}

	/**
	 * Score Calculation Algorithm
	 * The algorithm calculates the score for application which can depoly on AWS services
	 * such as EC2, AWS Elastic Beanstalk
	 * @return
	 */
	private Map<String, Integer> scoreForJava() {
		Map<String, Integer> deploymentStrategyMap = new HashMap<String, Integer>();
		try {
			javaApplicationCheckList.checkListForJava(REPO_OWNER_NAME, REPO_NAME, checkListMap);
			Set<String> set = checkListMap.get("Java");
			System.out.println("Deployment Attributes after analysis" + set);
			for (DeploymentStrategyEnum ds : DeploymentStrategyEnum.getDepolymentStrategy()) {
				int cloud_migration_index = 0;
				if (ds.name().equals(EC2.getClassName())) { // EC2
					caculateEC2Score(deploymentStrategyMap, set, cloud_migration_index);
				}
				if (ds.name().equals(ELASTICBEANSTALK.getClassName())) {
					caculateEBSScore(deploymentStrategyMap, set, cloud_migration_index);
				}
			}
		} catch (IOException | URISyntaxException e) {
			System.out.println("Base Criteria check failed");
		}
		System.out.println("Out Map Result"+deploymentStrategyMap);
		return deploymentStrategyMap;
	}
	/**
	 * Method for calculating the weightage score of EC2 application
	 * @param deploymentStrategyMap
	 * @param set
	 * @param cloud_migration_index
	 * @return
	 */
	private Map<String, Integer> caculateEC2Score(Map<String, Integer> deploymentStrategyMap, Set<String> set,
			int cloud_migration_index) {
		for (String name : EC2.names()) {
			if (set.contains(name)) {
				cloud_migration_index += EC2.valueOf(name).getValue();
			}
		}
		deploymentStrategyMap.put(EC2.getClassName(), cloud_migration_index);
		return deploymentStrategyMap;
	}

	/**
	 * Method for calculating the weightage score of EC2 application
	 * @param deploymentStrategyMap
	 * @param set
	 * @param cloud_migration_index
	 * @return
	 */
	private Map<String, Integer> caculateEBSScore(Map<String, Integer> deploymentStrategyMap, Set<String> set,
			int cloud_migration_index) {
		for (String name : ELASTICBEANSTALK.names()) {
			if (set.contains(name)) {
				if (ELASTICBEANSTALK.valueOf(name).getValue() == 0) {
					deploymentStrategyMap.put(ELASTICBEANSTALK.getClassName(), 0);
					return deploymentStrategyMap;
				}
				cloud_migration_index += ELASTICBEANSTALK.valueOf(name).getValue();
			}
		}
		deploymentStrategyMap.put(ELASTICBEANSTALK.getClassName(), cloud_migration_index);
		return deploymentStrategyMap;
	}

}
