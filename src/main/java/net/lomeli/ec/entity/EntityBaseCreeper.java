package net.lomeli.ec.entity;

import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityBaseCreeper extends EntityCreeper {

    protected int lastActiveTime;
    protected int timeSinceIgnited;
    protected int fuseTime = 30;
    protected int explosionRadius = 3;

    public EntityBaseCreeper(World par1World) {
        super(par1World);
    }

    @Override
    protected void fall(float par1) {
        super.fall(par1);
        this.timeSinceIgnited = (int) ((float) this.timeSinceIgnited + par1 * 1.5F);

        if (this.timeSinceIgnited > this.fuseTime - 5)
            this.timeSinceIgnited = this.fuseTime - 5;
    }

    @Override
    public void onUpdate() {
        if (this.isEntityAlive()) {
            this.lastActiveTime = this.timeSinceIgnited;
            int i = this.getCreeperState();

            if (i > 0 && this.timeSinceIgnited == 0)
                this.playSound("random.fuse", 1.0F, 0.5F);

            this.timeSinceIgnited += i;

            if (this.timeSinceIgnited < 0)
                this.timeSinceIgnited = 0;

            if (this.timeSinceIgnited >= this.fuseTime) {
                this.timeSinceIgnited = this.fuseTime;

                if (!this.worldObj.isRemote) {
                    boolean flag = this.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");

                    if (this.getPowered())
                        this.explosion(2, flag);
                    else
                        this.explosion(1, flag);

                    if (this.diesAfterExplosion())
                        this.setDead();

                    worldObj.playSoundEffect(posX, posY, posZ, "random.explode", 4F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);
                    spawnExplosionParticle();
                }
            }
        }

        super.onUpdate();
    }

    public void explosion(int power, boolean flag) {

    }

    public boolean diesAfterExplosion() {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public float getCreeperFlashIntensity(float par1) {
        return ((float) this.lastActiveTime + (float) (this.timeSinceIgnited - this.lastActiveTime) * par1) / (float) (this.fuseTime - 2);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);

        if (this.dataWatcher.getWatchableObjectByte(17) == 1)
            par1NBTTagCompound.setBoolean("powered", true);

        par1NBTTagCompound.setShort("Fuse", (short) this.fuseTime);
        par1NBTTagCompound.setByte("ExplosionRadius", (byte) this.explosionRadius);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.dataWatcher.updateObject(17, Byte.valueOf((byte) (par1NBTTagCompound.getBoolean("powered") ? 1 : 0)));

        if (par1NBTTagCompound.hasKey("Fuse"))
            this.fuseTime = par1NBTTagCompound.getShort("Fuse");

        if (par1NBTTagCompound.hasKey("ExplosionRadius"))
            this.explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");
    }
}
