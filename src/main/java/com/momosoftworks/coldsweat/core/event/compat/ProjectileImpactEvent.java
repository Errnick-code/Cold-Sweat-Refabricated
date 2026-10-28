package com.momosoftworks.coldsweat.core.event.compat;

import com.momosoftworks.coldsweat.core.event.Cancellable;
import com.momosoftworks.coldsweat.core.event.Event;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileImpactEvent extends Event implements Cancellable
{
    private final Projectile projectile;
    private final HitResult rayTraceResult;

    public ProjectileImpactEvent(Projectile projectile, HitResult rayTraceResult)
    {   this.projectile = projectile;
        this.rayTraceResult = rayTraceResult;
    }

    public Projectile getProjectile()
    {   return projectile;
    }

    public HitResult getRayTraceResult()
    {   return rayTraceResult;
    }
}