package com.momosoftworks.coldsweat.core.event.compat.client;

import com.momosoftworks.coldsweat.core.event.Event;

public abstract class ViewportEvent extends Event
{
    public static final class RenderFog extends ViewportEvent
    {
        private float nearPlaneDistance;
        private float farPlaneDistance;

        public RenderFog(float nearPlaneDistance, float farPlaneDistance)
        {
            this.nearPlaneDistance = nearPlaneDistance;
            this.farPlaneDistance = farPlaneDistance;
        }

        public float getNearPlaneDistance()
        {   return nearPlaneDistance;
        }

        public void setNearPlaneDistance(float distance)
        {   this.nearPlaneDistance = distance;
        }

        public float getFarPlaneDistance()
        {   return farPlaneDistance;
        }

        public void setFarPlaneDistance(float distance)
        {   this.farPlaneDistance = distance;
        }
    }

    public static final class ComputeFogColor extends ViewportEvent
    {
        private float red;
        private float green;
        private float blue;

        public ComputeFogColor(float red, float green, float blue)
        {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }

        public float getRed()
        {   return red;
        }

        public void setRed(float red)
        {   this.red = red;
        }

        public float getGreen()
        {   return green;
        }

        public void setGreen(float green)
        {   this.green = green;
        }

        public float getBlue()
        {   return blue;
        }

        public void setBlue(float blue)
        {   this.blue = blue;
        }
    }

    public static final class ComputeCameraAngles extends ViewportEvent
    {
        private final float partialTick;

        public ComputeCameraAngles(float partialTick)
        {   this.partialTick = partialTick;
        }

        public float getPartialTick()
        {   return partialTick;
        }
    }
}
