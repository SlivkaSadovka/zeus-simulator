package org.zeus.demo;

import org.zeus.demo.model.Bot;
import org.zeus.demo.dto.BotStateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BotIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Пользовательская история:
     * 1. Удаляем всех ботов
     * 2. Создаём бота
     * 3. Проверяем, что он появился
     * 4. Удаляем его
     */
    @Test
    void userCanCreateAndDeleteBot() throws Exception {

        // 1. Очистка
        mockMvc.perform(delete("/bots/delete-all"))
                .andExpect(status().isOk());

        // 2. Создание бота
        String createResponse = mockMvc.perform(post("/bots/create"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Bot createdBot = objectMapper.readValue(createResponse, Bot.class);
        assertThat(createdBot.getId()).isNotNull();

        // 3. Проверка состояния
        String stateResponse = mockMvc.perform(get("/bots/state"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<BotStateDTO> bots =
                objectMapper.readValue(
                        stateResponse,
                        objectMapper.getTypeFactory()
                                .constructCollectionType(List.class, BotStateDTO.class)
                );

        assertThat(bots).isNotEmpty();

        // 4. Удаление бота
        mockMvc.perform(delete("/bots/delete/" + createdBot.getId()))
                .andExpect(status().isOk());
    }
}
