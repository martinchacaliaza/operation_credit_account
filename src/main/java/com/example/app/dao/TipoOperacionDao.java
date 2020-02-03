package com.example.app.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.example.app.models.TypeOperationCredit;

public interface TipoOperacionDao extends ReactiveMongoRepository<TypeOperationCredit, String> {

}
