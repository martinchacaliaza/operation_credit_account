package com.example.app.controllers;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.app.models.Client;
import com.example.app.models.CreditAccount;
import com.example.app.models.OperationCreditAccount;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service

public class WebClientController {
	@Value("${com.bootcamp.gateway.url}")
	String valor;
	
	WebClient webClient;
	@PostConstruct
	 public void init() { 
			 webClient = WebClient 
			.builder()
			.baseUrl("http://"+valor+"/productos_creditos/api/ProductoCredito/")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
			.build(); 		 
	}
	
  
   public Flux<Client> getCliente(@PathVariable String dniCliente) 
   { 
		return webClient.get().uri("/dni/"+dniCliente).retrieve().bodyToFlux(Client.class); 
   }
  
	
   public Mono<CreditAccount> cosumo( String numero_cuenta,  Double monto) 
   { 
		return webClient.put().uri("/consumo/"+numero_cuenta+"/"+monto).retrieve().bodyToMono(CreditAccount.class); 
   }


}
