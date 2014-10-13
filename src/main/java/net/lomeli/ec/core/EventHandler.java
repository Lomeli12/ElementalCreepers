package net.lomeli.ec.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.DamageSource;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.ec.entity.EntityFriendlyCreeper;
import net.lomeli.ec.entity.EntityGhostCreeper;
import net.lomeli.ec.entity.EntitySpringCreeper;
import net.lomeli.ec.entity.IIllusion;
import net.lomeli.ec.lib.ECVars;

public class EventHandler {
    @SubscribeEvent
    public void entityHurt(LivingHurtEvent event) {
        EntityLivingBase entity = event.entityLiving;
        DamageSource source = event.source;
        if (entity != null && source != null) {
            if (entity instanceof EntitySpringCreeper) {
                EntitySpringCreeper creeper = (EntitySpringCreeper) entity;
                if (source == DamageSource.fall && creeper.isSprung() && !creeper.worldObj.isRemote) {
                    creeper.worldObj.createExplosion(creeper, creeper.posX, creeper.posY, creeper.posZ, creeper.getExplosionRadius(), creeper.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                    creeper.setDead();
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        boolean activate = false;

        if (event.source.getDamageType().equals("player"))
            activate = true;

        if (event.source.getSourceOfDamage() instanceof EntityArrow) {
            if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity != null) {
                if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity instanceof EntityPlayer)
                    activate = true;
            }
        }

        if (activate && event.entityLiving != null && (event.entityLiving instanceof EntityCreeper) && !(event.entityLiving instanceof EntityGhostCreeper)
                && !(event.entityLiving instanceof EntityFriendlyCreeper)) {
            if (event.entityLiving instanceof IIllusion) {
                if (((IIllusion) event.entityLiving).isIllusion())
                    return;
            }

            if (event.entityLiving.worldObj.rand.nextInt(100) < ECVars.ghostCreeperChance) {
                EntityGhostCreeper ghost = new EntityGhostCreeper(event.entityLiving.worldObj);
                ghost.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving.rotationYaw, event.entityLiving.rotationPitch);
                event.entityLiving.worldObj.spawnEntityInWorld(ghost);
            }
        }
    }
}
