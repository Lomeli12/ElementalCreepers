package net.lomeli.ec.entity.explosion;

import com.google.common.collect.Sets;

import java.util.*;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.lomeli.ec.entity.EntityFriendlyCreeper;

public class ExplosionFriendly extends Explosion {
    /**
     * whether or not the explosion sets fire to blocks around it
     */
    public boolean isFlaming;

    /**
     * whether or not this explosion spawns smoke particles
     */
    public boolean isSmoking = true;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public EntityFriendlyCreeper exploder;
    public float explosionSize;
    /**
     * A list of ChunkPositions of blocks affected by this explosion
     */
    @SuppressWarnings("rawtypes")
    public List affectedBlockPositions = new ArrayList();
    private World worldObj;
    @SuppressWarnings("rawtypes")
    private Map field_77288_k = new HashMap();

    public ExplosionFriendly(World world, EntityFriendlyCreeper entity, double xPos, double yPos, double zPos, float size) {
        super(world, entity, xPos, yPos, zPos, size, false, false);
        this.worldObj = world;
        this.exploder = entity;
        this.explosionSize = size;
        this.explosionX = xPos;
        this.explosionY = yPos;
        this.explosionZ = zPos;
    }

    @Override
    public void doExplosionA() {
        HashSet hashset = Sets.newHashSet();
        boolean flag = true;
        int j;
        int k;

        for (int i = 0; i < 16; ++i) {
            for (j = 0; j < 16; ++j) {
                for (k = 0; k < 16; ++k) {
                    if (i == 0 || i == 15 || j == 0 || j == 15 || k == 0 || k == 15) {
                        double d0 = (double) ((float) i / 15.0F * 2.0F - 1.0F);
                        double d1 = (double) ((float) j / 15.0F * 2.0F - 1.0F);
                        double d2 = (double) ((float) k / 15.0F * 2.0F - 1.0F);
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        double d4 = this.explosionX;
                        double d6 = this.explosionY;
                        double d8 = this.explosionZ;

                        for (float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            IBlockState iblockstate = this.worldObj.getBlockState(blockpos);

                            if (iblockstate.getBlock().getMaterial() != Material.air) {
                                float f2 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(worldObj, blockpos, (Entity) null, this);
                                f -= (f2 + 0.3F) * 0.3F;
                            }

                            /*
                            if (f > 0.0F && (this.exploder == null || this.exploder.func_174816_a(this, this.worldObj, blockpos, iblockstate, f))) {
                                hashset.add(blockpos);
                            }*/

                            d4 += d0 * 0.30000001192092896D;
                            d6 += d1 * 0.30000001192092896D;
                            d8 += d2 * 0.30000001192092896D;
                        }
                    }
                }
            }
        }

        this.affectedBlockPositions.addAll(hashset);
        float f3 = this.explosionSize * 2.0F;
        j = MathHelper.floor_double(this.explosionX - (double) f3 - 1.0D);
        k = MathHelper.floor_double(this.explosionX + (double) f3 + 1.0D);
        int j1 = MathHelper.floor_double(this.explosionY - (double) f3 - 1.0D);
        int l = MathHelper.floor_double(this.explosionY + (double) f3 + 1.0D);
        int k1 = MathHelper.floor_double(this.explosionZ - (double) f3 - 1.0D);
        int i1 = MathHelper.floor_double(this.explosionZ + (double) f3 + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, new AxisAlignedBB((double) j, (double) j1, (double) k1, (double) k, (double) l, (double) i1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, f3);
        Vec3 vec3 = new Vec3(this.explosionX, this.explosionY, this.explosionZ);

        for (int l1 = 0; l1 < list.size(); ++l1) {
            Entity entity = (Entity) list.get(l1);

            if (!entity.isImmuneToExplosions()) {
                double d12 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double) f3;

                if (d12 <= 1.0D) {
                    double d5 = entity.posX - this.explosionX;
                    double d7 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
                    double d9 = entity.posZ - this.explosionZ;
                    double d13 = (double) MathHelper.sqrt_double(d5 * d5 + d7 * d7 + d9 * d9);

                    if (d13 != 0.0D) {
                        d5 /= d13;
                        d7 /= d13;
                        d9 /= d13;
                        double d14 = (double) this.worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
                        double d10 = (1.0D - d12) * d14;
                        double d11 = EnchantmentProtection.func_92092_a(entity, d10);
                        boolean hurtEntity = true;
                        if (entity instanceof EntityTameable) {
                            if (((EntityTameable) entity).isTamed()) {
                                if (!((EntityTameable) entity).getOwner().equals(exploder.getOwner()))
                                    hurtEntity = ((EntityTameable) entity).getAttackTarget().equals(exploder.getOwner()) || ((EntityTameable) entity).getOwner().equals(exploder.getOwner());
                                else
                                    hurtEntity = false;
                            } else
                                hurtEntity = ((EntityTameable) entity).getAttackTarget() != null ? ((EntityTameable) entity).getAttackTarget().equals(exploder.getOwner())
                                        || ((EntityTameable) entity).getAttackTarget().equals(exploder) : false;
                        }

                        if (exploder.getOwner().equals(entity))
                            hurtEntity = false;

                        if (hurtEntity) {
                            entity.attackEntityFrom(DamageSource.setExplosionSource(this), ((int) ((d10 * d10 + d10) / 2.0D * 8.0D * this.explosionSize + 1.0D)));
                            entity.motionX += (d5 * d11);
                            entity.motionY += (d7 * d11);
                            entity.motionZ += (d9 * d11);

                            if (entity instanceof EntityPlayer)
                                this.field_77288_k.put(entity, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                        }
                    }
                }
            }
        }
    }
}
