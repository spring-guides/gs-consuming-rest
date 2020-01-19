package com.example.consumingrest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.Id;


/*https://jsonplaceholder.typicode.com/posts
  {
		  "userId": 6,
		  "id": 60,
		  "title": "consequatur placeat omnis quisquam quia reprehenderit fugit veritatis facere",
		  "body": "asperiores sunt ab assumenda cumque modi velit\nqui esse omnis\nvoluptate et fuga perferendis voluptas\nillo ratione amet aut et omnis"
		  },*/
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
	@Id
	private int id;

	private int userId;

	private String title;

	private String body;

	public Quote() {
	}

	public Quote(int id, int userId, String title, String body){
		this.id=id;
		this.userId=userId;
		this.title=title;
		this.body=body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "Quote{" +
				"id=" + id +
				", userId=" + userId +
				", title='" + title + '\'' +
				", body='" + body + '\'' +
				'}';
	}
}