package com.example.app.models;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import reactor.core.publisher.Flux;

@Getter
@Setter
@ToString
@Document(collection ="Operaciones")
public class OperationCreditAccount {

	@NotEmpty
	private String dni;
	@NotEmpty
	private String codigo_bancario;
	@NotEmpty
	private String numero_cuenta;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaOperacion;
	@NotEmpty
	private TypeOperation tipoOperacion;
	@NotEmpty
	private Double montoPago;
	

	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	public Date fechaOperacion() {
		return fechaOperacion;
	}
}










