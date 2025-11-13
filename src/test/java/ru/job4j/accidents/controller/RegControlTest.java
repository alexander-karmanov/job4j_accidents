package ru.job4j.accidents.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.ArgumentCaptor;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.accidents.Main;
import ru.job4j.accidents.model.Authority;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.AuthorityService;
import ru.job4j.accidents.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc

public class RegControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorityService authorities;

    @MockBean
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Test
    public void shouldReturnRegView() throws Exception {
        this.mockMvc.perform(get("/reg"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("reg"));
    }

    @Test
    @WithMockUser
    public void shouldSaveUserSuccess() throws Exception {
        Authority userAuthority = new Authority();
        userAuthority.setId(1);
        userAuthority.setAuthority("ROLE_USER");
        when(authorities.findByAuthority("ROLE_USER")).thenReturn(userAuthority);

        User savedUser = new User();
        savedUser.setUsername("testuser");
        when(userService.save(any(User.class))).thenReturn(Optional.of(savedUser));

        this.mockMvc.perform(post("/reg")
                        .param("username", "testuser")
                        .param("password", "pass123"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(userService).save(argument.capture());
        User captured = argument.getValue();

        assertThat(captured.getUsername()).isEqualTo("testuser");
        assertThat(captured.isEnabled()).isTrue();
        assertThat(captured.getAuthority()).isEqualTo(userAuthority);

        String originalPassword = "pass123";
        assertThat(captured.getPassword()).isNotEqualTo(originalPassword);
        assertThat(encoder.matches(originalPassword, captured.getPassword())).isTrue();
    }
}
