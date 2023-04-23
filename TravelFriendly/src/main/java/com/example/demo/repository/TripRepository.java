package com.example.demo.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Trip;
import com.example.demo.model.User;


@CacheConfig(cacheNames="tusViajes")
public interface TripRepository extends JpaRepository <Trip, Long>{
	
	//desaloja la caché ENTERA cuando salvas un viaje (esto es porque 
	//cuando la caché se modifica se tiene que invalidar.
	@CacheEvict(allEntries=true)
	Trip save(Trip trip);
	//Habilita la caché (guarda todos los viajes por id de conductor para 
	
	// Guarda el resultado del método en la caché que se haya
	//configurado para la aplicación, asociado a los valores de
	//los parámetros
	@Cacheable
	Optional<Trip> findByConductor_Id(long id);
	@Cacheable
	List<Trip> findAll();

	List<Optional<Trip>> findByOrigin(String origin);
	List<Optional<Trip>> findByDestiny(String destiny);
	List<Optional<Trip>> findByDate(String date);
//	select nombre, asunto, comentario
//	from anuncios
//	where nombre = nombreAnuncio
	
	
	
	
	
	/*@Query("UPDATE dad.trip SET sites = sites-1 WHERE id = t.getId();")
	void updateSites(Trip t);*/
	
	/*@Modifying(clearAutomatically = true)
	@Query("update trip u set u.sites = sitesNew where u.id=i")
	void updateSites(@Param("i")int i, @Param("sitesNew")int sitesNew);*/

	
}
