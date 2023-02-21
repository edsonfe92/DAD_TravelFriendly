package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.model.Trip;
import com.example.demo.model.User;

public interface TripRepository extends JpaRepository <Trip, Long>{

	//Optional<Trip> findByOrigin(String origin);
	List<Optional<Trip>> findByOrigin(String origin);
	List<Optional<Trip>> findByDestiny(String destiny);
	List<Optional<Trip>> findByDate(String date);
	
}
