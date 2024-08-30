package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.model.Post;
import com.rest_client.rest_client_example.network.RestNetwork;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@EnableAsync
@SpringBootApplication
public class RestClientExampleApplication {

	public static void main(String[] args){
		SpringApplication.run(RestClientExampleApplication.class, args);
		String postJsonArray;
		List<Post> posts;
		Post post;
		String postJson;

		/*
		 * get Post array
		 * */
		/*System.out.println("1. get all test");
		postJsonArray = RestNetwork.get(RestNetwork.POSTS_API, RestNetwork.emptyParam());
		posts = RestNetwork.parseFromJsonArray(postJsonArray, Post.class);
		posts.forEach(System.out::println);
		System.out.println();*/

		/*
		 * get Post array with request params
		 * */
		/*System.out.println("2. get all by userId");
		postJsonArray = RestNetwork.get(RestNetwork.POSTS_API, Map.of("userId", String.valueOf(1)));
		posts = RestNetwork.parseFromJsonArray(postJsonArray, Post.class);
		posts.forEach(System.out::println);
		System.out.println();*/

		/*
		 * get one Post
		 * */
		System.out.println("3. get one test");
//		postJson = RestNetwork.get(RestNetwork.POSTS_API + "s/1", RestNetwork.emptyParam());

		// first way
		ResponseEntity<String> response = RestNetwork.get1(RestNetwork.POSTS_API + "s/1", RestNetwork.emptyParam());
		System.out.println();
		System.out.println("first way");
		System.out.println();

		String body = response.getBody();
		System.out.println("response.getBody() = " + body);

		HttpStatusCode statusCode = response.getStatusCode();
		System.out.println("response.getStatusCode() = " + statusCode);

		HttpHeaders headers = response.getHeaders();
		headers.forEach((s, strings) -> System.out.println(s+" : "+strings));

		// second way
		try {
			System.out.println();
			System.out.println("second way");
			System.out.println();
			String res = RestNetwork.get2(RestNetwork.POSTS_API + "/s/1", RestNetwork.emptyParam());
			System.out.println(res);
		}catch (HttpClientErrorException | HttpServerErrorException e){
			String responseBodyAsString = e.getResponseBodyAsString();
			System.out.println("responseBodyAsString = " + responseBodyAsString);

			HttpStatusCode statusCode1 = e.getStatusCode();
			System.out.println("statusCode1 = " + statusCode1);

			HttpHeaders responseHeaders = e.getResponseHeaders();
			responseHeaders.forEach((s, strings) -> System.out.println(s+" : "+strings));
		}



//		post = RestNetwork.parseFromJson(postJson, Post.class);
//		System.out.println("post = " + post);
//		System.out.println();

		/*
		 * create Post
		 * */
		/*System.out.println("4. create test");
		post = new Post(1L, null, "Weather is cold", "Weather is very cold and temperature is 30 gradus");
		postJson = RestNetwork.post(RestNetwork.POSTS_API, post);
		post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();*/

		/*
		 * save file with POST request
		 * */
		// I have tested in TestController

		/*
		 * update Post
		 * */
		/*System.out.println("6. update test");
		post = new Post(1L, null, "Acer", "Acer company was published new laptop");
		postJson = RestNetwork.put(RestNetwork.POSTS_API + "/1", post);
		post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();*/

		/*
		 * delete Post
		 * */
		/*System.out.println("7. delete test");
		RestNetwork.delete(RestNetwork.POSTS_API + "/1", RestNetwork.emptyParam());
		System.out.println();*/
	}

}

