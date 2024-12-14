package com.example.rest_client_example;

import com.example.rest_client_example.model.Post;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestClientExampleApplication {

	public static void main(String[] args) {
//		SpringApplication.run(RestClientExampleApplication.class, args);
		Post post = Post.of(2, 3, "test", "sss");
		/*
		* get list test
		* */
//		RestNetwork.getList(RestNetwork.JSON_PLACE_HOLDER+RestNetwork.POSTS, Post.class, null).forEach(System.out::println);
//		RestNetwork.getList(RestNetwork.JSON_PLACE_HOLDER+RestNetwork.POSTS, Post.class, Map.of("userId",String.valueOf(4))).forEach(System.out::println);

//		Post getResponse = RestNetwork.get(RestNetwork.JSON_PLACE_HOLDER+RestNetwork.POSTS + "/" + 2, Post.class);
//		System.out.println("getResponse = " + getResponse);

//		Post postRequestResponse = RestNetwork.post(RestNetwork.JSON_PLACE_HOLDER + RestNetwork.POSTS, post, Post.class, null);
//		System.out.println("postRequestResponse = " + postRequestResponse);

//		Post putRequestResponse = RestNetwork.put(RestNetwork.JSON_PLACE_HOLDER + RestNetwork.POSTS, post, 4, Post.class, null);
//		System.out.println("putRequestResponse = " + putRequestResponse);

//		ResponseEntity<Void> responseEntity = RestNetwork.delete(RestNetwork.JSON_PLACE_HOLDER + RestNetwork.POSTS, 8);
//		System.out.println("responseEntity.getStatusCode().value() = " + responseEntity.getStatusCode().value());
	}

}
