package net.lomeli.ec.entity;

import java.awt.*;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ModVars;

public class EntityFireworkCreeper extends EntityBaseCreeper {
    private ItemStack firework;

    public EntityFireworkCreeper(World world) {
        super(world);
    }

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (ModVars.fireworkCreeperRadius * power) : ModVars.fireworkCreeperRadius;
        getRandomColorFireWork();
        if (!worldObj.isRemote) {
            EntityFireworkRocket rocket = new EntityFireworkRocket(worldObj, posX, posY, posZ, this.firework.copy());
            worldObj.spawnEntityInWorld(rocket);
        }
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(radius, radius, radius));
        if (list != null && !list.isEmpty()) {
            for (Entity entity : list) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entityLiving = (EntityLivingBase) entity;
                    if (rand.nextInt(2) == 0) {
                        if (!worldObj.isRemote) {
                            getRandomColorFireWork();
                            EntityFireworkRocket rocket = new EntityFireworkRocket(worldObj, entityLiving.posX, entityLiving.posY, entityLiving.posZ, this.firework.copy());
                            worldObj.spawnEntityInWorld(rocket);
                            entityLiving.mountEntity(rocket);
                        }
                    }
                }
            }
        }
    }

    public void getRandomColorFireWork() {
        this.firework = new ItemStack(Items.fireworks, 1);
        this.firework.setTagCompound(new NBTTagCompound());
        NBTTagCompound data = new NBTTagCompound();
        data.setByte("Flight", (byte) 1);
        NBTTagList list = new NBTTagList();
        NBTTagCompound fireworkData = new NBTTagCompound();
        fireworkData.setByte("Trail", (byte) 1);
        fireworkData.setByte("Type", (byte) 3);
        fireworkData.setIntArray("Colors", new int[]{new Color(this.rand.nextInt(255), this.rand.nextInt(255), this.rand.nextInt(255)).getRGB()});
        list.appendTag(fireworkData);
        data.setTag("Explosions", list);
        this.firework.getTagCompound().setTag("Fireworks", data);
    }
}
