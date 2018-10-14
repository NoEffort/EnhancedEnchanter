package me.noeffort.enhancedenchanter.util;

import org.bukkit.enchantments.Enchantment;

import me.noeffort.enhancedenchanter.util.enchantment.EnchantmentListener;

public class EnchantmentNameUtil {
	
	private static String displayName;
	
	public String setDisplayName(String enchantment) {
		return getDisplayName(enchantment);
	}
	
	private static String getDisplayName(String enchantment) {
		
		Enchantment[] enchantments = EnchantmentListener.listEnchantments();
		
		for(Enchantment enchant : enchantments) {
			
			enchantment.equalsIgnoreCase(enchant.getName());
			
			switch(enchantment) {
			case "ONE_HIT":
				displayName = "One Hit";
				break;
			case "EXPLOSIVE":
				displayName = "Explosive";
				break;
			case "GLOW":
				displayName = "Glow";
				break;
			case "NUKE":
				displayName = "Nuke";
				break;
			case "SMITE":
				displayName = "Smite";
				break;
			case "RETURN_FIRE":
				displayName = "Return Fire";
				break;
			case "LIGHTWEIGHT":
				displayName = "Lightweight";
				break;
			case "ABSORBTION":
				displayName = "Absorbtion";
				break;
			case "THICK_SKIN":
				displayName = "Thick Skin";
				break;
			case "MIDAS_TOUCH":
				displayName = "Midas Touch";
				break;
			case "HASTE":
				displayName = "Haste";
				break;
			case "SUMMONER":
				displayName = "Summoner";
				break;
			default:
				break;
			}
		}
		return displayName;
	}
}
