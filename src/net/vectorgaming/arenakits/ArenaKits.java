package net.vectorgaming.arenakits;

import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Kenny
 */
public class ArenaKits extends JavaPlugin
{
    private KitAPI api; 
    
    public void onEnable()
    {
        api = new KitAPI(this);
        KitManager.loadAllKits();
    }
    
    public void onDisable()
    {
        KitManager.saveAllKits();
    }
}
