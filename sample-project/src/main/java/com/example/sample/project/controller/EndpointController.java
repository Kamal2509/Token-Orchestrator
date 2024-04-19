package com.example.sample.project.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.sample.project.Entity.token;
import com.example.sample.project.repo.tokenrepo;

@RestController
public class EndpointController {
	@Autowired
	private tokenrepo tokenrepo1;
	Map<String, Boolean> keys;

// TO Create Tokens
	@PostMapping("/keys")
	public token Keyproduce() {
		token t = new token();
		String s = UUID.randomUUID().toString();
		t.setToken(s);
		t.setStatus(true);
		t.setTimestamp(LocalDateTime.now());

		token t1 = this.tokenrepo1.save(t);
		return t1;
	}

// To get token to client
	@GetMapping("/keys")
	public ResponseEntity<String> getkey() {
		token t = this.tokenrepo1.findRandomKey();
		if (t == null) {
			return ResponseEntity.status(404).build();
		} else {
			t.setStatus(false);
			t.setTimestamp(LocalDateTime.now());
			this.tokenrepo1.save(t);
			return ResponseEntity.of(Optional.of(t.getToken()));
		}
	}

// to Update key status	
	@PutMapping("/keys/{id}")
	public void updateKey(@PathVariable("id") int id) {
		Optional<token> t = tokenrepo1.findById(id);
		token t1 = t.get();
		t1.setStatus(true);
		t1.setTimestamp(LocalDateTime.now());
		this.tokenrepo1.save(t1);
	}

	// to delete a key from database
	@DeleteMapping("keys/{id}")
	public void deletetoken(@PathVariable("id") int id) {
		this.tokenrepo1.deleteById(id);
	}

//  to update and keep alive
	@PutMapping("/keepalive/{id}")
	public void keepAlive(@PathVariable("id") int id) {
		Optional<token> t = tokenrepo1.findById(id);
		token t1 = t.get();
		t1.setTimestamp(LocalDateTime.now());
		tokenrepo1.save(t1);
	}

//to check and delete from database	
	@Scheduled(fixedDelay = 1000) // to set duration after which program will run again to check
	public void CheckAndDelete() {
		LocalDateTime thresholdtime = LocalDateTime.now().minusMinutes(2); // after 5 minutes all the keys will be
																			// delete
		List<token> inactiveresource = tokenrepo1.findBYLastAccesse(thresholdtime);
		for (token t : inactiveresource) {
			tokenrepo1.delete(t);
		}
	}

// to check and change it status to active	
	@Scheduled(fixedDelay = 1000) // to set duration after which program will run again to check
	public void CheckAndUnblock() {
		LocalDateTime thresholdtime = LocalDateTime.now().minusMinutes(1); // after 5 minutes all the keys will be
																			// delete
		List<token> inactiveresource = tokenrepo1.findBYLastAccesse(thresholdtime);
		for (token t : inactiveresource) {
			t.setStatus(true);
			tokenrepo1.save(t);
		}
	}
}
