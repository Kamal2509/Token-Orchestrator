package com.example.sample.project.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class token {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private int id;
	private String Token;
	private LocalDateTime timestamp;
	private Boolean Status;
	
	public String getToken() {
		return Token;
	}
	public void setToken(String token) {
		this.Token = token;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public Boolean getStatus() {
		return Status;
	}
	public void setStatus(Boolean status) {
		Status = status;
	}
	
}
