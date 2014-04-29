package net.lomeli.ec.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import net.lomeli.ec.lib.ECVars;

public class EntitySpiderCreeper extends EntityBaseCreeper {

    public EntitySpiderCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.spiderCreeperRadius;
    }

    @Override
    public void explosion(int power, boolean flag) {
        float radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (float x = -radius; x <= radius; x++)
            for (float y = -radius; y <= radius; y++)
                for (float z = -radius; z <= radius; z++) {
                    if (rand.nextInt(100) < 2 && worldObj.isAirBlock((int) (posX + x), (int) (posY + y), (int) (posZ + z)))
                        worldObj.setBlock((int) (posX + x), (int) (posY + y), (int) (posZ + z), Blocks.web);
                }

        @SuppressWarnings("unchecked")
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(radius, radius, radius));

        byte difficulty = 0;
        if (this.worldObj.difficultySetting.getDifficultyId() == 1)
            difficulty = 7;
        else if (this.worldObj.difficultySetting.getDifficultyId() == 2)
            difficulty = 10;
        else if (this.worldObj.difficultySetting.getDifficultyId() == 3)
            difficulty = 15;

        if (difficulty > 0) {
            for (Entity entity : list) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entityLiving = (EntityLivingBase) entity;
                    if (entityLiving.getDistanceToEntity(this) < 30)
                        entityLiving.addPotionEffect(new PotionEffect(Potion.poison.id, difficulty * 20, 0));
                }
            }
        }
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(20, new Byte((byte) 0));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.worldObj.isRemote)
            this.setBesideClimbableBlock(this.isCollidedHorizontally);
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean par1) {
        byte b0 = this.dataWatcher.getWatchableObjectByte(20);

        if (par1)
            b0 = (byte) (b0 | 1);
        else
            b0 &= -2;

        this.dataWatcher.updateObject(20, Byte.valueOf(b0));
    }

    @Override
    protected Entity findPlayerToAttack() {
        float f = this.getBrightness(1.0F);

        if (f < 0.5F) {
            double d0 = 16.0D;
            return this.worldObj.getClosestVulnerablePlayerToEntity(this, d0);
        }else
            return null;
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

}
