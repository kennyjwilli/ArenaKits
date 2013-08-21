
package net.vectorgaming.arenakits;

/**
 *
 * @author Kenny
 */
public class KitAPI 
{
    private static ArenaKits plugin;
    
    public KitAPI(ArenaKits instance)
    {
        plugin = instance;
    }

    public static ArenaKits getPlugin() {return plugin;}
    
}
