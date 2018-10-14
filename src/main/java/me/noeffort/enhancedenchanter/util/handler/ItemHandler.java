package me.noeffort.enhancedenchanter.util.handler;

import java.util.ArrayList;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.MetaItemStack;
import com.earth2me.essentials.User;

import me.noeffort.enhancedenchanter.Main;
import me.noeffort.enhancedenchanter.Messages;
import me.noeffort.enhancedenchanter.enchantment.GlowEffectEnchant;
import me.noeffort.enhancedenchanter.util.EnchantmentNameUtil;
import me.noeffort.enhancedenchanter.util.MessageUtil;
import me.noeffort.enhancedenchanter.util.RomanNumerals;

public class ItemHandler implements Listener {
	
	private static ItemStack item;
	Main plugin;
	
	public ItemHandler(Main plugin) {
		this.plugin = plugin;
	}
	
	static GlowEffectEnchant glowEffect = new GlowEffectEnchant(100);
	
	public static void addEnchantment(ItemStack item, Enchantment enchantment, Player player, int level) {
		
		Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		
		ItemMeta meta = item.getItemMeta();
		ItemHandler.item = item;
		
		EnchantmentNameUtil name = new EnchantmentNameUtil();
		String displayName = name.setDisplayName(enchantment.getName());
		
		if(essentials.getConfig().getBoolean("unsafe-enchantments")) {
			if(true) {
				try {
					if(enchantment.isCursed()) {
						if(level >= 3999) {
							addLore(meta, ChatColor.RED + displayName + " enchantment.level." + level, displayName);
							meta.addEnchant(glowEffect, 1, true);
							meta.addEnchant(enchantment, level, true);
						}
						else if(level <= 0) {
							removeLore(meta, displayName);
							meta.removeEnchant(enchantment);
							checkEnchants(meta);
						} else {
							addLore(meta, ChatColor.RED + displayName + " " +  new RomanNumerals(level), displayName);
							meta.addEnchant(glowEffect, 1, true);
							meta.addEnchant(enchantment, level, true);
						}
					} else {
						if(level >= 3999) {
							addLore(meta, ChatColor.GRAY + displayName + " enchantment.level." + level, displayName);
							meta.addEnchant(glowEffect, 1, true);
							meta.addEnchant(enchantment, level, true);
						}
						else if(level <= 0) {
							removeLore(meta, displayName);
							meta.removeEnchant(enchantment);
							checkEnchants(meta);
						} else {
							addLore(meta, ChatColor.GRAY + displayName + " " + new RomanNumerals(level), displayName);
							meta.addEnchant(glowEffect, 1, true);
							meta.addEnchant(enchantment, level, true);
						}
					}
					item.setItemMeta(meta);
					
					if(essentials.isEnabled() && essentials != null) {
						MetaItemStack metaStack = new MetaItemStack(item);
						User user = (User) essentials.getUser(player);
						metaStack.addEnchantment(user.getSource(), true, enchantment, level);
						return;
					}	
				} catch (Exception e) {
					e.printStackTrace();
				}
				return;
			}
		}
		try {
			if(enchantment.isCursed()) {
				if(level >= 3999) {
					addLore(meta, ChatColor.RED + displayName + " enchantment.level." + level, displayName);
					meta.addEnchant(glowEffect, 1, true);
					meta.addEnchant(enchantment, level, false);
				}
				else if(level <= 0) {
					removeLore(meta, displayName);
					meta.removeEnchant(enchantment);
					checkEnchants(meta);
				} else {
					addLore(meta, ChatColor.RED + displayName + " " + new RomanNumerals(level), displayName);
					meta.addEnchant(glowEffect, 1, true);
					meta.addEnchant(enchantment, level, false);
				}
			} else {
				if(level >= 3999) {
					addLore(meta, ChatColor.GRAY + displayName + " enchantment.level." + level, displayName);
					meta.addEnchant(glowEffect, 1, true);
					meta.addEnchant(enchantment, level, false);
				}
				else if(level <= 0) {
					removeLore(meta, displayName);
					meta.removeEnchant(enchantment);
					checkEnchants(meta);
					
				} else {
					addLore(meta, ChatColor.GRAY + displayName + " " + new RomanNumerals(level), displayName);
					meta.addEnchant(glowEffect, 1, true);
					meta.addEnchant(enchantment, level, false);
				}
			}
			item.setItemMeta(meta);
			
			if(essentials.isEnabled() && essentials != null) {
				MetaItemStack metaStack = new MetaItemStack(item);
				User user = (User) essentials.getUser(player);
				metaStack.addEnchantment(user.getSource(), true, enchantment, level);
				return;
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addItem(ItemStack item, Enchantment enchantment, Player player, int level) {
		ItemHandler.item = item;
		addEnchantment(item, enchantment, player, level);
		player.getInventory().addItem(item);
	}
	
	private static void addLore(ItemMeta meta, String string, String displayName) {
		if(!meta.hasLore() || meta.getLore() == null) {
			ArrayList<String> lore = new ArrayList<String>();
			lore.add(string);
			meta.setLore(lore);
			return;
		} else {
			ArrayList<String> lore = new ArrayList<String>();
			lore.addAll(meta.getLore());
			
			for(Iterator<String> iterator = lore.iterator(); iterator.hasNext(); ) {
				String check = iterator.next();
				if(!check.contains(displayName)) {
					 continue;
				} else {
					iterator.remove();
					break;
				}
			}
			lore.add(string);
			meta.setLore(lore);
		}
	}
	
	private static void removeLore(ItemMeta meta, String string) {
		if(!meta.hasLore() || meta.getLore() == null) {
			return;
		} else {
			ArrayList<String> lore = new ArrayList<String>();
			lore.addAll(meta.getLore());
			
			for(Iterator<String> iterator = lore.iterator(); iterator.hasNext(); ) {
				String check = iterator.next();
				if(!check.contains(string)) {
					 continue;
				} else {
					iterator.remove();
					break;
				}
			}
			meta.setLore(lore);
		}
	}
	
	private static void checkEnchants(ItemMeta meta) {
		meta.removeEnchant(glowEffect);
		if(meta.getEnchants().isEmpty()) {
			try {
				meta.removeEnchant(glowEffect);
			} catch (Exception ignored) { }
		} else {
			meta.addEnchant(glowEffect, 1, true);
		}
	}
	
	public static void loopSlots(String args, Enchantment enchantment, Player player, int level) {
		
		ItemStack mainhand = player.getInventory().getItemInMainHand();
		ItemStack offhand = player.getInventory().getItemInOffHand();
		
		ItemStack helmet = player.getInventory().getHelmet();
		ItemStack chestplate = player.getInventory().getChestplate();
		ItemStack leggings = player.getInventory().getLeggings();
		ItemStack boots = player.getInventory().getBoots();
		
		Essentials essentials = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		
		boolean admin = player.hasPermission("enchanter.admin");
		boolean unsafe = player.hasPermission("enchanter.unsafe");
		
		boolean enabled = essentials.isEnabled();
		boolean config = essentials.getConfig().getBoolean("unsafe-enchantments");
		
		try {
			switch(args) {
			case "mainhand":
				if(mainhand == null || mainhand.getType() == Material.AIR) {
					player.sendMessage(MessageUtil.translate(Messages.noitem));
					break;
				} else {
					if(level <= enchantment.getMaxLevel() && admin) {
						ItemHandler.addEnchantment(mainhand, enchantment, player, level);
						player.sendMessage(MessageUtil.translate(Messages.itemenchant));
						break;
					}
					else if(level > enchantment.getMaxLevel() && (admin || unsafe)) {
						if(enabled && config) {
							ItemHandler.addEnchantment(mainhand, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
						else if(enabled && !config) {
							player.sendMessage(MessageUtil.translate(Messages.essentialsunsafe));
							break;
						}
						else if(!enabled) {
							ItemHandler.addEnchantment(mainhand, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						} else {
							ItemHandler.addEnchantment(mainhand, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.permissions));
						break;
					}
				}
			case "offhand":
				if(offhand == null || offhand.getType() == Material.AIR) {
					player.sendMessage(MessageUtil.translate(Messages.noitem));
					break;
				} else {
					if(level <= enchantment.getMaxLevel() && admin) {
						ItemHandler.addEnchantment(offhand, enchantment, player, level);
						player.sendMessage(MessageUtil.translate(Messages.itemenchant));
						break;
					}
					else if(level > enchantment.getMaxLevel() && (admin || unsafe)) {
						if(enabled && config) {
							ItemHandler.addEnchantment(offhand, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
						else if(enabled && !config) {
							player.sendMessage(MessageUtil.translate(Messages.essentialsunsafe));
							break;
						}
						else if(!enabled) {
							ItemHandler.addEnchantment(offhand, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						} else {
							ItemHandler.addEnchantment(offhand, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.permissions));
						break;
					}
				}
			case "helmet":
				if(helmet == null || helmet.getType() == Material.AIR) {
					player.sendMessage(MessageUtil.translate(Messages.noitem));
					break;
				} else {
					if(level <= enchantment.getMaxLevel() && admin) {
						ItemHandler.addEnchantment(helmet, enchantment, player, level);
						player.sendMessage(MessageUtil.translate(Messages.itemenchant));
						break;
					}
					else if(level > enchantment.getMaxLevel() && (admin || unsafe)) {
						if(enabled && config) {
							ItemHandler.addEnchantment(helmet, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
						else if(enabled && !config) {
							player.sendMessage(MessageUtil.translate(Messages.essentialsunsafe));
							break;
						}
						else if(!enabled) {
							ItemHandler.addEnchantment(helmet, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						} else {
							ItemHandler.addEnchantment(helmet, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.permissions));
						break;
					}
				}
			case "chestplate":
				if(chestplate == null || chestplate.getType() == Material.AIR) {
					player.sendMessage(MessageUtil.translate(Messages.noitem));
					break;
				} else {
					if(level <= enchantment.getMaxLevel() && admin) {
						ItemHandler.addEnchantment(chestplate, enchantment, player, level);
						player.sendMessage(MessageUtil.translate(Messages.itemenchant));
						break;
					}
					else if(level > enchantment.getMaxLevel() && (admin || unsafe)) {
						if(enabled && config) {
							ItemHandler.addEnchantment(chestplate, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
						else if(enabled && !config) {
							player.sendMessage(MessageUtil.translate(Messages.essentialsunsafe));
							break;
						}
						else if(!enabled) {
							ItemHandler.addEnchantment(chestplate, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						} else {
							ItemHandler.addEnchantment(chestplate, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.permissions));
						break;
					}
				}
			case "leggings":
				if(leggings == null || leggings.getType() == Material.AIR) {
					player.sendMessage(MessageUtil.translate(Messages.noitem));
					break;
				} else {
					if(level <= enchantment.getMaxLevel() && admin) {
						ItemHandler.addEnchantment(leggings, enchantment, player, level);
						player.sendMessage(MessageUtil.translate(Messages.itemenchant));
						break;
					}
					else if(level > enchantment.getMaxLevel() && (admin || unsafe)) {
						if(enabled && config) {
							ItemHandler.addEnchantment(leggings, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
						else if(enabled && !config) {
							player.sendMessage(MessageUtil.translate(Messages.essentialsunsafe));
							break;
						}
						else if(!enabled) {
							ItemHandler.addEnchantment(leggings, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						} else {
							ItemHandler.addEnchantment(leggings, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.permissions));
						break;
					}
				}
			case "boots":
				if(boots == null || boots.getType() == Material.AIR) {
					player.sendMessage(MessageUtil.translate(Messages.noitem));
					break;
				} else {
					if(level <= enchantment.getMaxLevel() && admin) {
						ItemHandler.addEnchantment(boots, enchantment, player, level);
						player.sendMessage(MessageUtil.translate(Messages.itemenchant));
						break;
					}
					else if(level > enchantment.getMaxLevel() && (admin || unsafe)) {
						if(enabled && config) {
							ItemHandler.addEnchantment(boots, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
						else if(enabled && !config) {
							player.sendMessage(MessageUtil.translate(Messages.essentialsunsafe));
							break;
						}
						else if(!enabled) {
							ItemHandler.addEnchantment(boots, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						} else {
							ItemHandler.addEnchantment(boots, enchantment, player, level);
							player.sendMessage(MessageUtil.translate(Messages.itemenchant));
							break;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.permissions));
						break;
					}
				}
			default:
				player.sendMessage(MessageUtil.translate(Messages.error));
				break;
			}
		} catch (NullPointerException ignored) {
			player.sendMessage(MessageUtil.translate(Messages.noenchant));
		}
	}
	
	public static ItemStack getItem() {
		return item;
	}
	
}