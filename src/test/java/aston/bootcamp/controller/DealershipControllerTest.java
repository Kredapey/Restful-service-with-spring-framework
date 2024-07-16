package aston.bootcamp.controller;

import aston.bootcamp.config.controller.DealershipControllerTestConfig;
import aston.bootcamp.controllers.DealershipController;
import aston.bootcamp.dto.*;
import aston.bootcamp.exceptions.handler.ExceptionAdvice;
import aston.bootcamp.service.DealershipService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {DealershipControllerTestConfig.class})
public class DealershipControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private DealershipService dealershipService;
    @InjectMocks
    private DealershipController dealershipController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(dealershipController).setControllerAdvice(ExceptionAdvice.class).build();
    }

    @Test
    void findAllDealerships() throws Exception {
        List<DealershipOutgoingDto> dealerships = List.of(new DealershipOutgoingDto(1L, "test", "test", 40L, new ArrayList<>()),
                new DealershipOutgoingDto(2L, "test", "test", 40L, new ArrayList<>()));
        Mockito.doReturn(dealerships).when(dealershipService).getAllDealerships();
        mockMvc.perform(get("/dealership/all")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void findDealershipById() throws Exception {
        DealershipOutgoingDto dealershipOutgoingDto = new DealershipOutgoingDto(1L, "test", "test", 40L, new ArrayList<>());
        Mockito.doReturn(dealershipOutgoingDto).when(dealershipService).getDealershipById(Mockito.anyLong());
        mockMvc.perform(get("/dealership/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("test"))
                .andExpect(jsonPath("$.street").value("test"))
                .andExpect(jsonPath("$.houseNum").value(40L));
    }

    @Test
    void createDealership() throws Exception {
        DealershipOutgoingDto dealershipOutgoingDto = new DealershipOutgoingDto(1L, "test", "test", 40L, new ArrayList<>());
        DealershipIncomingDto dealershipIncomingDto = new DealershipIncomingDto("test", "test", 40L);
        Mockito.doReturn(dealershipOutgoingDto).when(dealershipService).createDealership(Mockito.any());
        mockMvc.perform(post("/dealership")
                        .content(objectMapper.writeValueAsString(dealershipIncomingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("test"))
                .andExpect(jsonPath("$.street").value("test"))
                .andExpect(jsonPath("$.houseNum").value(40L));
    }

    @Test
    void updateDealership() throws Exception {
        DealershipOutgoingDto dealershipOutgoingDto = new DealershipOutgoingDto(1L, "test", "test", 40L, new ArrayList<>());
        DealershipUpdateDto dealershipUpdateDto = new DealershipUpdateDto(1L, "test", "test", 40L, new ArrayList<>());
        Mockito.doReturn(dealershipOutgoingDto).when(dealershipService).updateDealership(Mockito.anyLong(), Mockito.any());
        mockMvc.perform(put("/dealership/1")
                        .content(objectMapper.writeValueAsString(dealershipUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("test"))
                .andExpect(jsonPath("$.street").value("test"))
                .andExpect(jsonPath("$.houseNum").value(40L));
    }

    @Test
    void deleteDealership() throws Exception {
        Mockito.doNothing().when(dealershipService).deleteDealershipById(Mockito.anyLong());
        mockMvc.perform(delete("/dealership/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addBikeToDealership() throws Exception {
        DealershipOutgoingDto dealershipOutgoingDto = new DealershipOutgoingDto(1L,
                "test",
                "test",
                40L,
                List.of(new BikeOutgoingDto(1L,
                        new TypeOutgoingDto(),
                        new BrandOutgoingDto(),
                        "test",
                        100L,
                        new ArrayList<>())));
        Mockito.doReturn(dealershipOutgoingDto).when(dealershipService).addBikeToDealership(Mockito.anyLong(), Mockito.anyLong());
        mockMvc.perform(put("/dealership/1/add_bike/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("test"))
                .andExpect(jsonPath("$.street").value("test"))
                .andExpect(jsonPath("$.houseNum").value(40L))
                .andExpect(jsonPath("$.bikes.length()").value(1));
    }

    @Test
    void deleteBikeFromDealership() throws Exception {
        DealershipOutgoingDto dealershipOutgoingDto = new DealershipOutgoingDto(1L,
                "test",
                "test",
                40L,
                List.of());
        Mockito.doReturn(dealershipOutgoingDto).when(dealershipService).deleteBikeFromDealership(Mockito.anyLong(), Mockito.anyLong());
        mockMvc.perform(put("/dealership/1/delete_bike/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.city").value("test"))
                .andExpect(jsonPath("$.street").value("test"))
                .andExpect(jsonPath("$.houseNum").value(40L))
                .andExpect(jsonPath("$.bikes.length()").value(0));
    }

    @Test
    void handleEntityNotFound() throws Exception {
        Mockito.doThrow(EntityNotFoundException.class).when(dealershipService).addBikeToDealership(Mockito.anyLong(), Mockito.anyLong());
        mockMvc.perform(put("/dealership/1/add_bike/1"))
                .andExpect(status().isNotFound());
    }

}
