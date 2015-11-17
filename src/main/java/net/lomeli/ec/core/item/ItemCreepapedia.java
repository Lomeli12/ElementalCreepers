package net.lomeli.ec.core.item;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.*;
import net.minecraft.world.World;

import net.lomeli.lomlib.util.LangUtil;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.client.CreeperEntry;

public class ItemCreepapedia extends Item {
    public ItemCreepapedia() {
        super();
        this.setUnlocalizedName("elementalcreepers.creepapedia");
        this.setCreativeTab(CreativeTabs.tabMisc);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote && stack != null) {
            if (!stack.hasTagCompound())
                stack.setTagCompound(new NBTTagCompound());
            NBTTagCompound tag = stack.getTagCompound();
            if (!tag.getBoolean("ghostClear")) {
                IBlockState state = world.getBlockState(pos);
                if (state != null && state.getBlock() == Blocks.enchanting_table) {
                    tag.setBoolean("ghostClear", true);
                    stack.setTagCompound(tag);
                    player.addChatComponentMessage(new ChatComponentText(LangUtil.translate("item.elementalcreepers.creepapedia.enchant")));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        tooltip.add(LangUtil.translate("item.elementalcreepers.creepapediasub"));
        if (stack.hasTagCompound()) {
            if (stack.getTagCompound().getBoolean("ghostClear"))
                tooltip.add(LangUtil.translate("item.elementalcreepers.creepapedia.enchantsub"));
            if (stack.getTagCompound().hasKey("fullList") && stack.getTagCompound().getBoolean("fullList"))
                tooltip.add(LangUtil.translate("item.elementalcreepers.creepapedia.creative"));
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
        subItems.add(new ItemStack(itemIn));
        ItemStack stack = new ItemStack(itemIn);
        NBTTagCompound tag = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (CreeperEntry entry : CreeperEntry.entryList) {
            list.appendTag(new NBTTagString(entry.getEntityClass().getCanonicalName()));
        }
        tag.setTag("creepers", list);
        tag.setBoolean("fullList", true);
        tag.setBoolean("ghostClear", true);
        stack.setTagCompound(tag);
        subItems.add(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.isSneaking())
            player.openGui(ElementalCreepers.instance, 0, world, 0, 0, 0);
        return stack;
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("ghostClear");
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().getBoolean("fullList") ? EnumRarity.RARE : super.getRarity(stack);
    }
}
