package com.example.app.service;


import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.example.app.models.OperationCreditAccount;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OperacionService {

	Flux<OperationCreditAccount> findAllOperacion();
	
	Mono<OperationCreditAccount> findByIdOperacion(String id);
	
	Mono<OperationCreditAccount> saveOperacion(OperationCreditAccount producto);
	
	Flux<OperationCreditAccount> findAllByIdOperacionDniCliente(String dni);
		
	Mono<OperationCreditAccount> saveOperacionDeposito(OperationCreditAccount producto);

	Mono<OperationCreditAccount> saveOperacionRetiro(OperationCreditAccount operacion);

	Flux<OperationCreditAccount> findByDniAndNumeroCuenta(String dni, String num_cuenta);

	

}
