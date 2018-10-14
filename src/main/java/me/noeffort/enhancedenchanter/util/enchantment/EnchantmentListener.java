package me.noeffort.enhancedenchanter.util.enchantment;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.earth2me.essentials.Enchantments;
import com.earth2me.essentials.Essentials;

import me.noeffort.enhancedenchanter.Main;
import me.noeffort.enhancedenchanter.enchantment.AbsorbtionEnchant;
import me.noeffort.enhancedenchanter.enchantment.ExplosiveEnchant;
import me.noeffort.enhancedenchanter.enchantment.GlowEffectEnchant;
import me.noeffort.enhancedenchanter.enchantment.GlowEnchant;
import me.noeffort.enhancedenchanter.enchantment.HasteEnchant;
import me.noeffort.enhancedenchanter.enchantment.LightweightEnchant;
import me.noeffort.enhancedenchanter.enchantment.MidasTouchEnchant;
import me.noeffort.enhancedenchanter.enchantment.NukeEnchant;
import me.noeffort.enhancedenchanter.enchantment.OneHitEnchant;
import me.noeffort.enhancedenchanter.enchantment.ReturnFireEnchant;
import me.noeffort.enhancedenchanter.enchantment.SmiteEnchant;
import me.noeffort.enhancedenchanter.enchantment.SummonerEnchant;
import me.noeffort.enhancedenchanter.enchantment.ThickSkinEnchant;

public class EnchantmentListener implements Listener {
	
	Main plugin;
	
	static GlowEffectEnchant glowEffect = new GlowEffectEnchant(100);
	static OneHitEnchant onehit = new OneHitEnchant(101);
	static ExplosiveEnchant explosion = new ExplosiveEnchant(102);
	static GlowEnchant glow = new GlowEnchant(103);
	static NukeEnchant nuke = new NukeEnchant(104);
	static SmiteEnchant smite = new SmiteEnchant(105);
	static ReturnFireEnchant returnFire = new ReturnFireEnchant(106);
	static LightweightEnchant light = new LightweightEnchant(107);
	static AbsorbtionEnchant absorbtion = new AbsorbtionEnchant(108);
	static ThickSkinEnchant thickSkin = new ThickSkinEnchant(109);
	static MidasTouchEnchant midasTouch = new MidasTouchEnchant(110);
	static HasteEnchant haste = new HasteEnchant(111);
	static SummonerEnchant summoner = new SummonerEnchant(112);
	
	public EnchantmentListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public static Enchantment[] listEnchantments() {
		Map<Integer, Enchantment> enchantments = new HashMap<Integer, Enchantment>();
		enchantments.put(100, glowEffect);
		enchantments.put(101, onehit);
		enchantments.put(102, explosion);
		enchantments.put(103, glow);
		enchantments.put(104, nuke);
		enchantments.put(105, smite);
		enchantments.put(106, returnFire);
		enchantments.put(107, light);
		enchantments.put(108, absorbtion);
		enchantments.put(109, thickSkin);
		enchantments.put(110, midasTouch);
		enchantments.put(111, haste);
		enchantments.put(112, summoner);
		//ADD TO LIST
		return enchantments.values().toArray(new Enchantment[enchantments.size()]);
	}
	
	@SuppressWarnings({ "unchecked" })
	public static void registerEssentialsEnchantment() {
		
		Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		
		if(essentials.isEnabled() && !(essentials == null)) {
			try {
				Field enchantmentsField = Enchantments.class.getDeclaredField("ENCHANTMENTS");
				Field aliasEnchantmentsField = Enchantments.class.getDeclaredField("ALIASENCHANTMENTS");
				
				enchantmentsField.setAccessible(true);
				aliasEnchantmentsField.setAccessible(true);
				
				HashMap<String, Enchantment> enchantments = (HashMap<String, Enchantment>) enchantmentsField.get(null);
				HashMap<String, Enchantment> aliasEnchantments = (HashMap<String, Enchantment>) aliasEnchantmentsField.get(null);
				
				enchantments.put("onehit", onehit);
				aliasEnchantments.put("one", onehit);
				enchantments.put("explosion", explosion);
				aliasEnchantments.put("expl", explosion);
				aliasEnchantments.put("explode", explosion);
				enchantments.put("glow", glow);
				enchantments.put("nuke", nuke);
				enchantments.put("smite", smite);
				enchantments.put("returnFire", returnFire);
				aliasEnchantments.put("return", returnFire);
				enchantments.put("lightweight", light);
				aliasEnchantments.put("light", light);
				enchantments.put("absorbtion", absorbtion);
				aliasEnchantments.put("absorb", absorbtion);
				aliasEnchantments.put("abs", absorbtion);
				enchantments.put("thickskin", thickSkin);
				aliasEnchantments.put("thick", thickSkin);
				aliasEnchantments.put("skin", thickSkin);
				enchantments.put("midastouch", midasTouch);
				aliasEnchantments.put("midas", midasTouch);
				enchantments.put("haste", haste);
				enchantments.put("summoner", summoner);
				aliasEnchantments.put("summon", summoner);
				//ADD TO LIST
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
public static int getCurrentLevel(ItemStack item, String enchantment, Player player) {
		
		ItemMeta meta = item.getItemMeta();
		Enchantment enchant = Enchantment.getByName(enchantment.toUpperCase());
		
		boolean admin = player.hasPermission("enchanter.admin");
		boolean unsafe = player.hasPermission("enchanter.unsafe");
			
		for(Entry<Enchantment, Integer> entry : meta.getEnchants().entrySet()) {
			switch(entry.getKey().getName()) {
			case "EXPLOSIVE":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > 10 && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						int calculated = (int) ((level / 2.5) * 1.5);
						if(calculated > 1000) {
							return 1000;
						} else {
							return calculated;
						}
					}
					else if(meta.getEnchantLevel(enchant) > 10 && !(admin || unsafe)) {
						return 10;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level / 2.5) * 1.5);
					}
				}
			case "GLOW":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) < 100) {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 50) * 0.75);
					} else {
						return 3750;
					}
				}
			case "SMITE":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 10) / 0.75);
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return 80;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 10) / 0.75);
					}
				}
			case "RETURN_FIRE":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 5) / 0.75);
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return 80;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 5) / 0.75);
					}
				}
			case "LIGHTWEIGHT":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return level / 2;
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return (int) 2.5;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return level / 2;
					}
				}
			case "ABSORBTION":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return (int) (level / 0.25);
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return 50;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return (int) (level / 0.25);
					}
				}
			case "THICK_SKIN":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 5) / 0.75);
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return 80;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 5) / 0.75);
					}
				}
			case "MIDAS_TOUCH":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 2.5) / 0.75);
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return 80;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return (int) ((level * 2.5) / 0.75);
					}
				}
			case "HASTE":
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && (admin || unsafe)) {
						int level = meta.getEnchantLevel(enchant);
						return level / 2;
					}
					else if(meta.getEnchantLevel(enchant) > enchant.getMaxLevel() && !(admin || unsafe)) {
						return 3;
					} else {
						int level = meta.getEnchantLevel(enchant);
						return level / 2;
					}
				}
			default:
				if(!entry.getKey().getName().equalsIgnoreCase(enchant.getName())) {
					continue;
				} else {
					return meta.getEnchantLevel(enchant);
				}
			}
		}
		return 0;
	}	
}
