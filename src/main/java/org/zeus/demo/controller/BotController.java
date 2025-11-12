package org.zeus.demo.controller;

import org.springframework.web.bind.annotation.*;
import org.zeus.demo.dto.BotStateDTO;
import org.zeus.demo.model.Bot;
import org.zeus.demo.service.BotService;

import java.util.List;

@RestController
@RequestMapping("/bots")
public class BotController {

    private final BotService botService;

    public BotController(BotService botService) {
        this.botService = botService;
    }

    @PostMapping("/create")
    public Bot createBot() {
        return botService.createBot();
    }

    @GetMapping
    public List<Bot> getAllBots() {
        return botService.getAllBots();
    }

    @PutMapping("/{id}/status")
    public Bot updateStatus(@PathVariable Long id, @RequestParam String status) {
        return botService.updateBotStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteBot(@PathVariable Long id) {
        botService.deleteBot(id);
    }

    @GetMapping("/state")
    public List<BotStateDTO> getBotsState() {
        return botService.getBotsState();
    }

}
