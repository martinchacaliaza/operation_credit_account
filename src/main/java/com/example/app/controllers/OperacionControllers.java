package com.example.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.app.models.OperationCreditAccount;
import com.example.app.service.OperacionService;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping("/api/OperCuentasCreditos")
@RestController
public class OperacionControllers {

	@Autowired
	private OperacionService operacionService;


	@ApiOperation(value = "Muestra todos las cuentas bancarias existentes" , notes="")
	@GetMapping
	public Mono<ResponseEntity<Flux<OperationCreditAccount>>> findAll() {
		return Mono.just(
				ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(operacionService.findAllOperacion())
		);
	}
	
	@ApiOperation(value = "Filtra todas cuentas bancarias por id" , notes="")
	@GetMapping("/{id}")
	public Mono<ResponseEntity<OperationCreditAccount>> viewId(@PathVariable String id) {
		return operacionService.findByIdOperacion(id)
				.map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(p))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@ApiOperation(value = "Actualiza operacion con el numero de id, si no existe el id vuelve a crearlo" , notes="")
	@PutMapping
	public Mono<OperationCreditAccount> updateProducto(@RequestBody OperationCreditAccount oper) {
		System.out.println(oper.toString());
		return operacionService.saveOperacion(oper);
	}
	

	@ApiOperation(value = "Guarda una operacion bancaria" , notes="")
	@PostMapping
	public Mono<OperationCreditAccount> guardarOperacion(@RequestBody OperationCreditAccount oper) {
		return operacionService.saveOperacion(oper);
	}

	@ApiOperation(value = "Muestra todas las Operaciones bancarias por el numero de dni del cliente" , notes="")
	@GetMapping("/dni/{dni}")
	public Flux<OperationCreditAccount> listOperacionByDicliente(@PathVariable String dni) {
		Flux<OperationCreditAccount> operacion = operacionService.findAllByIdOperacionDniCliente(dni);
		return operacion;
	}
	
	
	@ApiOperation(value = "Realiza una Transaccion(RETIROS)/guardando en el microservicio operaciones(movimientos)/"
			+ " Y Actualiza el saldo de la tarjetae" , notes="")
	@PostMapping("/consumo")
	public Mono<OperationCreditAccount> saveOperacionRetiro(@RequestBody OperationCreditAccount oper) {
		//System.out.println(producto.toString());
		return operacionService.saveOperacionRetiro(oper);
	}

	@ApiOperation(value = "Realiza una Transaccion(Deposito)/guardando en el microservicio operaciones(movimientos)/"
			+ " Y Actualiza el saldo de la tarjeta" , notes="")
	@PostMapping("/pago")
	public Mono<OperationCreditAccount> saveOperacionDeposito(@RequestBody OperationCreditAccount producto) {
		//System.out.println(producto.toString());
		return operacionService.saveOperacionDeposito(producto);
	}

	@ApiOperation(value = "Muestra todos los movimientos bancarios por cliente y numero tarjeta(cuentas credito)" , notes="")
	@GetMapping("/MovimientosBancarios/{dni}/{num_cuenta}")
	public Flux<OperationCreditAccount> movimientosBancarios(@PathVariable String dni, @PathVariable String num_cuenta) {
		Flux<OperationCreditAccount> oper = operacionService.findByDniAndNumeroCuenta(dni, num_cuenta);
		return oper;
	}
}



