package com.example.app.models;

import java.util.Date;


import javax.validation.constraints.NotEmpty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@Document(collection ="Operaciones")
public class OperationCreditAccount {

	@NotEmpty
	private String dni;
	@NotEmpty
	private String codigoBancario;
	@NotEmpty
	private String numeroCuenta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOperacion;
	@NotEmpty
	private TypeOperationCredit tipoOperacion;
	@NotEmpty
	private Double montoPago;
	

	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}
}










