package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.Trip;
import com.example.demo.model.User;

public interface TripRepository extends JpaRepository <Trip, Long>{

	Optional<Trip> findByConductor_Id(long id);
	List<Optional<Trip>> findByOrigin(String origin);
	List<Optional<Trip>> findByDestiny(String destiny);
	List<Optional<Trip>> findByDate(String date);
	
	/*@Query("UPDATE dad.trip SET sites = sites-1 WHERE id = t.getId();")
	void updateSites(Trip t);*/
	
	/*@Modifying(clearAutomatically = true)
	@Query("update trip u set u.sites = sitesNew where u.id=i")
	void updateSites(@Param("i")int i, @Param("sitesNew")int sitesNew);*/

	
}
