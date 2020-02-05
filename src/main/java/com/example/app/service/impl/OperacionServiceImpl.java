package com.example.app.service.impl;

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
import com.example.app.exception.RequestException;
import com.example.app.exception.ResponseStatus;
import com.example.app.models.OperationCreditAccount;
import com.example.app.models.CreditAccount;
import com.example.app.models.TypeOperationCredit;
import com.example.app.repository.OperacionDao;
import com.example.app.service.OperacionService;
import com.example.app.service.TipoOperacionService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OperacionServiceImpl implements OperacionService {

	
	@Value("${com.bootcamp.gateway.url}")
	String valor;
	
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
		return productoDao.findByDni(dni);

	}

	@Override
	public Mono<OperationCreditAccount> saveOperacion(OperationCreditAccount producto)
	{
	return productoDao.save(producto);
	}
	
	@Override
	public Mono<OperationCreditAccount> saveOperacionRetiro(OperationCreditAccount operacion) 
	{
	
		 Mono<CreditAccount> oper= 	WebClient
					.builder()
					.baseUrl("http://"+valor+"/productos_creditos/api/ProductoCredito/")
					//.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
					.build().put().uri("/consumo/"+operacion.getNumeroCuenta()+"/"+
					operacion.getMontoPago()+"/"+operacion.getCodigoBancario()).retrieve()
					.bodyToMono(CreditAccount.class)
					.log();
				 
			 return oper.flatMap(c->{
				 if(c.getNumeroCuenta().equalsIgnoreCase("")) 
					{	
						return Mono.error(new InterruptedException("No existe Numero de tarjeta.."));
					}
					
			TypeOperationCredit tipo=new TypeOperationCredit();
			tipo.setIdTipo("2");	
			tipo.setDescripcion("consumo");
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
						.build().put().uri("/pago/"+operacion.getNumeroCuenta()+"/"+
						 operacion.getMontoPago()+"/"+operacion.getCodigoBancario()).retrieve()
						
						.bodyToMono(CreditAccount.class)
						.log();
				 return oper.flatMap(c->{
						if(c.getNumeroCuenta() == null) 
						{	
							return Mono.error(new InterruptedException("No existe Numero de tarjeta"));
						}
						
				TypeOperationCredit tipo=new TypeOperationCredit();
				
				/*tipo.setIdTipo(operacion.getTipoOperacion().getIdTipo());	
				tipo.setDescripcion(operacion.getTipoOperacion().getDescripcion());*/
				tipo.setIdTipo("1");	
				tipo.setDescripcion("pago");
				operacion.setTipoOperacion(tipo);
				return productoDao.save(operacion);
			
		});
	}

	@Override
	public Flux<OperationCreditAccount> findByDniAndNumeroCuenta(String dni, String numCuenta) {
		
		return productoDao.findByDniAndNumeroCuenta(dni, numCuenta);
	}

	

}
