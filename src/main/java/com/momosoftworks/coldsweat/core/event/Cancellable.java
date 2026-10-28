package com.momosoftworks.coldsweat.core.event;

public interface Cancellable
{
    boolean isCanceled();

    void setCanceled(boolean canceled);
}