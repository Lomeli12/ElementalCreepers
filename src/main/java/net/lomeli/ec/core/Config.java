package net.lomeli.ec.core;

import java.io.File;

import net.lomeli.ec.lib.ECVars;

import net.lomeli.lomlib.util.XMLConfiguration;
import net.lomeli.lomlib.util.XMLConfiguration.ConfigEnum;

public class Config {
    public static XMLConfiguration config;
    
    public static void loadConfig(File configFile){
        config = new XMLConfiguration(configFile);
        
        config.loadXml();
        
        loadID();
        
        config.saveXML();
    }
    
    private static void loadID(){
        ECVars.fireCreeperID = setGetInt("fireCreeperID", 120);
        ECVars.waterCreeperID = setGetInt("waterCreeperID", 121);
        ECVars.electricCreeperID = setGetInt("electricCreeperID", 122);
        ECVars.cookieCreeperID = setGetInt("cookieCreeperID", 123);
        ECVars.darkCreeperID = setGetInt("darkCreeperID", 124);
        ECVars.lightCreeperID = setGetInt("lightCreeperID", 125);
        ECVars.earthCreeperID = setGetInt("earthCreeperID", 126);
        ECVars.magmaCreeperID = setGetInt("magmaCreeperID", 127);
        ECVars.reverseCreeperID = setGetInt("reverseCreeperID", 128);
    }
    
    private static int setGetInt(String tag, int id){
        return config.getInt(tag, id, ConfigEnum.GENERAL_CONFIG);
    }
}
