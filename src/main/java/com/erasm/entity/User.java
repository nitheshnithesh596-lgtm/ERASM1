package com.erasm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="users")
public class User {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long userId;
	
	
	@Column(nullable =false)
	private String username;
	
	@Column(nullable =false ,unique=true)
	private String email;
	
	
	@Column(nullable =false)
	private String password;
	
	
	@ManyToOne
	@JoinColumn(name ="role_id")
	private Role role;
	
	public User() {
		
		
	}

	public User(Long userId, String username, String email, String password, Role role) {
		
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	

}
