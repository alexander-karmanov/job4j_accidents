package ru.job4j.accidents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

import javax.transaction.Transactional;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    @WithMockUser
    public void shouldReturnCreateAccidentView() throws Exception {
        this.mockMvc.perform(get("/createAccident"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("createAccident"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("rules"));
    }

    @Test
    @Transactional
    @WithMockUser
    public void shouldReturnEditAccidentView() throws Exception {
        this.mockMvc.perform(get("/formUpdateAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"))
                .andExpect(model().attributeExists("accident"))
                .andExpect(model().attributeExists("types"));
    }
}
