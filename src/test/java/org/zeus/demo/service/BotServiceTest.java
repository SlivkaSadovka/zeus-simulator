package org.zeus.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.zeus.demo.dto.BotStateDTO;
import org.zeus.demo.model.Bot;
import org.zeus.demo.model.Status;
import org.zeus.demo.repository.BotRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotServiceTest {

    private BotRepository botRepository;
    private BotService botService;

    @BeforeEach
    void setUp() {
        botRepository = mock(BotRepository.class);
        botService = new BotService(botRepository);
    }

    @Test
    void testCreateBot() {
        Bot savedBot = new Bot();
        when(botRepository.save(any(Bot.class))).thenReturn(savedBot);

        Bot result = botService.createBot();

        assertNotNull(result);
        assertEquals(savedBot, result);

        verify(botRepository, times(1)).save(any(Bot.class));
    }

    @Test
    void testGetAllBots() {
        List<Bot> bots = List.of(new Bot(), new Bot());
        when(botRepository.findAll()).thenReturn(bots);

        List<Bot> result = botService.getAllBots();

        assertEquals(2, result.size());
        verify(botRepository, times(1)).findAll();
    }

    @Test
    void testBlessBot() {
        Bot bot = new Bot();
        bot.setFaithLevel(5);
        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        Bot result = botService.blessBot(1L);

        assertEquals(15, result.getFaithLevel());
        verify(botRepository).save(bot);
    }

    @Test
    void testBlessBotThrowsExceptionIfNotFound() {
        when(botRepository.findById(1L)).thenReturn(Optional.empty());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> botService.blessBot(1L));
        assertEquals("Bot not found", exception.getMessage());
    }

    @Test
    void testSmiteBot() {
        Bot bot = new Bot();
        bot.setStatus(Status.ALIVE);
        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        Bot result = botService.smiteBot(1L);

        assertEquals(Status.DEAD, result.getStatus());
        verify(botRepository).save(bot);
    }

    @Test
    void testUpdateBotStatusValid() {
        Bot bot = new Bot();
        bot.setStatus(Status.UNKNOWN);
        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        Bot result = botService.updateBotStatus(1L, "ALIVE");

        assertEquals(Status.ALIVE, result.getStatus());
        verify(botRepository).save(bot);
    }

    @Test
    void testUpdateBotStatusInvalid() {
        Bot bot = new Bot();
        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> botService.updateBotStatus(1L, "NOT_EXIST"));
        assertEquals("Invalid status: NOT_EXIST", exception.getMessage());
    }

    @Test
    void testDeleteBot() {
        when(botRepository.existsById(1L)).thenReturn(true);

        botService.deleteBot(1L);

        verify(botRepository).deleteById(1L);
    }

    @Test
    void testDeleteBotThrowsExceptionIfNotFound() {
        when(botRepository.existsById(1L)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> botService.deleteBot(1L));
        assertEquals("Bot not found with id: 1", exception.getMessage());
    }

    @Test
    void testGetBotsState() {
        Bot bot1 = new Bot();
        bot1.setFirstName("Zeus");
        bot1.setStatus(Status.ALIVE);

        Bot bot2 = new Bot();
        bot2.setFirstName("Hera");
        bot2.setStatus(Status.DEAD);

        when(botRepository.findAll()).thenReturn(List.of(bot1, bot2));

        List<BotStateDTO> states = botService.getBotsState();

        assertEquals(2, states.size());
        assertEquals("Zeus", states.get(0).getName());
        assertEquals(Status.ALIVE, states.get(0).getStatus());
        assertEquals("Hera", states.get(1).getName());
        assertEquals(Status.DEAD, states.get(1).getStatus());
    }
}
