package net.lomeli.ec.core;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.lomeli.ec.entity.EntitySilverCreeper;
import net.lomeli.ec.lib.Strings;

public class BlockSilverCreeper extends Block {
    public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockSilverCreeper.EnumType.class);

    public BlockSilverCreeper() {
        super(Material.clay);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockSilverCreeper.EnumType.STONE));
        this.setHardness(0.0F);
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHardness(0.75F);
        this.setUnlocalizedName(Strings.MOD_ID.toLowerCase() + ".silverCreep");
    }

    @Override
    public int quantityDropped(Random random) {
        return 0;
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state) {
        switch (BlockSilverCreeper.SwitchEnumType.TYPE_LOOKUP[((BlockSilverCreeper.EnumType) state.getValue(VARIANT)).ordinal()]) {
            case 1:
                return new ItemStack(Blocks.cobblestone);
            case 2:
                return new ItemStack(Blocks.stonebrick);
            case 3:
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.MOSSY.getMetadata());
            case 4:
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CRACKED.getMetadata());
            case 5:
                return new ItemStack(Blocks.stonebrick, 1, BlockStoneBrick.EnumType.CHISELED.getMetadata());
            default:
                return new ItemStack(Blocks.stone);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if (!worldIn.isRemote && worldIn.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            EntitySilverCreeper creeper = new EntitySilverCreeper(worldIn);
            creeper.setLocationAndAngles((double) pos.getX() + 0.5D, (double) pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
            worldIn.spawnEntityInWorld(creeper);
            creeper.spawnExplosionParticle();
        }
    }

    @Override
    public int getDamageValue(World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        return iblockstate.getBlock().getMetaFromState(iblockstate);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        BlockSilverCreeper.EnumType[] aenumtype = BlockSilverCreeper.EnumType.values();
        int i = aenumtype.length;

        for (int j = 0; j < i; ++j) {
            BlockSilverCreeper.EnumType enumtype = aenumtype[j];
            list.add(new ItemStack(itemIn, 1, enumtype.getMetadata()));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, BlockSilverCreeper.EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((BlockSilverCreeper.EnumType) state.getValue(VARIANT)).getMetadata();
    }

    @Override
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[]{VARIANT});
    }

    public static enum EnumType implements IStringSerializable {
        STONE(0, "stone", (BlockSilverCreeper.SwitchEnumType) null) {
            public IBlockState getModelBlock() {
                return Blocks.stone.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.STONE);
            }
        },
        COBBLESTONE(1, "cobblestone", "cobble", (BlockSilverCreeper.SwitchEnumType) null) {
            public IBlockState getModelBlock() {
                return Blocks.cobblestone.getDefaultState();
            }
        },
        STONEBRICK(2, "stone_brick", "brick", (BlockSilverCreeper.SwitchEnumType) null) {
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT);
            }
        },
        MOSSY_STONEBRICK(3, "mossy_brick", "mossybrick", (BlockSilverCreeper.SwitchEnumType) null) {
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.MOSSY);
            }
        },
        CRACKED_STONEBRICK(4, "cracked_brick", "crackedbrick", (BlockSilverCreeper.SwitchEnumType) null) {
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED);
            }
        },
        CHISELED_STONEBRICK(5, "chiseled_brick", "chiseledbrick", (BlockSilverCreeper.SwitchEnumType) null) {
            public IBlockState getModelBlock() {
                return Blocks.stonebrick.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED);
            }
        };
        private static final EnumType[] META_LOOKUP = new EnumType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private EnumType(int meta, String name) {
            this(meta, name, name);
        }

        private EnumType(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata() {
            return this.meta;
        }

        public String toString() {
            return this.name;
        }

        public static BlockSilverCreeper.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length)
                meta = 0;

            return META_LOOKUP[meta];
        }

        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        public abstract IBlockState getModelBlock();

        public static BlockSilverCreeper.EnumType forModelBlock(IBlockState model) {
            BlockSilverCreeper.EnumType[] aenumtype = values();
            int i = aenumtype.length;

            for (int j = 0; j < i; ++j) {
                BlockSilverCreeper.EnumType enumtype = aenumtype[j];

                if (model == enumtype.getModelBlock()) {
                    return enumtype;
                }
            }

            return STONE;
        }

        EnumType(int meta, String name, BlockSilverCreeper.SwitchEnumType dummy) {
            this(meta, name);
        }

        EnumType(int meta, String name, String unlocalizedName, BlockSilverCreeper.SwitchEnumType dummy) {
            this(meta, name, unlocalizedName);
        }

        static {
            BlockSilverCreeper.EnumType[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                BlockSilverCreeper.EnumType var3 = var0[var2];
                META_LOOKUP[var3.getMetadata()] = var3;
            }
        }
    }

    static final class SwitchEnumType {
        static final int[] TYPE_LOOKUP = new int[BlockSilverCreeper.EnumType.values().length];

        static {
            try {
                TYPE_LOOKUP[BlockSilverCreeper.EnumType.COBBLESTONE.ordinal()] = 1;
            } catch (NoSuchFieldError var5) {
            }

            try {
                TYPE_LOOKUP[BlockSilverCreeper.EnumType.STONEBRICK.ordinal()] = 2;
            } catch (NoSuchFieldError var4) {
            }

            try {
                TYPE_LOOKUP[BlockSilverCreeper.EnumType.MOSSY_STONEBRICK.ordinal()] = 3;
            } catch (NoSuchFieldError var3) {
            }

            try {
                TYPE_LOOKUP[BlockSilverCreeper.EnumType.CRACKED_STONEBRICK.ordinal()] = 4;
            } catch (NoSuchFieldError var2) {
            }

            try {
                TYPE_LOOKUP[BlockSilverCreeper.EnumType.CHISELED_STONEBRICK.ordinal()] = 5;
            } catch (NoSuchFieldError var1) {
            }
        }
    }
}
