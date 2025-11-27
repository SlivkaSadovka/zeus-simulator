package org.zeus.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zeus.demo.dto.BotStateDTO;
import org.zeus.demo.model.Bot;
import org.zeus.demo.model.Status;
import org.zeus.demo.service.BotService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class BotControllerTest {

    private MockMvc mockMvc;
    private BotService botService;
    private BotController botController;

    @BeforeEach
    void setUp() {
        botService = mock(BotService.class);
        botController = new BotController(botService);
        mockMvc = MockMvcBuilders.standaloneSetup(botController).build();
    }

    @Test
    void testCreateBot() throws Exception {
        Bot bot = new Bot();
        bot.setFirstName("Zeus");
        bot.setStatus(Status.ALIVE);

        when(botService.createBot()).thenReturn(bot);

        mockMvc.perform(post("/bots/create"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Zeus"))
                .andExpect(jsonPath("$.status").value("ALIVE"));

        verify(botService, times(1)).createBot();
    }

    @Test
    void testGetAllBots() throws Exception {
        Bot bot = new Bot();
        bot.setFirstName("Zeus");
        when(botService.getAllBots()).thenReturn(List.of(bot));

        mockMvc.perform(get("/bots"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Zeus"));

        verify(botService, times(1)).getAllBots();
    }

    @Test
    void testUpdateStatus() throws Exception {
        Bot bot = new Bot();
        bot.setFirstName("Zeus");
        bot.setStatus(Status.ALIVE);

        when(botService.updateBotStatus(1L, "ALIVE")).thenReturn(bot);

        mockMvc.perform(put("/bots/1/status")
                        .param("status", "ALIVE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Zeus"))
                .andExpect(jsonPath("$.status").value("ALIVE"));

        verify(botService, times(1)).updateBotStatus(1L, "ALIVE");
    }

    @Test
    void testDeleteBot() throws Exception {
        doNothing().when(botService).deleteBot(1L);

        mockMvc.perform(delete("/bots/1"))
                .andExpect(status().isOk());

        verify(botService, times(1)).deleteBot(1L);
    }

    @Test
    void testGetBotsState() throws Exception {
        BotStateDTO stateDTO = new BotStateDTO(1L, "Zeus", Status.ALIVE);
        when(botService.getBotsState()).thenReturn(List.of(stateDTO));

        mockMvc.perform(get("/bots/state"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Zeus"))
                .andExpect(jsonPath("$[0].status").value("ALIVE"));

        verify(botService, times(1)).getBotsState();
    }
}
