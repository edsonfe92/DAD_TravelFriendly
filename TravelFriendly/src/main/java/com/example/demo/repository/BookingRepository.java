package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Booking;
import com.example.demo.model.Trip;

public interface BookingRepository extends JpaRepository <Booking, Long>{
	
	Optional<Booking> findByUser_Id(long id);
}
