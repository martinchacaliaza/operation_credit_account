package com.example.app.service;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.app.models.TypeOperationCredit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TipoOperacionService {
	
	Flux<TypeOperationCredit> findAllTipoproducto();
	Mono<TypeOperationCredit> findByIdTipoProducto(String id);
	Mono<TypeOperationCredit> saveTipoProducto(TypeOperationCredit tipoProducto);
	Mono<Void> deleteTipo(TypeOperationCredit tipoProducto);
	
}
