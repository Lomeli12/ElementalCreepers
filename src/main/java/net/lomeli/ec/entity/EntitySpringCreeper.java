package net.lomeli.ec.entity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntitySpringCreeper extends EntityBaseCreeper {
    private boolean isSprung;
    private int radius;

    public EntitySpringCreeper(World world) {
        super(world);
        this.explosionRadius = ECVars.springCreeperPower;
    }

    @Override
    public boolean diesAfterExplosion() {
        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (isSprung() && !this.worldObj.isRemote)
            spawnExplosionParticle();
    }

    @Override
    public void explosion(int power, boolean flag) {
        radius = getPowered() ? (int) (this.explosionRadius * 1.5F) : this.explosionRadius;
        if (this.worldObj.isRemote)
            spawnExplosionParticle();
        if (!isSprung()) {
            this.motionY = 1.5f;
            this.isSprung = true;
        }
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound tag) {
        super.readEntityFromNBT(tag);
        this.isSprung = tag.getBoolean("isSprung");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound tag) {
        super.writeEntityToNBT(tag);
        tag.setBoolean("isSprung", this.isSprung);
    }

    public boolean isSprung() {
        return this.isSprung;
    }

    public int getExplosionRadius() {
        return this.radius;
    }
}
