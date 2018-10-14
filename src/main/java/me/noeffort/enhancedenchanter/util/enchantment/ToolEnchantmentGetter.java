package me.noeffort.enhancedenchanter.util.enchantment;

import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;

import me.noeffort.enhancedenchanter.Main;

public class ToolEnchantmentGetter implements Listener {

	Main plugin = Main.get();
	
	public ToolEnchantmentGetter() {}
	
	
	public boolean isHasteItem(Player player) {

		ItemStack mainhand = player.getInventory().getItemInMainHand();
			
		for(Material material : all) {
			if(mainhand.getType() != material) {
				continue;
			} else {
				for(Entry<Enchantment, Integer> entry : mainhand.getEnchantments().entrySet()) {
					switch(entry.getKey().getName().toUpperCase()) {
					case "HASTE":
						if(mainhand.containsEnchantment(Enchantment.getByName("HASTE"))) {
							return true;
						} else {
							return false;
						}
					default:
						break;
					}
				}
			}
		}
		return false;
	}
	
	int taskVal;
	
	public void checkItemForHaste() {
		
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		
		int taskID = scheduler.scheduleSyncRepeatingTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				if(!plugin.isEnabled()) {
					scheduler.cancelTask(taskVal);
					return;
				}
				for(Player player : Bukkit.getServer().getOnlinePlayers()) {
					
					ItemStack mainhand = player.getInventory().getItemInMainHand();
					
					if(isHasteItem(player)) {
						
						PotionEffect haste = new PotionEffect(PotionEffectType.FAST_DIGGING, 100, EnchantmentListener.getCurrentLevel(mainhand, "HASTE", player));
						
						if(player.getGameMode().equals(GameMode.CREATIVE)) {
							return;
						} else {
							player.addPotionEffect(haste);
							return;
						}
					} else {
						return;
					}
				}
			}
			
		}, 0L, 20L);
		
		taskVal = taskID;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		checkItemForHaste();
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		
		Player player = (Player) event.getPlayer();
		ItemStack mainhand = player.getInventory().getItemInMainHand();
		
		for(Material material : pickaxe) {
			if(mainhand.getType() != material) {
				continue;
			} else {
				for(Entry<Enchantment, Integer> entry : mainhand.getEnchantments().entrySet()) {
					switch(entry.getKey().getName().toUpperCase()) {
					case "MIDAS_TOUCH":
						if(mainhand.containsEnchantment(Enchantment.getByName("MIDAS_TOUCH"))) {
							
							Block block = event.getBlock();
							Location location = block.getLocation();
							
							Random random = new Random();
							int lowChance = random.nextInt(100);
							int highChance = random.nextInt(300);
							
							if(highChance <= EnchantmentListener.getCurrentLevel(mainhand, "MIDAS_TOUCH", player)) {
								if(player.getGameMode().equals(GameMode.CREATIVE)) {
									break;
								} else {
									location.getWorld().dropItem(location, new ItemStack(Material.GOLD_INGOT));
									player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 3.0F, 1.8F);
									block.breakNaturally(mainhand);
								}
							} else {
								if(player.getGameMode().equals(GameMode.CREATIVE)) {
									break;
								} else {
									block.breakNaturally(mainhand);
								}
							}
							
							if (lowChance <= EnchantmentListener.getCurrentLevel(mainhand, "MIDAS_TOUCH", player)) {
								if(player.getGameMode().equals(GameMode.CREATIVE)) {
									break;
								} else {
									location.getWorld().dropItem(location, new ItemStack(Material.GOLD_NUGGET));
									player.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 3.0F, 2.0F);
									block.breakNaturally(mainhand);
								}
							} else {
								if(player.getGameMode().equals(GameMode.CREATIVE)) {
									break;
								} else {
									block.breakNaturally(mainhand);
								}
							}
						} else {
							break;
						}
					default:
						break;
					}
				}
			}
		}
	}
	
	private Material[] pickaxe = {Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE};
	@SuppressWarnings("unused")
	private Material[] axe = {Material.WOOD_AXE, Material.STONE_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE};
	@SuppressWarnings("unused")
	private Material[] shovel = {Material.WOOD_SPADE, Material.STONE_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE};
	@SuppressWarnings("unused")
	private Material[] hoe = {Material.WOOD_HOE, Material.STONE_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.DIAMOND_HOE};
	private Material[] all = {Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.GOLD_PICKAXE, Material.IRON_PICKAXE, Material.DIAMOND_PICKAXE,
			Material.WOOD_AXE, Material.STONE_AXE, Material.GOLD_AXE, Material.IRON_AXE, Material.DIAMOND_AXE,
			Material.WOOD_SPADE, Material.STONE_SPADE, Material.GOLD_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE,
			Material.WOOD_HOE, Material.STONE_HOE, Material.GOLD_HOE, Material.IRON_HOE, Material.DIAMOND_HOE};	

}