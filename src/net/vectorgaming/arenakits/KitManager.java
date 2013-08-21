
package net.vectorgaming.arenakits;

import info.jeppes.ZoneCore.ZoneConfig;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.arcanerealm.arcanelib.ItemSLAPI;
import net.vectorgaming.arenakits.exceptions.KitFormatException;
import net.vectorgaming.arenakits.framework.Kit;
import net.vectorgaming.arenakits.framework.enums.KitDirectory;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Kenny
 */
public class KitManager 
{
    private static ArenaKits plugin = KitAPI.getPlugin();
    
    private static HashMap<String,Kit> kits = new HashMap<>();
    
    /**
     * Gets a kit from the specified name
     * @param name Name of the kit
     * @return Kit object
     */
    public static Kit getKit(String name) {return kits.get(name);}
    
    /**
     * Adds a kit to the plugin
     * @param name Name of the kit
     * @param kit Kit object
     */
    public static void addKit(String name, Kit kit) {kits.put(name, kit);}
    
    /**
     * Removed a kit from the plugin
     * @param name Name of the kit
     */
    public static void removeKit(String name) {kits.remove(name.toLowerCase());}
    
    /**
     * Checks to see if the given kit exists
     * @param name Name of the kit
     * @return boolean value
     */
    public static boolean kitExists(String name) {return kits.containsKey(name.toLowerCase());}
        
    /**
     * Checks to see if the given kit exists
     * @param kit Kit object
     * @return boolean value
     */
    public static boolean kitExists(Kit kit) {return kitExists(kit.getName());}
    
    /**
     * Gets a list of all the kit names
     * @return A list of kit names
     */
    public static Set<String> getKitNames() {return kits.keySet();}
    
        /**
     * Saves the given kit name
     * 
     * NOTE: Does save a kit config file
     * @param name Name of the kit
     */
    public static void saveKit(String name)
    {
        Kit kit = getKit(name);
        ZoneConfig config = new ZoneConfig(KitAPI.getPlugin(), new File(KitDirectory.KITS+File.separator+name+".yml"));
        config.set("name", name);
        config.set("armor.helmet", ItemSLAPI.itemStackToSaveString(kit.getHelmet()));
        config.set("armor.chestplate", ItemSLAPI.itemStackToSaveString(kit.getChestplate()));
        config.set("armor.leggings", ItemSLAPI.itemStackToSaveString(kit.getLeggings()));
        config.set("armor.boots", ItemSLAPI.itemStackToSaveString(kit.getBoots()));
        
        List<String> inventory = new ArrayList<>();
        for(Map.Entry kv : kit.getInventoryContents().entrySet())
        {
            if(kv.getValue() != null)
                inventory.add("SLOT:"+(int)kv.getKey()+";"+ItemSLAPI.itemStackToSaveString( (ItemStack) kv.getValue()) );
        }
        
        config.set("inventory", inventory);
        config.save();
        
        //Adds to enabled kits in config.yml
        List<String> kitsList;
        if(!plugin.getConfig().contains("kits"))
        {
            kitsList = new ArrayList<>();
        }else
        {
            kitsList = plugin.getConfig().getStringList("kits");
        }
        if(!kitsList.contains(name))
            kitsList.add(name);
        plugin.getConfig().set("kits", kitsList);
        plugin.saveConfig(); 
    }
    
    /**
     * Loads a kit through the given name
     * @param name Name of the kit
     */
    public static void loadKit(String name) throws Exception
    {
        try
        {
            ZoneConfig config = new ZoneConfig(KitAPI.getPlugin(), new File(KitDirectory.KITS+File.separator+name+".yml"));
            Kit kit = new Kit(name);
            kit.setHelmet(ItemSLAPI.getItemStackFromSave(config.getString("armor.helmet")));
            kit.setChestplate(ItemSLAPI.getItemStackFromSave(config.getString("armor.chestplate")));
            kit.setLeggings(ItemSLAPI.getItemStackFromSave(config.getString("armor.leggings")));
            kit.setBoots(ItemSLAPI.getItemStackFromSave(config.getString("armor.boots")));
            for(String s : config.getStringList("inventory"))
            {
                String[] split = s.split(";");
                int slotNumber = Integer.parseInt(split[0].split(":")[1]);
                System.out.println(split[1]);
                kit.addInventoryItem(slotNumber, ItemSLAPI.getItemStackFromSave(split[1]));
            }
            kits.put(name, kit);
        } catch (KitFormatException ex)
        {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Loads all the kits activated in the config.yml
     */
    public static void loadAllKits()
    {
        plugin.reloadConfig();
        if(!plugin.getConfig().contains("kits"))
            return;
        
        try
        {
            for(String s : plugin.getConfig().getStringList("kits"))
                loadKit(s);
        } catch (Exception ex)
        {
            Logger.getLogger(KitManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Saves all the kits and enabled them in the config.yml 
     */
    public static void saveAllKits()
    {
        for(String s : kits.keySet())
            saveKit(s);
    }
    
    /**
     * Checks to see if the kit is saved to the config
     * @param name Name of the kit
     * @return boolean value
     */
    public static boolean isSavedToConfig(String name)
    {
        if(!plugin.getConfig().contains("kits"))
            return false;
        return plugin.getConfig().getStringList("kits").contains(name);
    }
    
    
}
