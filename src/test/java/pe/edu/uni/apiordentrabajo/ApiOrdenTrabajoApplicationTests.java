package pe.edu.uni.apiordentrabajo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import pe.edu.uni.apiordentrabajo.controller.OrdenTrabajoController;

@SpringBootTest
class ApiOrdenTrabajoApplicationTests {

	@Test
	void contextLoads() {
	}

}
