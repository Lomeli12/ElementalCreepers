package net.lomeli.ec.client.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.fml.common.network.IGuiHandler;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.client.CreeperEntry;
import net.lomeli.ec.client.gui.GuiItemList;

public class ECGuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        ItemStack stack = player.getCurrentEquippedItem();
        if (stack != null && stack.getItem() != null && stack.getItem() == ElementalCreepers.creepapedia)
            return new GuiItemList(CreeperEntry.generatePages(stack.getTagCompound()));
        return null;
    }
}
