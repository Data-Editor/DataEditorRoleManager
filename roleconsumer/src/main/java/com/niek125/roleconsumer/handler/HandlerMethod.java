package com.niek125.roleconsumer.handler;

import com.niek125.roleconsumer.events.DataEditorEvent;

public abstract class HandlerMethod<T extends DataEditorEvent> {
    private final Class<T> claz;

    protected HandlerMethod(Class<T> claz) {
        this.claz = claz;
    }

    public Class getHandlingType() {
        return claz;
    }

    public abstract void handle(DataEditorEvent event);
}
