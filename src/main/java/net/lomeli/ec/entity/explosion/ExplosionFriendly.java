package net.lomeli.ec.entity.explosion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.lomeli.ec.entity.EntityFriendlyCreeper;

public class ExplosionFriendly extends Explosion {
    /** whether or not the explosion sets fire to blocks around it */
    public boolean isFlaming;

    /** whether or not this explosion spawns smoke particles */
    public boolean isSmoking = true;
    private int field_77289_h = 16;
    private Random explosionRNG = new Random();
    private World worldObj;
    public double explosionX;
    public double explosionY;
    public double explosionZ;
    public EntityFriendlyCreeper exploder;
    public float explosionSize;

    /** A list of ChunkPositions of blocks affected by this explosion */
    @SuppressWarnings("rawtypes")
    public List affectedBlockPositions = new ArrayList();
    @SuppressWarnings("rawtypes")
    private Map field_77288_k = new HashMap();

    public ExplosionFriendly(World world, EntityFriendlyCreeper entity, double xPos, double yPos, double zPos, float size) {
        super(world, entity, xPos, yPos, zPos, size);
        this.worldObj = world;
        this.exploder = entity;
        this.explosionSize = size;
        this.explosionX = xPos;
        this.explosionY = yPos;
        this.explosionZ = zPos;
    }

    @Override
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void doExplosionA() {
        float f = this.explosionSize;
        int i;
        int j;
        int k;
        double d0;
        double d1;
        double d2;

        for (i = 0; i < this.field_77289_h; ++i) {
            for (j = 0; j < this.field_77289_h; ++j) {
                for (k = 0; k < this.field_77289_h; ++k) {
                    if (i == 0 || i == this.field_77289_h - 1 || j == 0 || j == this.field_77289_h - 1 || k == 0 || k == this.field_77289_h - 1) {
                        double d3 = i / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
                        double d4 = j / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
                        double d5 = k / (this.field_77289_h - 1.0F) * 2.0F - 1.0F;
                        double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
                        d3 /= d6;
                        d4 /= d6;
                        d5 /= d6;
                        float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                        d0 = this.explosionX;
                        d1 = this.explosionY;
                        d2 = this.explosionZ;

                        for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                            d0 += d3 * f2;
                            d1 += d4 * f2;
                            d2 += d5 * f2;
                        }
                    }
                }
            }
        }

        this.explosionSize *= 2.0F;
        i = MathHelper.floor_double(this.explosionX - this.explosionSize - 1.0D);
        j = MathHelper.floor_double(this.explosionX + this.explosionSize + 1.0D);
        k = MathHelper.floor_double(this.explosionY - this.explosionSize - 1.0D);
        int l1 = MathHelper.floor_double(this.explosionY + this.explosionSize + 1.0D);
        int i2 = MathHelper.floor_double(this.explosionZ - this.explosionSize - 1.0D);
        int j2 = MathHelper.floor_double(this.explosionZ + this.explosionSize + 1.0D);
        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this.exploder, AxisAlignedBB.getAABBPool().getAABB(i, k, i2, j, l1, j2));
        Vec3 vec3 = this.worldObj.getWorldVec3Pool().getVecFromPool(this.explosionX, this.explosionY, this.explosionZ);

        for (int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = (Entity) list.get(k2);
            double d7 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / this.explosionSize;

            if (d7 <= 1.0D) {
                d0 = entity.posX - this.explosionX;
                d1 = entity.posY + entity.getEyeHeight() - this.explosionY;
                d2 = entity.posZ - this.explosionZ;
                double d8 = MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);

                if (d8 != 0.0D) {
                    d0 /= d8;
                    d1 /= d8;
                    d2 /= d8;
                    double d9 = this.worldObj.getBlockDensity(vec3, entity.boundingBox);
                    double d10 = (1.0D - d7) * d9;
                    double d11 = EnchantmentProtection.func_92092_a(entity, d10);
                    boolean hurtEntity = true;
                    if (entity instanceof EntityTameable) {
                        if (((EntityTameable) entity).isTamed()) {
                            if (!((EntityTameable) entity).getOwner().equals(exploder.getOwner()))
                                hurtEntity = ((EntityTameable) entity).getAttackTarget().equals(exploder.getOwner()) || ((EntityTameable) entity).getOwner().equals(exploder.getOwner());
                            else
                                hurtEntity = false;
                        }else
                            hurtEntity = ((EntityTameable) entity).getAttackTarget() != null ? ((EntityTameable) entity).getAttackTarget().equals(exploder.getOwner())
                                    || ((EntityTameable) entity).getAttackTarget().equals(exploder) : false;
                    }

                    if (exploder.getOwner().equals(entity))
                        hurtEntity = false;

                    if (hurtEntity) {
                        System.out.println("Attacking " + entity.toString() + " for " + (float) ((int) ((d10 * d10 + d10) / 2.0D * 8.0D * this.explosionSize + 1.0D)));
                        entity.attackEntityFrom(DamageSource.setExplosionSource(this), ((int) ((d10 * d10 + d10) / 2.0D * 8.0D * this.explosionSize + 1.0D)));
                        entity.motionX += (d0 * d11);
                        entity.motionY += (d1 * d11);
                        entity.motionZ += (d2 * d11);

                        if (entity instanceof EntityPlayer)
                            this.field_77288_k.put(entity, this.worldObj.getWorldVec3Pool().getVecFromPool(d0 * d10, d1 * d10, d2 * d10));
                    }
                }
            }
        }

        this.explosionSize = f;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public void doExplosionB(boolean par1) {
        this.worldObj.playSoundEffect(this.explosionX, this.explosionY, this.explosionZ, "random.explode", 4.0F, (1.0F + (this.worldObj.rand.nextFloat() - this.worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

        if (this.explosionSize >= 2.0F && this.isSmoking)
            this.worldObj.spawnParticle("hugeexplosion", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);
        else
            this.worldObj.spawnParticle("largeexplode", this.explosionX, this.explosionY, this.explosionZ, 1.0D, 0.0D, 0.0D);

        Iterator iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block l;

        if (this.isSmoking) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                l = this.worldObj.getBlock(i, j, k);

                if (par1) {
                    double d0 = i + this.worldObj.rand.nextFloat();
                    double d1 = j + this.worldObj.rand.nextFloat();
                    double d2 = k + this.worldObj.rand.nextFloat();
                    double d3 = d0 - this.explosionX;
                    double d4 = d1 - this.explosionY;
                    double d5 = d2 - this.explosionZ;
                    double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    double d7 = 0.5D / (d6 / this.explosionSize + 0.1D);
                    d7 *= this.worldObj.rand.nextFloat() * this.worldObj.rand.nextFloat() + 0.3F;
                    d3 *= d7;
                    d4 *= d7;
                    d5 *= d7;
                    this.worldObj.spawnParticle("explode", (d0 + this.explosionX * 1.0D) / 2.0D, (d1 + this.explosionY * 1.0D) / 2.0D, (d2 + this.explosionZ * 1.0D) / 2.0D, d3, d4, d5);
                    this.worldObj.spawnParticle("smoke", d0, d1, d2, d3, d4, d5);
                }
            }
        }

        if (this.isFlaming) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = (ChunkPosition) iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                l = this.worldObj.getBlock(i, j, k);
                Block i1 = this.worldObj.getBlock(i, j - 1, k);

                if (l == null && i1.isOpaqueCube() && this.explosionRNG.nextInt(3) == 0)
                    this.worldObj.setBlock(i, j, k, Blocks.fire);
            }
        }
    }

}
