package net.lomeli.ec.core.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.ec.lib.ModLib;

public class BlockRadiation extends Block {
    public BlockRadiation() {
        super(Material.leaves);
        this.translucent = true;
        this.setTickRandomly(true);
        this.setUnlocalizedName(ModLib.MOD_ID.toLowerCase() + ".radiationBlock");
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
        return null;
        //float f = 0.9F;
        //return new AxisAlignedBB((double) ((float) pos.getX() + f), (double) pos.getY(), (double) ((float) pos.getZ() + f), (double) ((float) (pos.getX() + 1) - f), (double) ((float) (pos.getY() + 1) - f), (double) ((float) (pos.getZ() + 1) - f));
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isFullCube() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public float getAmbientOcclusionLightValue() {
        return 1.0F;
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        if (!world.isRemote && (rand.nextFloat() < 0.15f || world.isAirBlock(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ()))))
            world.setBlockToAir(pos);
    }

    @Override
    public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand) {
        double d0 = (double) pos.getX() + (double) rand.nextFloat();
        double d1 = (double) ((float) pos.getY() + 0.0625F);
        double d2 = (double) pos.getZ() + (double) rand.nextFloat();
        world.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, 0f, 1f, 0f, 1, 1, 1);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity) {
        if (entity instanceof EntityLivingBase)
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.poison.id, 100, 1));
    }
}
