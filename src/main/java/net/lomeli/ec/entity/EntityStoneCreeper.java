package net.lomeli.ec.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import net.lomeli.ec.lib.ModVars;

public class EntityStoneCreeper extends EntityBaseCreeper {
    private List<Block> blockList = new ArrayList<Block>();

    public EntityStoneCreeper(World par1World) {
        super(par1World);
    }

    @Override
    public void explosion(int power, boolean flag) {
        genList();
        int radius = getPowered() ? (ModVars.stoneCreeperRadius * power) : ModVars.stoneCreeperRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    BlockPos pos = new BlockPos((int) posX + x, (int) posY + y, (int) posZ + z);
                    IBlockState blockState = worldObj.getBlockState(pos);
                    if (blockState != null && blockState.getBlock() != null) {
                        Block bk = blockState.getBlock();
                        if (bk != null && this.blockList.contains(bk) && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= radius) {
                            bk.dropBlockAsItem(worldObj, pos, blockState, 0);
                            worldObj.setBlockToAir(pos);
                        }
                    }
                }
    }

    public void genList() {
        blockList.clear();
        List<ItemStack> list = OreDictionary.getOres("cobblestone");
        if (list != null && !list.isEmpty()) {
            for (ItemStack stack : list) {
                blockList.add(Block.getBlockFromItem(stack.getItem()));
            }
        }
        list = OreDictionary.getOres("stone");
        if (list != null && !list.isEmpty()) {
            for (ItemStack stack : list) {
                blockList.add(Block.getBlockFromItem(stack.getItem()));
            }
        }
        blockList.add(Blocks.mossy_cobblestone);
        blockList.add(Blocks.stone_brick_stairs);
        blockList.add(Blocks.stone_button);
        blockList.add(Blocks.stone_pressure_plate);
        blockList.add(Blocks.stone_stairs);
        blockList.add(Blocks.stonebrick);
        blockList.add(Blocks.stone_slab);
        blockList.add(Blocks.cobblestone_wall);
        blockList.add(Blocks.double_stone_slab);
    }
}
