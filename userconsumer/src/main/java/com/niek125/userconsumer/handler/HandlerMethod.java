package com.niek125.userconsumer.handler;

import com.niek125.userconsumer.events.DataEditorEvent;

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
