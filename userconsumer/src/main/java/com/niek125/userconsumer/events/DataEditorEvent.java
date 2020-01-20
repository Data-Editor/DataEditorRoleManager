package com.niek125.userconsumer.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class DataEditorEvent {
    private String creator;

    public DataEditorEvent() {
        this.creator = "user-consumer";
    }
}
