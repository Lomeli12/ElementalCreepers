package net.lomeli.ec.core.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

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
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        tooltip.add(EnumChatFormatting.GREEN + StatCollector.translateToLocal("item.elementalcreepers.creepapediasub") + EnumChatFormatting.RESET);
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("fullList") && stack.getTagCompound().getBoolean("fullList"))
            tooltip.add(EnumChatFormatting.DARK_PURPLE + StatCollector.translateToLocal("item.elementalcreepers.creepapedia.creative") + EnumChatFormatting.RESET);
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
        stack.setTagCompound(tag);
        subItems.add(stack);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.isSneaking())
            player.openGui(ElementalCreepers.instance, 0, world, 0, 0, 0);
        return stack;
    }
}
