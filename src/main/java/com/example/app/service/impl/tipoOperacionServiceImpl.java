package com.example.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.models.TypeOperationCredit;
import com.example.app.repository.TipoOperacionDao;
import com.example.app.service.TipoOperacionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class tipoOperacionServiceImpl implements TipoOperacionService{

	
	@Autowired
	public TipoOperacionDao  tipoProductoDao;
	
	@Override
	public Flux<TypeOperationCredit> findAllTipoproducto()
	{
	return tipoProductoDao.findAll();
	
	}
	@Override
	public Mono<TypeOperationCredit> findByIdTipoProducto(String id)
	{
	return tipoProductoDao.findById(id);
	
	}
	
	@Override
	public Mono<TypeOperationCredit> saveTipoProducto(TypeOperationCredit tipoCliente)
	{
	return tipoProductoDao.save(tipoCliente);
	}
	
	@Override
	public Mono<Void> deleteTipo(TypeOperationCredit tipoProducto) {
		return tipoProductoDao.delete(tipoProducto);
	}
	
}
