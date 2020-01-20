package com.niek125.roleconsumer.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DataEditorEvent {
    private String creator;
    private String interest;

    public DataEditorEvent() {
        this.creator = "role-consumer";
    }
}
