package org.zeus.demo.service;

import org.springframework.stereotype.Service;
import org.zeus.demo.dto.BotStateDTO;
import org.zeus.demo.model.Bot;
import org.zeus.demo.model.Status;
import org.zeus.demo.repository.BotRepository;
import java.util.List;
import java.util.Optional;

@Service
public class BotService {

    private final BotRepository botRepository;
    private final PersonalityRandomGeneratorService personalityGenerator;

    public BotService(BotRepository botRepository, PersonalityRandomGeneratorService personalityGenerator) {
        this.botRepository = botRepository;
        this.personalityGenerator = personalityGenerator;
    }

    public Bot createBot() {
        Bot bot = new Bot();
        bot.setFirstName(PersonalityRandomGeneratorService.generateFirstName());
        bot.setLastName(PersonalityRandomGeneratorService.generateLastName());
        bot.setStatus(Status.ALIVE);
        return botRepository.save(bot);
    }

    public List<Bot> getAllBots() {
        return botRepository.findAll();
    }

    public Bot blessBot(Long id) {
        Bot bot = botRepository.findById(id).orElseThrow(() -> new RuntimeException("Bot not found"));
        bot.setFaithLevel(bot.getFaithLevel() + 10);
        return botRepository.save(bot);
    }

    public Bot smiteBot(Long id) {
        Bot bot = botRepository.findById(id).orElseThrow(() -> new RuntimeException("Bot not found"));
        bot.setStatus(Status.DEAD);
        return botRepository.save(bot);
    }
    public Bot updateBotStatus(Long id, String statusString) {
        Optional<Bot> optionalBot = botRepository.findById(id);
        if (optionalBot.isEmpty()) {
            throw new RuntimeException("Bot not found with id: " + id);
        }

        Bot bot = optionalBot.get();

        try {
            Status newStatus = Status.valueOf(statusString.toUpperCase());
            bot.setStatus(newStatus);
            return botRepository.save(bot);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + statusString);
        }
    }

    // Удалить бота
    public void deleteBot(Long id) {
        if (!botRepository.existsById(id)) {
            throw new RuntimeException("Bot not found with id: " + id);
        }
        botRepository.deleteById(id);
    }

    public List<BotStateDTO> getBotsState() {
        return botRepository.findAll().stream()
                .map(bot -> new BotStateDTO(bot.getId(), bot.getFirstName(), bot.getStatus()))
                .toList();
    }


}
