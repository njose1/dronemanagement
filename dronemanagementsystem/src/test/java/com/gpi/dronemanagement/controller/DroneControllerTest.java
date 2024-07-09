package com.gpi.dronemanagement.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gpi.dronemanagement.dto.DroneDto;
import com.gpi.dronemanagement.dto.DronePosition;
import com.gpi.dronemanagement.service.DroneService;

@WebMvcTest(DroneController.class)
class DroneControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private DroneService droneService;

	DroneDto droneDto;
	List<DroneDto> droneDtos = new ArrayList<DroneDto>();

	@BeforeEach
	void setUp() {
		droneDto = new DroneDto("testdronetest", new DronePosition(2, 2), "NORTH");
		droneDtos.add(droneDto);
	}

	@Test
	public void registerDroneTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(droneDto);

		when(droneService.createDrone(droneDto)).thenReturn(droneDto);
		this.mvc.perform(post("/dronemanagement/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isCreated());

	}

	@Test
    void getDroneTest() throws Exception {
        when(droneService.getDroneByName("testdronetest")).thenReturn(droneDto);
        this.mvc.perform(get("/dronemanagement/" + "testdronetest")).andDo(print()).andExpect(status().isOk());
    }

	@Test
	void updateDronePositionTest() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
		String requestJson = ow.writeValueAsString(droneDto);

		when(droneService.updatePosition(droneDto)).thenReturn(droneDtos);
		this.mvc.perform(put("/dronemanagement/").contentType(MediaType.APPLICATION_JSON).content(requestJson))
				.andDo(print()).andExpect(status().isOk());
	}

}
