package com.example.app.impl;

import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.controllers.WebClientController;
import com.example.app.dao.OperacionDao;
import com.example.app.models.Client;
import com.example.app.models.OperationCreditAccount;
import com.example.app.models.CreditAccount;
import com.example.app.models.TypeOperation;
import com.example.app.service.OperacionService;
import com.example.app.service.TipoOperacionService;
import com.sistema.app.exception.RequestException;
import com.sistema.app.exception.ResponseStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperacionServiceImpl implements OperacionService {

	
	@Value("${com.bootcamp.gateway.url}")
	String valor;
	
	WebClientController web;
	
	
	@Autowired
	public OperacionDao productoDao;
	
	@Autowired
	public OperacionDao tipoProductoDao;
	
	
	@Autowired
	private TipoOperacionService tipoProductoService;


	@Override
	public Flux<OperationCreditAccount> findAllOperacion() {
		return productoDao.findAll();

	}
	
	@Override
	public Mono<OperationCreditAccount> findByIdOperacion(String id) {
		return productoDao.findById(id);

	}
	
	@Override
	public Flux<OperationCreditAccount> findAllByIdOperacionDniCliente(String dni) {
		return productoDao.viewDniCliente(dni);

	}

	@Override
	public Mono<OperationCreditAccount> saveOperacion(OperationCreditAccount producto)
	{
	return productoDao.save(producto);
	}
	
	

	@Override
	public Mono<OperationCreditAccount> saveOperacionRetiro(OperationCreditAccount operacion) 
	{
	
		 Mono<CreditAccount> oper= web.cosumo(operacion.getNumero_cuenta(), operacion.getMontoPago());
				/* WebClient
					.builder()
					.baseUrl("http://"+valor+"/productos_creditos/api/ProductoCredito/")
					.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.build().put().uri("/consumo/"+operacion.getNumero_cuenta()+"/"+operacion.getMontoPago()).retrieve()
					.bodyToMono(CreditAccount.class)
					.log();*/
				 
			 return oper.flatMap(c->{
				 if(c.getNumero_cuenta().equalsIgnoreCase("")) 
					{	
						return Mono.error(new InterruptedException("No existe Numero de tarjeta.."));
					}
					
			TypeOperation tipo=new TypeOperation();
			tipo.setIdTipo("1");	
			tipo.setDescripcion("pago");
			operacion.setTipoOperacion(tipo);
			return productoDao.save(operacion);
		
	});
			
						
	}
	@Override
	public Mono<OperationCreditAccount> saveOperacionDeposito(OperationCreditAccount operacion)
	{
				 Mono<CreditAccount> oper=WebClient
						.builder()
						.baseUrl("http://"+valor+"/productos_creditos/api/ProductoCredito/")
						.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
						.build().put().uri("/pago/"+operacion.getNumero_cuenta()+"/"+operacion.getMontoPago()).retrieve()
						.bodyToMono(CreditAccount.class)
						.log();
				 return oper.flatMap(c->{
						if(c.getNumero_cuenta() == null) 
						{	
							return Mono.error(new InterruptedException("No existe Numero de tarjeta"));
						}
						
				TypeOperation tipo=new TypeOperation();
				
				/*tipo.setIdTipo(operacion.getTipoOperacion().getIdTipo());	
				tipo.setDescripcion(operacion.getTipoOperacion().getDescripcion());*/
				tipo.setIdTipo("1");	
				tipo.setDescripcion("pago");
				operacion.setTipoOperacion(tipo);
				return productoDao.save(operacion);
			
		});
	}

	@Override
	public Flux<OperationCreditAccount> consultaMovimientos(String dni, String numTarjeta) {
		
		return productoDao.consultarMovimientos(dni, numTarjeta);
	}
	
	

}
