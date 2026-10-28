package com.momosoftworks.coldsweat.core.event;

public abstract class Event
{
    private boolean canceled;

    public void setCanceled(boolean canceled)
    {
        this.canceled = canceled;
    }

    public boolean isCanceled()
    {
        return this.canceled;
    }

    public void cancel()
    {
        this.canceled = true;
    }
}