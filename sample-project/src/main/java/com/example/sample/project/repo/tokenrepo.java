package com.example.sample.project.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.sample.project.Entity.token;

public interface tokenrepo extends CrudRepository<token, Integer> {
	@Query(value = "SELECT * FROM token WHERE status=true ORDER BY RAND() LIMIT 1", nativeQuery = true)
	token findRandomKey();

	@Query(value = "SELECT * FROM token WHERE timestamp<= :thresholdtime", nativeQuery = true)
	List<token> findBYLastAccesse(LocalDateTime thresholdtime);
}
