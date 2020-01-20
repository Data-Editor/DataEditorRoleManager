package com.niek125.roleconsumer.events;

import com.niek125.roleconsumer.models.Project;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProjectCreatedEvent extends DataEditorEvent {
    private Project project;
    private String creatorid;
}
