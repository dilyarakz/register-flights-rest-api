package com.assessment.flights;
import static org.assertj.core.api.Assertions.assertThat;

import com.assessment.flights.controller.FlightsController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlightsApplicationTests {

	@Autowired
	FlightsController flightsController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(flightsController).isNotNull();
	}

}
