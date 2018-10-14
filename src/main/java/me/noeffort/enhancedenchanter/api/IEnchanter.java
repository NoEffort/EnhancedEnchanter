package me.noeffort.enhancedenchanter.api;

import java.util.HashSet;
import java.util.Map.Entry;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.noeffort.enhancedenchanter.util.EnchantmentNameUtil;
import me.noeffort.enhancedenchanter.util.handler.ItemHandler;
import me.noeffort.enhancedenchanter.util.handler.ProjectileHandler;

public class IEnchanter {

	public IEnchanter() {}
	
	private static String enchantmentName = "";
	private static int enchantmentId = 0;
	
	public static Enchantment getEnchantmentByName(String enchantment) {
		enchantmentName = enchantment;
		return Enchantment.getByName(enchantmentName);
	}
	
	@Deprecated
	public static Enchantment getEnchantmentById(int id) {
		enchantmentId = id;
		return Enchantment.getById(enchantmentId);
	}
	
	public static String getNameByEnchantment(Enchantment enchantment) {
		enchantmentName = enchantment.getName();
		return enchantmentName;
	}
	
	@Deprecated
	public static int getIdByEnchantment(Enchantment enchantment) {
		enchantmentId = enchantment.getId();
		return enchantmentId;
	}
	
	public static void enchantItem(ItemStack item, Enchantment enchantment, Player player, int level) {
		ItemHandler.addEnchantment(item, enchantment, player, level);
	}
	
	public static String[] getAppliedEnchantments(ItemStack item) {
		
		HashSet<String> enchantmentNames = new HashSet<String>();
		String[] array = {""};
		
		String[] notFoundMeta = {"Meta Not Found!"};
		String[] notFoundEnchantment = {"Enchantments Not Found!"};
		
		if(item.hasItemMeta()) {
			
			ItemMeta meta = item.getItemMeta();
			if(meta.hasEnchants()) {
		
				EnchantmentNameUtil name = new EnchantmentNameUtil();
				
				for(Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
					enchantmentNames.add(name.setDisplayName(entry.getKey().getName()));
				}
				
				array = enchantmentNames.toArray(new String[enchantmentNames.size()]);
				
			} else {
				return notFoundEnchantment;
			}
		} else {
			return notFoundMeta;
		}
		return array;
	}
	
	public static ItemStack getEnchantedItem() {
		ItemStack stack = ItemHandler.getItem();
		return stack;
	}
	
	public static Projectile getProjectile() {
		Projectile projectile = ProjectileHandler.getProjectile();
		return projectile;
	}
	
	public static String getDisplayName(Enchantment enchantment) {
		EnchantmentNameUtil name = new EnchantmentNameUtil();
		String displayName = name.setDisplayName(enchantment.getName());
		return displayName;
	}
	
}
