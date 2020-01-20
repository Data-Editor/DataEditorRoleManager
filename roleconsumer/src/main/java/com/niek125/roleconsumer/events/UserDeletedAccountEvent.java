package com.niek125.roleconsumer.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDeletedAccountEvent extends DataEditorEvent {
    private String userid;
}
