package org.zeus.demo.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zeus.demo.model.Behavior;
import org.zeus.demo.model.Bot;
import org.zeus.demo.model.Status;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class BotTest {

    private Bot bot;

    @BeforeEach
    void setUp() {
        bot = new Bot();
    }

    @Test
    void testDefaultConstructor() {
        assertEquals(0, bot.getFaithLevel());
        assertEquals(Status.UNKNOWN, bot.getStatus());
        assertNull(bot.getFirstName());
        assertNull(bot.getLastName());
        assertNull(bot.getFavoriteJoke());
        assertNull(bot.getFavoriteQuote());
        assertNull(bot.getFavoriteAnimal());
        assertNull(bot.getCreatedAt());
        assertNull(bot.getUpdatedAt());
    }

    @Test
    void testPrePersistSetsTimestamps() {
        bot.onCreate();

        assertNotNull(bot.getCreatedAt());
        assertNotNull(bot.getUpdatedAt());
        assertEquals(bot.getCreatedAt(), bot.getUpdatedAt());
    }

    @Test
    void testPreUpdateSetsUpdatedAt() throws InterruptedException {
        bot.onCreate();
        LocalDateTime created = bot.getCreatedAt();
        LocalDateTime updated = bot.getUpdatedAt();

        Thread.sleep(10); // чтобы время точно изменилось
        bot.onUpdate();

        assertEquals(created, bot.getCreatedAt(), "createdAt не должно меняться при обновлении");
        assertTrue(bot.getUpdatedAt().isAfter(updated), "updatedAt должно обновиться");
    }

    @Test
    void testSettersAndGetters() {
        bot.setFirstName("Zeus");
        bot.setLastName("Thunder");
        bot.setFavoriteJoke("Why did the chicken cross the road?");
        bot.setFavoriteQuote("To be or not to be");
        bot.setFavoriteAnimal("Eagle");
        bot.setStatus(Status.ACTIVE);
        bot.setBehavior(Behavior.FRIENDLY);
        bot.setFaithLevel(10);

        assertEquals("Zeus", bot.getFirstName());
        assertEquals("Thunder", bot.getLastName());
        assertEquals("Why did the chicken cross the road?", bot.getFavoriteJoke());
        assertEquals("To be or not to be", bot.getFavoriteQuote());
        assertEquals("Eagle", bot.getFavoriteAnimal());
        assertEquals(Status.ACTIVE, bot.getStatus());
        assertEquals(Behavior.FRIENDLY, bot.getBehavior());
        assertEquals(10, bot.getFaithLevel());
    }
}
