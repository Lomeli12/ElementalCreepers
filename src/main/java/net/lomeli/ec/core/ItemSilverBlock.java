package net.lomeli.ec.core;

import com.google.common.base.Function;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

public class ItemSilverBlock extends ItemMultiTexture {
    public ItemSilverBlock(Block block) {
        super(block, block, new Function() {
            @Nullable
            @Override
            public Object apply(Object input) {
                return this.apply((ItemStack) input);
            }

            public String apply(ItemStack stack) {
                return BlockSilverCreeper.EnumType.byMetadata(stack.getMetadata()).getUnlocalizedName();
            }
        });
    }
}
