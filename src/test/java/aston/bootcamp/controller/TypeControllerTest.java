package aston.bootcamp.controller;

import aston.bootcamp.config.controller.TypeControllerTestConfig;
import aston.bootcamp.controllers.TypeController;
import aston.bootcamp.dto.TypeIncomingDto;
import aston.bootcamp.dto.TypeOutgoingDto;
import aston.bootcamp.dto.TypeUpdateDto;
import aston.bootcamp.exceptions.handler.ExceptionAdvice;
import aston.bootcamp.service.TypeService;
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
@ContextConfiguration(classes = {TypeControllerTestConfig.class})
public class TypeControllerTest {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private TypeService typeService;
    @InjectMocks
    private TypeController typeController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(typeController).setControllerAdvice(ExceptionAdvice.class).build();
    }

    @Test
    void findAllTypes() throws Exception {
        List<TypeOutgoingDto> types = List.of(new TypeOutgoingDto(1L, "test", new ArrayList<>()),
                new TypeOutgoingDto(2L, "test1", new ArrayList<>()));
        Mockito.doReturn(types).when(typeService).getAllTypes();
        mockMvc.perform(get("/type/all")).andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));
    }

    @Test
    void findTypeById() throws Exception {
        TypeOutgoingDto typeOutgoingDto = new TypeOutgoingDto(1L, "test", new ArrayList<>());
        Mockito.doReturn(typeOutgoingDto).when(typeService).getByTypeId(Mockito.anyLong());
        mockMvc.perform(get("/type/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("test"));
    }

    @Test
    void createType() throws Exception {
        TypeOutgoingDto typeOutgoingDto = new TypeOutgoingDto(1L, "test", new ArrayList<>());
        TypeIncomingDto typeIncomingDto = new TypeIncomingDto("test");
        Mockito.doReturn(typeOutgoingDto).when(typeService).createType(Mockito.any());
        mockMvc.perform(post("/type")
                        .content(objectMapper.writeValueAsString(typeIncomingDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("test"));
    }

    @Test
    void updateType() throws Exception {
        TypeOutgoingDto typeOutgoingDto = new TypeOutgoingDto(1L, "test", new ArrayList<>());
        TypeUpdateDto typeUpdateDto = new TypeUpdateDto(1L, "test", new ArrayList<>());
        Mockito.doReturn(typeOutgoingDto).when(typeService).updateType(Mockito.anyLong(), Mockito.any());
        mockMvc.perform(put("/type/1")
                        .content(objectMapper.writeValueAsString(typeUpdateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.type").value("test"));
    }

    @Test
    void deleteType() throws Exception {
        Mockito.doNothing().when(typeService).deleteByTypeId(Mockito.anyLong());
        mockMvc.perform(delete("/type/1"))
                .andExpect(status().isNoContent());
    }
}
