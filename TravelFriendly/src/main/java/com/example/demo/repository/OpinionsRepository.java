package com.example.demo.repository;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.Opinions;
import com.example.demo.model.Trip;

import java.util.Optional;


@CacheConfig(cacheNames="opiniones")
public interface OpinionsRepository extends JpaRepository <Opinions, Long>{
	@CacheEvict(allEntries=true)
	Opinions save(Opinions op);
}
