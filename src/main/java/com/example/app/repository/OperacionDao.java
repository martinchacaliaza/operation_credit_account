package com.example.app.repository;


import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;


import com.example.app.models.OperationCreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



public interface OperacionDao extends ReactiveMongoRepository<OperationCreditAccount, String> {

	Flux<OperationCreditAccount> findByDni(String dni);
	
	Flux<OperationCreditAccount> findByDniAndNumeroCuenta(String dni, String numeroCuenta);

}
