package com.example.app.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreditAccount {
	private String id;
	private String numeroCuenta;
	private String dni;
	private TypeCreditAccount tipoProducto;
	private String fecha_afiliacion;
	private String fecha_caducidad;
	private Double credito;
	private Double saldo;
	private Double consumo;
	private String usuario;
	private String clave;
	private String codigoBancario;

	
	//private tipoProducto tipoCliente;
}










