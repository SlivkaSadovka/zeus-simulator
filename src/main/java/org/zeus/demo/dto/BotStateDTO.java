package org.zeus.demo.dto;

import lombok.Getter;
import lombok.Setter;
import org.zeus.demo.model.Behavior;
import org.zeus.demo.model.Status;

@Getter
public class BotStateDTO {
    private Long id;
    private String name;
    private Status status;
    private Behavior behavior;

    public BotStateDTO(Long id, String name, Status status, Behavior behavior) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.behavior = behavior;
    }
}
