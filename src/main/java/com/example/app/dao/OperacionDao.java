package com.example.app.dao;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import com.example.app.models.OperationCreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface OperacionDao extends ReactiveMongoRepository<OperationCreditAccount, String> {

	Flux<OperationCreditAccount> findByDni(String dni);
	
	
	@Query("{ 'dni' : ?0 , 'cuenta_origen' : ?1}")
	Flux<OperationCreditAccount> findByDniAndCuenta_origen(String dni, String cuenta_origen);
	
}
