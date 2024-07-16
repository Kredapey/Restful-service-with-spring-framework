package aston.bootcamp.controller;

import aston.bootcamp.config.controller.BrandControllerTestConfig;
import aston.bootcamp.controllers.BrandController;
import aston.bootcamp.dto.BrandIncomingDto;
import aston.bootcamp.dto.BrandOutgoingDto;
import aston.bootcamp.dto.BrandUpdateDto;
import aston.bootcamp.exceptions.handler.ExceptionAdvice;
import aston.bootcamp.service.BrandService;
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
@ContextConfiguration(classes = {BrandControllerTestConfig.class})
public class BrandControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private BrandService brandService;
    @InjectMocks
    private BrandController brandController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(brandController).setControllerAdvice(ExceptionAdvice.class).build();
    }

    @Test
    void findAllBrands() throws Exception {
        List<BrandOutgoingDto> brands = List.of(new BrandOutgoingDto(1L, "test", new ArrayList<>()),
                new BrandOutgoingDto(2L, "test1", new ArrayList<>()));
        Mockito.doReturn(brands).when(brandService).getAllBrands();
        mockMvc.perform(get("/brand/all")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void findBrandById() throws Exception {
        BrandOutgoingDto brandOutgoingDto = new BrandOutgoingDto(1L, "test", new ArrayList<>());
        Mockito.doReturn(brandOutgoingDto).when(brandService).getBrandById(Mockito.anyLong());
        mockMvc.perform(get("/brand/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("test"));
    }

    @Test
    void createBrand() throws Exception {
        BrandOutgoingDto brandOutgoingDto = new BrandOutgoingDto(1L, "test", new ArrayList<>());
        BrandIncomingDto brandIncomingDto = new BrandIncomingDto("test");
        Mockito.doReturn(brandOutgoingDto).when(brandService).createBrand(Mockito.any());
        mockMvc.perform(post("/brand")
                        .content(objectMapper.writeValueAsString(brandIncomingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("test"));
    }

    @Test
    void updateBrand() throws Exception {
        BrandOutgoingDto brandOutgoingDto = new BrandOutgoingDto(1L, "test", new ArrayList<>());
        BrandUpdateDto brandUpdateDto = new BrandUpdateDto(1L, "test", new ArrayList<>());
        Mockito.doReturn(brandOutgoingDto).when(brandService).updateBrand(Mockito.anyLong(), Mockito.any());
        mockMvc.perform(put("/brand/1")
                        .content(objectMapper.writeValueAsString(brandUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.brand").value("test"));
    }

    @Test
    void updateBrandIllegalArgument() throws Exception {
        BrandUpdateDto brandUpdateDto = new BrandUpdateDto(null, "test", new ArrayList<>());
        Mockito.doThrow(IllegalArgumentException.class).when(brandService).
                updateBrand(Mockito.anyLong(), Mockito.any());
        mockMvc.perform(put("/brand/1")
                        .content(objectMapper.writeValueAsString(brandUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBrand() throws Exception {
        Mockito.doNothing().when(brandService).deleteByBrandId(Mockito.anyLong());
        mockMvc.perform(delete("/brand/1"))
                .andExpect(status().isNoContent());
    }

}
