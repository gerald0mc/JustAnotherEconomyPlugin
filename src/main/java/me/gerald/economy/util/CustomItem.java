package me.gerald.economy.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomItem {
    public Material material;
    public String displayName;
    public String[] lore;

    public ItemStack itemStack;
    public ItemMeta itemMeta;

    public CustomItem(Material material, String displayName, String[] lore) {
        this.material = material;
        this.displayName = displayName;
        this.lore = lore;

        this.itemStack = new ItemStack(material, 1);
        this.itemMeta = itemStack.getItemMeta();
        this.itemMeta.setDisplayName(displayName);
        this.itemMeta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItem() {
        return itemStack;
    }

    public ItemMeta getMeta() {
        return itemMeta;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String[] getLore() {
        return lore;
    }
}
