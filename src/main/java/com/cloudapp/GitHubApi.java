package com.cloudapp;

import static com.cloudapp.ScoringAlgoConstants.GITHUB_API_BASE_URL;
import static com.cloudapp.ScoringAlgoConstants.GITHUB_API_SEARCH_CODE_PATH;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;

@Component
public class GitHubApi {

	private static Gson gson = new Gson();
	private static String EXTENSION = "extension:";
	private static String REPO = "repo:";

	/**
	 * Method to check the programming language of the application
	 * 
	 * @param repoOwnerName
	 * @param repoName
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 * URL used - https://api.github.com/search/code?q=extension:java+repo:shashirajraja/onlinebookstore
	 */
	public String getAppProgramLanguage(String repoOwnerName, String repoName) throws IOException, URISyntaxException {
		Map extensionSearchResult = new HashMap<>();
		String result = "";
		Map<String, String> extensionType = getExtensionType();
		for (Entry<String, String> extType : extensionType.entrySet()) {
			extensionSearchResult = makeRESTCall(GITHUB_API_BASE_URL + GITHUB_API_SEARCH_CODE_PATH + EXTENSION
					+ extType.getValue() + "+" + REPO + repoOwnerName + "/" + repoName);
			if (extensionSearchResult != null && !extensionSearchResult.isEmpty()) {
				if (gson.toJsonTree(extensionSearchResult).getAsJsonObject().get("items").getAsJsonArray().size() > 0) {
					result = extType.getKey();
					break;
				}
			}

		}
		return result;
	}
	
	/**
	 * Method to create a map which contains different types of extension
	 * @return
	 */
	private Map<String, String> getExtensionType() {
		Map<String, String> extensionTypeMap = new HashMap<String, String>();
		extensionTypeMap.put("Java", ".java");
		extensionTypeMap.put("Python", ".py");
		return extensionTypeMap;
	}

	/**
	 * Get the content of the application
	 * 
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public List<Map> getContent(String repoOwnerName, String repoName) throws IOException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		List<Map> response = restTemplate.getForObject(
				GITHUB_API_BASE_URL + "repos/" + repoOwnerName + "/" + repoName + "/contents/", List.class);
		return response;
	}


	/**
	 * This method will make a REST GET call for this URL using Apache http client &
	 * fluent library.
	 * 
	 * Then parse response using GSON & return parsed Map.
	 */
	public static Map makeRESTCall(String restUrl) throws ClientProtocolException, IOException {
		Request request = Request.Get(restUrl);

		Content content = request.execute().returnContent();
		String jsonString = content.asString();

		// To print response JSON, using GSON. Any other JSON parser can be used here.
		Map jsonMap = gson.fromJson(jsonString, Map.class);
		return jsonMap;
	}
}
