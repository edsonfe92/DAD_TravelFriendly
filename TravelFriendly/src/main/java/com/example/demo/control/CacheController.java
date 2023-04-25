package com.example.demo.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CacheController {
	
	@Autowired
	private CacheManager cacheManager;
	//devuelve el contenido actual de la cache de viajes en LA SIGUIENTE URL
	@GetMapping(value="/cacheViajes")
	public Map<Object, Object> getCacheContentViajes() {
		ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
		ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("viajes");
		return cache.getNativeCache();
	}
	//devuelve el contenido actual de la cache de opiniones en LA SIGUIENTE URL
	@GetMapping(value="/cacheOpiniones")
	public Map<Object, Object> getCacheContentOpiniones() {
		ConcurrentMapCacheManager cacheMgr = (ConcurrentMapCacheManager) cacheManager;
		ConcurrentMapCache cache = (ConcurrentMapCache) cacheMgr.getCache("opiniones");
		return cache.getNativeCache();
	}
}
