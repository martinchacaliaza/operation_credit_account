package com.example.app;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.example.app.models.OperationCreditAccount;
import com.example.app.service.OperacionService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationOperCreditTests {

	@Autowired
	private WebTestClient oper;

	@Autowired
	OperacionService operService;

	@Test
	void contextLoads() {
	}

	@Test
	public void listOper() {
		oper.get().uri("/api/OperCuentasCreditos/")
		.accept().exchange().expectStatus()
				.isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBodyList(OperationCreditAccount.class).consumeWith(response -> {
					List<OperationCreditAccount> operas = response.getResponseBody();
					operas.forEach(p -> {
						System.out.println(p.getDni());
					});

					Assertions.assertThat(operas.size() > 0).isTrue();
				});
		;
	}

	@Test
	public void findAllOperByDniCliente() {
		OperationCreditAccount cred = operService.findAllByIdOperacionDniCliente("72739839").blockFirst();
		oper.get().uri("/api/OperCuentasCreditos/dni/{dni}", Collections.singletonMap("dni", cred.getDni()))
				.accept()
				.exchange().expectStatus().isOk().expectHeader()
				.contentType(MediaType.APPLICATION_JSON);
	}

}
