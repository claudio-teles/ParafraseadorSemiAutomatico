package test.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import service.VocabuloService;

@TestMethodOrder(OrderAnnotation.class)
class VocabuloTest {
	
	private String s = "";

	@BeforeEach
	void setUp() throws Exception {
		s = "";
	}

	@Test
	@Order(1)
	void testeCriar() {
		new VocabuloService().registrarVocabulo(s);
	}

}
