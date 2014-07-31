package net.lomeli.ec.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import net.lomeli.ec.ElementalCreepers;

public class EntityIllusionCreeper extends EntityBaseCreeper implements IIllusion {
    public boolean split, illusion;

    public EntityIllusionCreeper(World par1World) {
        super(par1World);
        this.illusion = false;
        this.explosionSound = true;
        this.split = false;
    }

    @Override
    public void onUpdate() {
        if (!worldObj.isRemote) {
            if (!illusion) {
                EntityPlayer player = worldObj.getClosestPlayerToEntity(this, 8F);
                if (!split && player != null && !player.capabilities.isCreativeMode) {
                    createFakeCreepersAndLaunchSelf();
                    split = true;
                }
            } else
                this.explosionSound = false;
        }
        super.onUpdate();
    }

    @Override
    public void explosion(int power, boolean flag) {
        if (!illusion) {
            int exPower = this.explosionRadius * power;
            this.worldObj.createExplosion(this, posX, posY, posZ, exPower, flag);
        } else
            this.spawnExplosionParticle();
    }

    private void createFakeCreepersAndLaunchSelf() {
        if (!worldObj.isRemote) {
            ElementalCreepers.proxy.spawnIllusionCreepers(worldObj, posX, posY, posZ);
            this.motionY = 0.5F;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("split", split);
        par1NBTTagCompound.setBoolean("isIllusion", illusion);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        split = par1NBTTagCompound.getBoolean("split");
        illusion = par1NBTTagCompound.getBoolean("isIllusion");
    }

    @Override
    public boolean isIllusion() {
        return this.illusion;
    }
}
