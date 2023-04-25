package com.example.demo;

import org.springframework.amqp.core.Queue;

import java.util.Collections;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

@SpringBootApplication(scanBasePackages={
		"es.codeurjc.web.service", "com.example"})
@EnableScheduling
@EnableCaching //Activamos caching
//@EnableHazelcastHttpSession
public class TravelFriendlyApplication {
	
	public static void main(String[] args) {
		
		SpringApplication.run(TravelFriendlyApplication.class, args);
		
	}
	@Bean
	public Queue myQueue() {
    	return new Queue("messages", false);
	}
	//El cache manager nos sirve para comprobar que se aloja o desaloja en la caché
	//basicamente un depurador para ver si funciona
	
	/*
	 @Bean
	    public Config config() {

	        Config config = new Config();

	        JoinConfig joinConfig = config.getNetworkConfig().getJoin();

	        joinConfig.getMulticastConfig().setEnabled(true);
	        //joinConfig.getTcpIpConfig().setEnabled(true).setMembers(Collections.singletonList("127.0.0.1"));

	        return config;
	    }*/
	 
	 @Bean
	    public CacheManager cacheManager() {
			final Log LOG = LogFactory.getLog(this.getClass());
			LOG.info("Activating cache...");
	    	return new ConcurrentMapCacheManager("viajes","opiniones");
	    }
}
