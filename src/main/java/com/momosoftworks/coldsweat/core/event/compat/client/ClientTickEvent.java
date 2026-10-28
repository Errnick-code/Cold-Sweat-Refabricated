package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;

public abstract class ClientTickEvent extends Event
{
    public static final class Pre extends ClientTickEvent {}
    public static final class Post extends ClientTickEvent {}
}
