package com.rest_client.rest_client_example;

import com.rest_client.rest_client_example.model.Post;
import com.rest_client.rest_client_example.network.RestNetwork;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class RestClientExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestClientExampleApplication.class, args);
	}

	@PostConstruct
	public void init() {
		String postJsonArray;
		List<Post> posts;

		/*
		 * get Post array
		 * */
		/*System.out.println("1. get all test");
		postJsonArray = RestNetwork.get(RestNetwork.POSTS_API, RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		posts = RestNetwork.parseFromJsonArray(postJsonArray, Post.class);
		posts.forEach(System.out::println);
		System.out.println();*/

		/*
		 * get Post array with request params
		 * */
		/*System.out.println("2. get all by userId");
		postJsonArray = RestNetwork.get(RestNetwork.POSTS_API, Map.of("userId", String.valueOf(1)), RestNetwork.emptyHeaders());
		posts = RestNetwork.parseFromJsonArray(postJsonArray, Post.class);
		posts.forEach(System.out::println);
		System.out.println();*/

		/*
		 * get one Post
		 * */
		/*System.out.println("3. get one test");
		String postJson = RestNetwork.get(RestNetwork.POSTS_API + "/1", RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		Post post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();*/

		/*
		 * create Post
		 * */
		/*System.out.println("4. create test");
		post = new Post(1L, null, "Weather is cold", "Weather is very cold and temperature is 30 gradus");
		postJson = RestNetwork.post(RestNetwork.POSTS_API, post, RestNetwork.emptyParam(), RestNetwork.emptyHeaders(), MediaType.APPLICATION_JSON);
		post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();*/

		/*
		 * save file with POST request
		 * */
		// todo
//		RestNetwork.saveImage();

		/*
		 * update Post
		 * */
		/*System.out.println("6. update test");
		post = new Post(1L, null, "Acer", "Acer company was published new laptop");
		postJson = RestNetwork.put(RestNetwork.POSTS_API + "/1", post, RestNetwork.emptyParam(), RestNetwork.emptyHeaders(), MediaType.APPLICATION_JSON);
		post = RestNetwork.parseFromJson(postJson, Post.class);
		System.out.println("post = " + post);
		System.out.println();*/

		/*
		 * delete Post
		 * */
		/*System.out.println("7. delete test");
		RestNetwork.delete(RestNetwork.POSTS_API + "/1", RestNetwork.emptyParam(), RestNetwork.emptyHeaders());
		System.out.println();*/
	}
}
