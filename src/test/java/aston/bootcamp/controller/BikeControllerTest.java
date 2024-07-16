package aston.bootcamp.controller;

import aston.bootcamp.config.controller.BikeControllerTestConfig;
import aston.bootcamp.controllers.BikeController;
import aston.bootcamp.dto.*;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.exceptions.handler.ExceptionAdvice;
import aston.bootcamp.model.Brand;
import aston.bootcamp.model.Type;
import aston.bootcamp.service.BikeService;
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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@ContextConfiguration(classes = {BikeControllerTestConfig.class})
public class BikeControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private BikeService bikeService;
    @InjectMocks
    private BikeController bikeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(bikeController).setControllerAdvice(ExceptionAdvice.class).build();
    }

    @Test
    public void findAllBikesTest() throws Exception {
        List<BikeOutgoingDto> bikes = List.of(
                new BikeOutgoingDto(1L,
                        new TypeOutgoingDto(),
                        new BrandOutgoingDto(),
                        "test",
                        100L,
                        new ArrayList<>()),
                new BikeOutgoingDto(2L,
                        new TypeOutgoingDto(),
                        new BrandOutgoingDto(),
                        "test",
                        100L,
                        new ArrayList<>())
        );
        Mockito.doReturn(bikes).when(bikeService).getAllBikes();
        mockMvc.perform(get("/bike/all")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void findByBikeId() throws Exception {
        BikeOutgoingDto bikeOutgoingDto = new BikeOutgoingDto(1L,
                new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        Mockito.doReturn(bikeOutgoingDto).when(bikeService).getBikeById(Mockito.anyLong());
        mockMvc.perform(get("/bike/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("test"))
                .andExpect(jsonPath("$.cost").value(100L));
    }

    @Test
    void findByBikeIdNotFound() throws Exception {
        Mockito.doThrow(NotFoundException.class).when(bikeService).getBikeById(Mockito.anyLong());
        mockMvc.perform(get("/bike/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBike() throws Exception {
        BikeOutgoingDto created = new BikeOutgoingDto(1L,
                new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        BikeIncomingDto incomingDto = new BikeIncomingDto(new Type(1L,
                "test",
                new ArrayList<>()), new Brand(1L,
                "test",
                new ArrayList<>()), "test", 100L);
        Mockito.doReturn(created).when(bikeService).createBike(Mockito.any());
        mockMvc.perform(post("/bike")
                        .content(objectMapper.writeValueAsString(incomingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("test"))
                .andExpect(jsonPath("$.cost").value(100L));
    }

    @Test
    void createBikeIncorrectInput() throws Exception {
        Mockito.doThrow(SQLException.class).when(bikeService).createBike(Mockito.any());
        BikeIncomingDto incomingDto = new BikeIncomingDto(new Type(1L,
                "test",
                new ArrayList<>()), new Brand(1L,
                "test",
                new ArrayList<>()), "test", 100L);
        mockMvc.perform(post("/bike")
                        .content(objectMapper.writeValueAsString(incomingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBike() throws Exception {
        BikeOutgoingDto created = new BikeOutgoingDto(1L,
                new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                "test",
                100L,
                new ArrayList<>());
        BikeUpdateDto updateDto = new BikeUpdateDto(1L,
                new TypeUpdateDto(1L,
                        "test",
                        new ArrayList<>()),
                new BrandUpdateDto(1L,
                        "test",
                        new ArrayList<>()), "test", 100L,
                new ArrayList<>());
        Mockito.doReturn(created).when(bikeService).updateBike(Mockito.anyLong(), Mockito.any());
        mockMvc.perform(put("/bike/1")
                        .content(objectMapper.writeValueAsString(updateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.model").value("test"))
                .andExpect(jsonPath("$.cost").value(100L));
    }

    @Test
    void deleteBike() throws Exception {
        Mockito.doNothing().when(bikeService).deleteByBikeId(Mockito.anyLong());
        mockMvc.perform(delete("/bike/1"))
                .andExpect(status().isNoContent());
    }
}
