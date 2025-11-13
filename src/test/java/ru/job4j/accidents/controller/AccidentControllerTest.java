package ru.job4j.accidents.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;
import ru.job4j.accidents.service.RuleService;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
public class AccidentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RuleService rule;

    @MockBean
    private AccidentService accidentService;

    @MockBean
    private AccidentTypeService type;

    @MockBean
    private Model model;

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
        Accident testAccident = new Accident();
        testAccident.setId(1);
        when(accidentService.getById(1)).thenReturn(Optional.of(testAccident));
        this.mockMvc.perform(get("/formUpdateAccident").param("id", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("editAccident"))
                .andExpect(model().attributeExists("accident"))
                .andExpect(model().attributeExists("types"));
    }

    @Test
    @WithMockUser
    public void shouldSaveAccident() throws Exception {
        Set<Rule> mockRules = Set.of(new Rule(1, "Rule1"), new Rule(2, "Rule2"));
        when(rule.findAllById(any())).thenReturn(mockRules);

        this.mockMvc.perform(post("/saveAccident")
                        .param("name", "ДТП на МКАД")
                        .param("text", "Описание аварии")
                        .param("address", "Москва")
                        .param("rIds", "1")
                        .param("rIds", "2"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).create(argument.capture());
        Accident captured = argument.getValue();

        assertThat(captured.getName()).isEqualTo("ДТП на МКАД");
        assertThat(captured.getText()).isEqualTo("Описание аварии");
        assertThat(captured.getAddress()).isEqualTo("Москва");
        assertThat(captured.getRules()).hasSize(2);
    }

    @Test
    @WithMockUser
    public void shouldUpdateAccidentSuccess() throws Exception {
        AccidentType accidentType = new AccidentType(1, "Дорожно-транспортное происшествие");
        when(type.getById(1)).thenReturn(accidentType);
        when(accidentService.update(any(Accident.class))).thenReturn(true);

        this.mockMvc.perform(post("/editAccident")
                        .param("name", "ДТП на МКАД")
                        .param("text", "Описание аварии")
                        .param("address", "Москва")
                        .param("typeId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/index"));

        ArgumentCaptor<Accident> argument = ArgumentCaptor.forClass(Accident.class);
        verify(accidentService).update(argument.capture());
        Accident captured = argument.getValue();
        assertThat(captured.getName()).isEqualTo("ДТП на МКАД");
        assertThat(captured.getText()).isEqualTo("Описание аварии");
        assertThat(captured.getAddress()).isEqualTo("Москва");
        assertThat(captured.getType()).isEqualTo(accidentType);
    }
}
