package com.niek125.roleconsumer.events;

import com.niek125.roleconsumer.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserRoleChangedEvent extends DataEditorEvent {
    private Role role;
}
