
package net.vectorgaming.arenakits.framework.enums;

import java.io.File;
import net.vectorgaming.arenakits.ArenaKits;
import net.vectorgaming.arenakits.KitAPI;

/**
 *
 * @author Kenny
 */
public class KitDirectory 
{
    private static final ArenaKits plugin = KitAPI.getPlugin();
    
    public static final String KITS = plugin.getDataFolder()+File.separator+"kits";
}
