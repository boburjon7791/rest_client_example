package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.model.Post;
import com.rest_client.rest_client_example.network.RestNetwork;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@SpringBootApplication
public class RestClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestClientExampleApplication.class, args);
	}

	@PostConstruct
	public void init(){
		/*
		 * get Post array
		 * */
		System.out.println("1. get all test");
		String postJsonArray = RestNetwork.get(RestNetwork.POSTS_API, RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		List<Post> posts = RestNetwork.parseFromJsonArray(postJsonArray, Post.class);
		posts.forEach(System.out::println);
		System.out.println();

		/*
		 * get Post array with request params
		 * */
		System.out.println("2. get all by userId");
		postJsonArray = RestNetwork.get(RestNetwork.POSTS_API, Map.of("userId",1), RestNetwork.emptyHeaders());
		posts = RestNetwork.parseFromJsonArray(postJsonArray, Post.class);
		posts.forEach(System.out::println);
		System.out.println();

		/*
		* get one Post
		* */
		System.out.println("3. get one test");
		String postJson = RestNetwork.get(RestNetwork.POSTS_API + "/1", RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		Post post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();

		/*
		* get file
		* */
		System.out.println("4. get file test");
		InputStream inputStream = RestNetwork.getMultipart(RestNetwork.IMAGE_URL2, RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		try {
            Files.copy(inputStream, Path.of("test.jpg"), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*
		* create Post
		* */
		System.out.println("5. create test");
		post = new Post(1L, null, "Weather is cold", "Weather is very cold and temperature is 30 gradus");
		postJson = RestNetwork.post(RestNetwork.POSTS_API, post, RestNetwork.emptyParam(), RestNetwork.emptyHeaders(), MediaType.APPLICATION_JSON);
		post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();

		/*
		* save file with POST request
		* */
		// todo: I can not implement

		/*
		* update Post
		* */
		System.out.println("6. update test");
		post = new Post(1L, null, "Acer", "Acer company was published new laptop");
		postJson = RestNetwork.put(RestNetwork.POSTS_API + "/1", post, RestNetwork.emptyParam(), RestNetwork.emptyHeaders(),  MediaType.APPLICATION_JSON);
		post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();

		/*
		* delete Post
		* */
		System.out.println("7. delete test");
		RestNetwork.delete(RestNetwork.POSTS_API+"/1", RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		System.out.println();

		/*
		 * sending request with headers test
		 * in this runnable, java program wait during 10 second, then send requests to self
		 * */
		Runnable runnable = () -> {
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}

			HttpHeaders httpheaders = new HttpHeaders();
			httpheaders.add("authorization", "custom_key");
			httpheaders.add("api_key", "custom_api_key");
			httpheaders.add("api_password", "123");

			System.out.println();
			System.out.println("8. get with headers test");

			httpheaders.set("method", HttpMethod.GET.name());

			String jsonResponse = RestNetwork.get(RestNetwork.HEADER_TEST_URL, RestNetwork.emptyParam(), httpheaders);
			Map map = RestNetwork.parseFromJson(jsonResponse, Map.class);
			System.out.println(map);
			System.out.println();


			System.out.println("9. post with headers test");

			httpheaders.set("method", HttpMethod.POST.name());

			jsonResponse = RestNetwork.post(RestNetwork.HEADER_TEST_URL, "String", RestNetwork.emptyParam(), httpheaders, MediaType.APPLICATION_JSON);
			map = RestNetwork.parseFromJson(jsonResponse, Map.class);
			System.out.println(map);
			System.out.println();


			System.out.println("10. put with headers test");

			httpheaders.set("method", HttpMethod.PUT.name());

			jsonResponse = RestNetwork.put(RestNetwork.HEADER_TEST_URL, "String", RestNetwork.emptyParam(), httpheaders, MediaType.APPLICATION_JSON);
			map = RestNetwork.parseFromJson(jsonResponse, Map.class);
			System.out.println(map);
			System.out.println();


			System.out.println("11. delete with headers test");

			httpheaders.set("method", HttpMethod.DELETE.name());

			RestNetwork.delete(RestNetwork.HEADER_TEST_URL, RestNetwork.emptyParam(), httpheaders);
			System.out.println();


			System.out.println("12. get multipart with headers test");

			httpheaders.set("method", HttpMethod.GET.name());

			InputStream is = RestNetwork.getMultipart(RestNetwork.FILE_URL_WITH_HEADERS, RestNetwork.emptyParam(), httpheaders);

			try {
				Files.copy(is, Path.of("testing-pom.xml"), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			System.out.println();


			System.out.println("13. post with url encoded request");
			Map<String, Object> params = Map.of(
					"id", "25",
					"userId", "15",
					"title", "title for request",
					"body", "body for request"
			);

			httpheaders.set("auth", "testing url encoded post request");

			MultiValueMap<String, Object> multiValueMap = RestNetwork.convertToMultiValueMap(params);
			String postedJson = RestNetwork.post(RestNetwork.FORM_DATA_TEST, multiValueMap, httpheaders);
			Post posted = RestNetwork.parseFromJson(postedJson, Post.class);
			System.out.println("posted = " + posted);
			System.out.println();


			System.out.println("14. put with url encoded request");

			httpheaders.set("auth", "testing url encoded post request");

			params=Map.of(
					"id", "28",
					"userId", "185",
					"title", "title for request 2",
					"body", "body for request 2"
			);
			multiValueMap=RestNetwork.convertToMultiValueMap(params);
			String updatedJson = RestNetwork.put(RestNetwork.FORM_DATA_TEST, multiValueMap, httpheaders);
			Post updated = RestNetwork.parseFromJson(updatedJson, Post.class);
			System.out.println("updated = " + updated);
			System.out.println();
		};
		new Thread(runnable).start();
	}
}
