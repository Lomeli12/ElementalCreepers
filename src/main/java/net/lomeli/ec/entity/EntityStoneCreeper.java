package net.lomeli.ec.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import net.minecraftforge.oredict.OreDictionary;

import net.lomeli.ec.lib.ECVars;

public class EntityStoneCreeper extends EntityBaseCreeper {
    private List<Block> blockList = new ArrayList<Block>();

    public EntityStoneCreeper(World par1World) {
        super(par1World);
        this.explosionRadius = ECVars.stoneCreeperRadius;
        for (ItemStack stack : OreDictionary.getOres("cobblestone")) {
            blockList.add(Block.getBlockFromItem(stack.getItem()));
        }
        for (ItemStack stack : OreDictionary.getOres("stone")) {
            blockList.add(Block.getBlockFromItem(stack.getItem()));
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

    @Override
    public void explosion(int power, boolean flag) {
        int radius = getPowered() ? (int) (this.explosionRadius * power) : this.explosionRadius;
        for (int x = -radius; x <= radius; x++)
            for (int y = -radius; y <= radius; y++)
                for (int z = -radius; z <= radius; z++) {
                    Block bk = worldObj.getBlock((int) posX + x, (int) posY + y, (int) posZ + z);
                    int meta = this.worldObj.getBlockMetadata((int) posX + x, (int) posY + y, (int) posZ + z);
                    if (bk != null && this.blockList.contains(bk) && Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2)) <= radius) {
                        bk.dropBlockAsItem(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, meta, 0);
                        worldObj.setBlockToAir((int) posX + x, (int) posY + y, (int) posZ + z);
                        bk.onBlockDestroyedByExplosion(worldObj, (int) posX + x, (int) posY + y, (int) posZ + z, new Explosion(worldObj, this, 0.0D, 0.0D, 0.0D, 0.0F));
                    }
                }
    }

}
