package org.zeus.demo.dto;

import org.zeus.demo.model.Status;

public class BotStateDTO {
    private Long id;
    private String name;
    private Status status;

    public BotStateDTO(Long id, String name, Status status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    // геттеры и сеттеры
    public Long getId() { return id; }
    public String getName() { return name; }
    public Status getStatus() { return status; }
}
