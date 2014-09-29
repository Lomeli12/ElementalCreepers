package net.lomeli.ec.core;

import com.google.gson.stream.JsonReader;
import org.apache.logging.log4j.Level;

import java.io.InputStreamReader;
import java.net.URL;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.StatCollector;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.SideOnly;

import net.lomeli.ec.lib.Strings;

import static cpw.mods.fml.relauncher.Side.CLIENT;

public class VersionChecker {
    private static boolean needsUpdate, isDirect, doneTelling;
    private static String updateJson = "https://raw.githubusercontent.com/Lomeli12/ElementalCreepers-4/master/update.json";
    private static String version, downloadURL, changeLog;

    public static void checkForUpdates() {
        needsUpdate = false;
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(new URL(updateJson).openStream()));

            if (reader != null) {
                int major = 0, minor = 0, revision = 0;
                reader.beginObject();
                while (reader.hasNext()) {
                    String name = reader.nextName();
                    if (name.equals("major"))
                        major = reader.nextInt();
                    else if (name.equals("minor"))
                        minor = reader.nextInt();
                    else if (name.equals("revision"))
                        revision = reader.nextInt();
                    else if (name.equals("downloadURL"))
                        downloadURL = reader.nextString();
                    else if (name.equals("direct"))
                        isDirect = reader.nextBoolean();
                    else if (name.equals("changeLog"))
                        changeLog = reader.nextString();
                }
                reader.endObject();
                if (major > Strings.MAJOR)
                    needsUpdate = true;
                else if (major == Strings.MAJOR) {
                    if (minor > Strings.MINOR)
                        needsUpdate = true;
                    else if (minor == Strings.MINOR) {
                        if (revision > Strings.REVISION)
                            needsUpdate = true;
                    }
                }

                if (needsUpdate) {
                    version = major + "." + minor + "." + revision;
                    FMLLog.log(Level.INFO, translate(Strings.UPDATE_MESSAGE));
                    FMLLog.log(Level.INFO, translate(Strings.OLD_VERSION) + " " + Strings.VERSION);
                    FMLLog.log(Level.INFO, translate(Strings.NEW_VERSION) + " " + version);
                }
                reader.close();
            }
        } catch (Exception e) {
        }
    }

    public static void sendMessage() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setString("modDisplayName", Strings.MOD_NAME);
        tag.setString("oldVersion", Strings.VERSION);
        tag.setString("newVersion", version);
        tag.setString("updateUrl", downloadURL);
        tag.setBoolean("isDirectLink", isDirect);
        tag.setString("changeLog", changeLog);
        FMLInterModComms.sendMessage("VersionChecker", "addUpdate", tag);
    }

    public static boolean needUpdate() {
        return needsUpdate;
    }

    public static String translate(String unlocalized) {
        return StatCollector.translateToLocal(unlocalized);
    }

    @SubscribeEvent
    @SideOnly(CLIENT)
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (needUpdate() && event.phase == TickEvent.Phase.END && Minecraft.getMinecraft().thePlayer != null && !doneTelling) {
            EntityPlayer player = Minecraft.getMinecraft().thePlayer;
            if (!version.isEmpty() && player != null) {
                player.addChatMessage(new ChatComponentTranslation(Strings.UPDATE_MESSAGE));
                player.addChatMessage(new ChatComponentText(translate(Strings.OLD_VERSION) + " " + Strings.VERSION));
                player.addChatMessage(new ChatComponentText(translate(Strings.NEW_VERSION) + " " + version));
                doneTelling = true;
            }
        }
    }
}
