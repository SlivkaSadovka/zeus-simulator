package org.zeus.demo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zeus.demo.dto.BotStateDTO;
import org.zeus.demo.model.Bot;
import org.zeus.demo.model.Status;
import org.zeus.demo.repository.BotRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BotServiceTest {

    private BotRepository botRepository;
    private PersonalityRandomGeneratorService personalityMock;
    private BotService botService;

    @BeforeEach
    void setup() throws Exception {
        botRepository = mock(BotRepository.class);
        personalityMock = mock(PersonalityRandomGeneratorService.class);

        botService = new BotService(botRepository);

        // Подмена приватного поля personalityGenerator
        Field field = BotService.class.getDeclaredField("personalityGenerator");
        field.setAccessible(true);
        field.set(botService, personalityMock);
    }

    @Test
    void testCreateBot() {
        when(personalityMock.generateFirstName()).thenReturn("John");
        when(personalityMock.generateLastName()).thenReturn("Doe");
        when(personalityMock.generateFavoriteJoke()).thenReturn("Funny joke");
        when(personalityMock.generateFavoriteBook()).thenReturn("Book Name");
        when(personalityMock.generateFavoriteQuote()).thenReturn("Quote text");

        Bot saved = new Bot();
        saved.setId(1L);

        when(botRepository.save(any(Bot.class))).thenReturn(saved);

        Bot result = botService.createBot();

        assertEquals(1L, result.getId());
        verify(botRepository, times(1)).save(any(Bot.class));
    }

    @Test
    void testGetAllBots() {
        when(botRepository.findAll()).thenReturn(List.of(new Bot(), new Bot()));
        assertEquals(2, botService.getAllBots().size());
    }

    @Test
    void testBlessBot() {
        Bot bot = new Bot();
        bot.setId(1L);
        bot.setFaithLevel(20);

        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        Bot result = botService.blessBot(1L);

        assertEquals(30, result.getFaithLevel());
    }

    @Test
    void testSmiteBot() {
        Bot bot = new Bot();
        bot.setId(1L);
        bot.setStatus(Status.ALIVE);

        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        Bot result = botService.smiteBot(1L);

        assertEquals(Status.DEAD, result.getStatus());
    }

    @Test
    void testUpdateBotStatusValid() {
        Bot bot = new Bot();
        bot.setId(1L);
        bot.setStatus(Status.ALIVE);

        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));
        when(botRepository.save(bot)).thenReturn(bot);

        Bot result = botService.updateBotStatus(1L, "dead");

        assertEquals(Status.DEAD, result.getStatus());
    }

    @Test
    void testUpdateBotStatusInvalid() {
        Bot bot = new Bot();
        bot.setId(1L);

        when(botRepository.findById(1L)).thenReturn(Optional.of(bot));

        assertThrows(RuntimeException.class, () ->
                botService.updateBotStatus(1L, "banana")
        );
    }

    @Test
    void testDeleteBotSuccess() {
        when(botRepository.existsById(1L)).thenReturn(true);

        botService.deleteBot(1L);

        verify(botRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteBotNotFound() {
        when(botRepository.existsById(1L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> botService.deleteBot(1L));
    }

    @Test
    void testGetBotsState() {
        Bot bot = new Bot();
        bot.setId(1L);
        bot.setFirstName("John");
        bot.setStatus(Status.ALIVE);

        when(botRepository.findAll()).thenReturn(List.of(bot));

        List<BotStateDTO> list = botService.getBotsState();

        assertEquals(1, list.size());

        BotStateDTO dto = list.get(0);

        assertEquals(1L, dto.getId());
        assertEquals("John", dto.getName());
        assertEquals(Status.ALIVE, dto.getStatus());
    }

    @Test
    void testUpdateBotStatusNotFoundById() {
        // given
        Long missingId = 42L;
        when(botRepository.findById(missingId)).thenReturn(Optional.empty());

        // when
        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> botService.updateBotStatus(missingId, "alive"));

        // then
        assertEquals("Bot not found with id: " + missingId, ex.getMessage());
    }

}
