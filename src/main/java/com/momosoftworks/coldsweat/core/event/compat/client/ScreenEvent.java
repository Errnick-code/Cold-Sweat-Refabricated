package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;

import java.lang.reflect.Method;

public abstract class ScreenEvent extends Event
{
    private final Screen screen;

    protected ScreenEvent(Screen screen)
    {   this.screen = screen;
    }

    public Screen getScreen()
    {   return screen;
    }

    public static class Init extends ScreenEvent
    {
        public Init(Screen screen)
        {   super(screen);
        }

        public static final class Post extends Init
        {
            public Post(Screen screen)
            {   super(screen);
            }

            public void addListener(GuiEventListener listener)
            {
                try
                {
                    Method method = Screen.class.getDeclaredMethod("addRenderableWidget", GuiEventListener.class);
                    method.setAccessible(true);
                    method.invoke(this.getScreen(), listener);
                }
                catch (ReflectiveOperationException e)
                {   throw new IllegalStateException("Could not add screen listener", e);
                }
            }
        }
    }

    public static class Render extends ScreenEvent
    {
        private final GuiGraphicsExtractor graphics;
        private final int mouseX;
        private final int mouseY;

        public Render(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY)
        {
            super(screen);
            this.graphics = graphics;
            this.mouseX = mouseX;
            this.mouseY = mouseY;
        }

        public GuiGraphicsExtractor getGuiGraphics()
        {   return graphics;
        }

        public int getMouseX()
        {   return mouseX;
        }

        public int getMouseY()
        {   return mouseY;
        }

        public static final class Post extends Render
        {
            public Post(Screen screen, GuiGraphicsExtractor graphics, int mouseX, int mouseY)
            {   super(screen, graphics, mouseX, mouseY);
            }
        }
    }

    public static class MouseButtonPressed extends ScreenEvent
    {
        private final double mouseX;
        private final double mouseY;
        private final int button;

        public MouseButtonPressed(Screen screen, double mouseX, double mouseY, int button)
        {
            super(screen);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.button = button;
        }

        public double getMouseX()
        {   return mouseX;
        }

        public double getMouseY()
        {   return mouseY;
        }

        public int getButton()
        {   return button;
        }

        public static final class Pre extends MouseButtonPressed implements Cancellable
        {
            public Pre(Screen screen, double mouseX, double mouseY, int button)
            {   super(screen, mouseX, mouseY, button);
            }
        }

        public static final class Post extends MouseButtonPressed
        {
            public Post(Screen screen, double mouseX, double mouseY, int button)
            {   super(screen, mouseX, mouseY, button);
            }
        }
    }

    public static class MouseButtonReleased extends ScreenEvent
    {
        private final double mouseX;
        private final double mouseY;
        private final int button;

        public MouseButtonReleased(Screen screen, double mouseX, double mouseY, int button)
        {
            super(screen);
            this.mouseX = mouseX;
            this.mouseY = mouseY;
            this.button = button;
        }

        public double getMouseX()
        {   return mouseX;
        }

        public double getMouseY()
        {   return mouseY;
        }

        public int getButton()
        {   return button;
        }

        public static final class Pre extends MouseButtonReleased implements Cancellable
        {
            public Pre(Screen screen, double mouseX, double mouseY, int button)
            {   super(screen, mouseX, mouseY, button);
            }
        }
    }
}
