
package net.vectorgaming.arenakits.framework;

import java.util.Map;
import java.util.TreeMap;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Kenny
 */
public class Kit 
{
    private String name;
    private ItemStack helmet;
    private ItemStack chestplate;
    private ItemStack leggings;
    private ItemStack boots;
    private TreeMap<Integer, ItemStack> inventory = new TreeMap<>();
    
    public Kit(String name) {this.name = name;}
    
    public Kit(String name, ItemStack[] armor, TreeMap<Integer, ItemStack> inventory)
    {
        setArmorContents(armor);
        this.inventory = inventory;
    }
        
    public String getName() {return name;}
    
    public ItemStack[] getArmorContents(){return new ItemStack[]{boots, leggings, chestplate, helmet};}
    
    public final void setArmorContents(ItemStack[] items) 
    {
        helmet = items[3];
        chestplate = items[2];
        leggings = items[1];
        boots = items[0];
    }
    
    public ItemStack getHelmet() {return helmet;}
    
    public void setHelmet(ItemStack item) {helmet = item;}
    
    public ItemStack getChestplate() {return chestplate;}
    
    public void setChestplate(ItemStack item) {chestplate = item;}
    
    public ItemStack getLeggings() {return leggings;}
    
    public void setLeggings(ItemStack item) {leggings = item;}
    
    public ItemStack getBoots() {return boots;}
    
    public void setBoots(ItemStack item) {boots = item;}
    
    public TreeMap<Integer, ItemStack> getInventoryContents() {return inventory;}
    
    public void setInventoryContents(TreeMap<Integer, ItemStack> items) {this.inventory = items;}
    
    public void setInventoryContents(ItemStack[] items) 
    {
        inventory.clear();
        int i = 0;
        for(ItemStack item : items)
        {
            inventory.put(i, item);
            i++;
        }
    }
    
    public void addInventoryItem(Integer slot, ItemStack item)
    {
        inventory.put(slot, item);
    }
    
    public void giveKit(Player p, boolean clearInventory)
    {
        if(clearInventory)
        {
            p.getInventory().clear();
            p.getInventory().setArmorContents(null);
        }
        p.getInventory().setArmorContents(getArmorContents());
        for(Map.Entry kv : inventory.entrySet())
            p.getInventory().setItem((Integer)kv.getKey(), (ItemStack)kv.getValue());
    }
    
}
