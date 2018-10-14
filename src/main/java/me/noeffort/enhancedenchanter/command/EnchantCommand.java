package me.noeffort.enhancedenchanter.command;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import me.noeffort.enhancedenchanter.Main;
import me.noeffort.enhancedenchanter.Messages;
import me.noeffort.enhancedenchanter.util.EnchantmentNameUtil;
import me.noeffort.enhancedenchanter.util.MessageUtil;
import me.noeffort.enhancedenchanter.util.enchantment.EnchantmentListener;
import me.noeffort.enhancedenchanter.util.handler.ItemHandler;

public class EnchantCommand implements CommandExecutor {

	Main plugin = Main.get();
	
	public EnchantCommand() { }
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			
			if(player.hasPermission("enchanter.admin")) {
				
				if(args.length == 0) {
					player.sendMessage(MessageUtil.translate(Messages.toolittleargs));
				}
				
				if(args.length >= 1) {
					
					//---Key---
					//args.length (0) == command
					//args.length (1) == args[0]
					//args.length (2) == args[1]
					//etc.
					
					//---/command <arg0> <arg1> etc.
					
					String[] types = {"mainhand", "offhand", "helmet", "chestplate", "leggings", "boots"};
					
					if(args.length == 1) {
						for(String string : types) {
							if(!args[0].equalsIgnoreCase(string)) {
								if(args[0].equalsIgnoreCase("list")) {
									Enchantment[] enchantments = EnchantmentListener.listEnchantments();
									player.sendMessage(MessageUtil.translate(Messages.list) + getKeys(enchantments));
									return true;
								}
								if(args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v")) {
									player.sendMessage(MessageUtil.translate(Messages.prefix + "&a" + plugin.getDescription().getVersion()));
									player.sendMessage("DEBUG TOKENS: " + plugin.getTokens().getTokens(player));
									player.sendMessage("DEBUG PERMS: " + plugin.getPermissions().getLevel(player));
									player.sendMessage("DEBUG CONFIG: " + plugin.getTokenConfig().getUUID(player));
									return true;
								}
							} else {
								player.sendMessage(MessageUtil.translate(Messages.noenchant));
								return true;
							}
						}
						player.sendMessage(MessageUtil.translate(Messages.noslot));
					}
					else if(args.length == 2) {
						
						Enchantment[] enchantments = EnchantmentListener.listEnchantments();
						
						if(!args[1].isEmpty() && args[0].equalsIgnoreCase("list")) {
							player.sendMessage(MessageUtil.translate(Messages.list) + getKeys(enchantments));
							return true;
						}
						
						if(!args[1].isEmpty() && (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v"))) {
							player.sendMessage(MessageUtil.translate(Messages.prefix + "&a" + plugin.getDescription().getVersion()));
							return true;
						}
						
						for(Enchantment enchant : enchantments) {
							if(!args[1].equalsIgnoreCase(enchant.getName())) {
								continue;
							} else {
								if(args[1].equalsIgnoreCase("GLOWEFFECT")) {
									return true;
								}
							}
						}
						player.sendMessage(MessageUtil.translate(Messages.noint));
					}
					else if(args.length == 3) {
						
						if(args[1].equalsIgnoreCase("GLOWEFFECT") && !(args[2].isEmpty())) {
							return true;
						}
						
						if(!args[1].isEmpty() && !args[2].isEmpty() && args[0].equalsIgnoreCase("list")) {
							Enchantment[] enchantments = EnchantmentListener.listEnchantments();
							player.sendMessage(MessageUtil.translate(Messages.list) + getKeys(enchantments));
							return true;
						}
						
						if(!args[1].isEmpty() && (args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("v"))) {
							player.sendMessage(MessageUtil.translate(Messages.prefix + "&a" + plugin.getDescription().getVersion()));
							return true;
						}
						
						if(!isInt(args[2])) {
							player.sendMessage(MessageUtil.translate(Messages.noint));
							return true;
						}
						else if(parseInt(args[2]) >= 3999) {
							ItemHandler.loopSlots(args[0], getEnchantment(args[1]), player, parseInt(args[2]));
							return true;
						}
						else if(parseInt(args[2]) <= 0) {
							ItemHandler.loopSlots(args[0], getEnchantment(args[1]), player, 0);
							return true;
						} else {
							ItemHandler.loopSlots(args[0], getEnchantment(args[1]), player, parseInt(args[2]));
							return true;
						}
					} else {
						player.sendMessage(MessageUtil.translate(Messages.toomanyargs));
						return true;
					}
				}
			} else {
				player.sendMessage(MessageUtil.translate(Messages.permissions));
				return true;
			}
		} else {
			sender.sendMessage(MessageUtil.translate(Messages.player));
			return true;
		}
		return true;
	}
	
	private Enchantment getEnchantment(String string) {
		Enchantment enchanted = Enchantment.getByName(string.toUpperCase());
		try {
			if(!enchanted.getName().equalsIgnoreCase(string.toUpperCase())) {
				return null;
			}
		} catch (NullPointerException ignored) {}
		return enchanted;
	}
	
	private int parseInt(String string) {
		int converted = Integer.parseInt(string);
		return converted;
	}
	
	private boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
	}
	
	private String getKeys(Enchantment[] enchantments) {
		
		EnchantmentNameUtil name = new EnchantmentNameUtil();
		ArrayList<String> keys = new ArrayList<String>();
		
		String displayName;
		
		for(Enchantment enchant : enchantments) {
			displayName = name.setDisplayName(enchant.getName());
			keys.add(displayName);
		}
		
		keys.remove(0);
		
		String joined = String.join(", ", keys);
		
		return joined;
	}

}
