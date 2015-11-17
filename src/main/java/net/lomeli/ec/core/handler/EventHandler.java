package net.lomeli.ec.core.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.lomeli.lomlib.util.LangUtil;
import net.lomeli.lomlib.util.entity.EntityUtil;

import net.lomeli.ec.ElementalCreepers;
import net.lomeli.ec.client.CreeperEntry;
import net.lomeli.ec.entity.*;
import net.lomeli.ec.lib.ModVars;

public class EventHandler {
    @SubscribeEvent
    public void entityHurt(LivingHurtEvent event) {
        EntityLivingBase entity = event.entityLiving;
        DamageSource source = event.source;
        if (entity != null && source != null) {
            if (entity instanceof EntitySpringCreeper) {
                EntitySpringCreeper creeper = (EntitySpringCreeper) entity;
                if (source == DamageSource.fall && creeper.isSprung() && !creeper.worldObj.isRemote) {
                    creeper.worldObj.createExplosion(creeper, creeper.posX, creeper.posY - 2, creeper.posZ, creeper.getExplosionRadius() * ((event.ammount < 6 ? 6 : event.ammount) / 6), creeper.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing"));
                    creeper.setDead();
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        boolean activate = EntityUtil.damageFromPlayer(event.source);
        Entity source = EntityUtil.getSourceOfDamage(event.source);

        if (activate && event.entityLiving != null && (event.entityLiving instanceof EntityCreeper || event.entityLiving instanceof EntityFriendlyCreeper || event.entityLiving instanceof EntityBigBadCreep)) {
            if (source instanceof EntityPlayer && registerCreep((EntityPlayer) source, event.entityLiving.getClass()))
                ((EntityPlayer) source).addChatComponentMessage(new ChatComponentText(String.format(EnumChatFormatting.GREEN + StatCollector.translateToLocal("book.entry.newEntry"), event.entityLiving.getCommandSenderName())));

            if (!(event.entityLiving instanceof EntityGhostCreeper) && !(event.entityLiving instanceof EntityFriendlyCreeper)) {
                if (event.entityLiving instanceof IIllusion) {
                    if (((IIllusion) event.entityLiving).isIllusion())
                        return;
                }

                if (event.entityLiving.worldObj.rand.nextInt(100) < ModVars.ghostCreeperChance) {
                    EntityGhostCreeper ghost = new EntityGhostCreeper(event.entityLiving.worldObj);
                    ghost.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving.rotationYaw, event.entityLiving.rotationPitch);
                    event.entityLiving.worldObj.spawnEntityInWorld(ghost);
                }
            }
        }
    }

    private boolean registerCreep(EntityPlayer player, Class<? extends Entity> creep) {
        ItemStack stack = null;
        int slot = -1;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            ItemStack item = player.inventory.getStackInSlot(i);
            if (item != null && item.getItem() == ElementalCreepers.creepapedia) {
                stack = item;
                slot = i;
                break;
            }
        }
        if (stack != null) {
            NBTTagCompound tag = new NBTTagCompound();
            if (stack.hasTagCompound())
                tag = stack.getTagCompound();
            NBTTagList list = new NBTTagList();
            boolean flag = true;
            if (tag.hasKey("creepers", 9)) {
                list = tag.getTagList("creepers", 8);
                for (int i = 0; i < list.tagCount(); i++) {
                    String clazzString = ((NBTTagString) list.get(i)).getString();
                    if (clazzString.equals(creep.getCanonicalName())) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag)
                list.appendTag(new NBTTagString(creep.getCanonicalName()));
            tag.setTag("creepers", list);
            stack.setTagCompound(tag);
            player.inventory.setInventorySlotContents(slot, stack);
            if (!tag.getBoolean("complete") && list.tagCount() >= CreeperEntry.entryList.size()) {
                tag.setBoolean("complete", true);
                player.addChatComponentMessage(new ChatComponentText(LangUtil.translate("book.entry.allentries")));
            }
            return flag;
        }
        return false;
    }
}
