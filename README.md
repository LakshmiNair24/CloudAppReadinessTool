# CloudAppReadinessTool
This tool is assess the readiness of an application to migrate to a cloud environment...

User Story:
Design an application to access the readiness off an application to migrate to a cloud environment.

Summary:
CloudAppReadinessTool is a Java spring boot application which is developed for calculating the cloud readiness score of software applications across an organization’s IT portfolio, and to plan for modernization or migration to the Cloud.

In this project, it covers the analysis of a static JAVA application which have a DB connection with both MYSQL and POSTGRES SQL.

Technology Stack Used: Java, Spring Boot, AWS

Application Workflow: 

Public GitHub Repo URL used as input: 
https://github.com/shashirajraja/onlinebookstore

CloudAppReadinessTool Repo GitHub Repo URL:
https://github.com/LakshmiNair24/CloudAppReadinessTool

The workflow consists of the following steps.

    1.Input GitHub Repo URL is called from baseCriteria () method in ScoringAlgorithm.java 

    a.For that, first connected to GitHub repo using the GitHub API by passing the Repo owner name and the repository name dynamically to this below method. 

    gitHubApi.getAppProgramLanguage(REPO_OWNER_NAME, REPO_NAME);

    b.	Make a REST call to the repository.

    makeRESTCall(GITHUB_API_BASE_URL + GITHUB_API_SEARCH_CODE_PATH + EXTENSION
    + extType.getValue() + "+" + REPO + repoOwnerName + "/" + repoName);
				
    c.	Based on the result of the REST call, check the programming language used in the application.
                    
    2.	Once the programming type is identified as “JAVA”, scoreForJava() method is used to analyze the patterns in application that estimate the multi-cloud readiness by detecting the use of platform services.

    a.	Use this below method for checking the DB dependency. 
    public void dbDependency(String fileContent, Map<String, Set<String>> map) {


    b.	Check the Distributed Event Systems
    public void messageEvent(String fileContent, Map<String,Set<String>> map) {

    c.	Check the packaging structure
    public void packageType(String fileContent,Map<String,   
          Set<String>> map) {
          
    d.	Check the platform as a service component (DockerFile)     
    public void dockerCheck(String downloadUrl, String fileContent, Map<String,   Set<String>> map) {

    3.	Based on the patterns available in the application, scoreForJava() method is also used for calculating the score for the application readiness for cloud deployment. 
    
    a.	DeploymentStrategyEnum class contains the services which supports the cloud deployment environment for Java application.
    
    b.	EC2 enum class contains the weightage of each pattern which is required to calculate the score for deploying the application in EC2.
    
    c.	Elastic BeanStalk contains the weightage of each pattern which is required to calculate the score for deploying the application in Elastic BeanStalk
    
    d.	Based on the weightage values & available patterns, derive the scoring calculations in the following methods.
        private Map<String, Integer>caculateEC2Score
        ( Map<String,Integer> deploymentStrategyMap,
		Set<String set, int cloud_migration_index) {
                  
        private Map<String, Integer>caculateEBSScore (   
		Map<String,Integer> deploymentStrategyMap,
        Set<String set, int cloud_migration_index) {
        
    
    Input patterns identified in the application 
    1.	Packaging
    2.	MYSQL DB Connection


    Output 
    Note : The highlighted section in the above screenshot represents the cloud service which we can use for deployment and the readiness score on that service.


















