package net.lomeli.ec;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.ChunkEvent;

import net.lomeli.ec.core.CommonProxy;
import net.lomeli.ec.core.Config;
import net.lomeli.ec.core.EntityRegistering;
import net.lomeli.ec.core.MorphAddon;
import net.lomeli.ec.entity.EntityFriendlyCreeper;
import net.lomeli.ec.entity.EntityGhostCreeper;
import net.lomeli.ec.entity.EntitySolarCreeper;
import net.lomeli.ec.entity.IIllusion;
import net.lomeli.ec.lib.ECVars;
import net.lomeli.ec.lib.Strings;

import net.lomeli.lomlib.util.ModLoaded;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Strings.MOD_ID, name = Strings.MOD_NAME, version = Strings.VERSION, dependencies = "required-after:LomLib;")
public class ElementalCreepers {

    @SidedProxy(clientSide = Strings.CLIENT, serverSide = Strings.COMMON)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.loadConfig(event.getSuggestedConfigurationFile());
    }

    @Mod.EventHandler
    public void Init(FMLInitializationEvent event) {
        proxy.registerRenders();

        MinecraftForge.EVENT_BUS.register(this);

        EntityRegistering.loadEntities();
    }

    @Mod.EventHandler
    public void postLoad(FMLPostInitializationEvent event) {
        if (ModLoaded.isModInstalled("Morph"))
            MorphAddon.loadAddon();
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event) {
        boolean activate = false;

        if (event.source.getDamageType().equals("player"))
            activate = true;

        if (event.source.getSourceOfDamage() instanceof EntityArrow) {
            if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity != null) {
                if (((EntityArrow) event.source.getSourceOfDamage()).shootingEntity instanceof EntityPlayer)
                    activate = true;
            }
        }

        if (activate && event.entityLiving != null && (event.entityLiving instanceof EntityCreeper) && !(event.entityLiving instanceof EntityGhostCreeper)
                && !(event.entityLiving instanceof EntityFriendlyCreeper)) {
            if (event.entityLiving instanceof IIllusion) {
                if (((IIllusion) event.entityLiving).isIllusion())
                    return;
            }

            if (event.entityLiving.worldObj.rand.nextInt(100) < ECVars.ghostCreeperChance) {
                EntityGhostCreeper ghost = new EntityGhostCreeper(event.entityLiving.worldObj);
                ghost.setLocationAndAngles(event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, event.entityLiving.rotationYaw, event.entityLiving.rotationPitch);
                event.entityLiving.worldObj.spawnEntityInWorld(ghost);
            }
        }
    }

    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
        if (event.world.isDaytime() && event.world.rand.nextInt(1000) <= 1) {
            EntitySolarCreeper creeper = new EntitySolarCreeper(event.world);
            int x = event.getChunk().xPosition + event.world.rand.nextInt(15);
            int z = event.getChunk().zPosition + event.world.rand.nextInt(15);
            for (int y = event.world.getActualHeight(); y > 0; y++) {
                Block block = event.world.getBlock(x, y, z);
                if (block != null) {
                    Block bk = event.world.getBlock(x, y + 1, z);
                    Block jk = event.world.getBlock(x, y + 2, z);
                    if (bk == null && jk == null) {
                        creeper.setLocationAndAngles(x, y, z, 0, 0);
                        break;
                    }
                }
            }
            event.world.spawnEntityInWorld(creeper);
        }
    }
}
