package me.noeffort.enhancedenchanter.util.enchantment;

import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.noeffort.enhancedenchanter.Main;

public class ArmorEnchantmentGetter implements Listener {

	Main plugin;
	
	public ArmorEnchantmentGetter(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onePlayerMove(PlayerMoveEvent event) {
		if(event.getPlayer() instanceof Player) {
			
			Player player = (Player) event.getPlayer();
			ItemStack[] armor = player.getInventory().getArmorContents();
			
			for(ItemStack item : armor) {
				if(item == null || item.getType() == Material.AIR) {
					continue;
				} else {
					for(Material material : boots) {
						if(item.getType() != material) {
							continue;
						} else {
							for(Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
								switch(entry.getKey().getName().toUpperCase()) {
								case "LIGHTWEIGHT":
									if(item.containsEnchantment(Enchantment.getByName("LIGHTWEIGHT"))) {
										PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, 10, EnchantmentListener.getCurrentLevel(item, "LIGHTWEIGHT", player));
										player.addPotionEffect(speed);
										break;
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
			}
		} else {
			return;
		}
	}
	
	@EventHandler
	public void onEntityAttack(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof LivingEntity && event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			ItemStack[] armor = player.getInventory().getArmorContents();
			
			for(ItemStack item : armor) {
				if(item == null || item.getType() == Material.AIR) {
					continue;
				} else {
					for(Material material : all) {
						if(item.getType() != material) {
							continue;
						} else {
							for(Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
								switch(entry.getKey().getName().toUpperCase()) {
								case "ABSORBTION":
									if(item.containsEnchantment(Enchantment.getByName("ABSORBTION"))) {
										Random random = new Random();
										int chance = random.nextInt(100);
										
										if(chance <= EnchantmentListener.getCurrentLevel(item, "ABSORBTION", player)) {
											event.setDamage(0.0);
											event.setCancelled(true);
											
											double x = player.getLocation().getX();
											double y = player.getLocation().getY();
											double z = player.getLocation().getZ();
											
											player.spawnParticle(Particle.TOTEM, x, y + 1, z, 10);
											break;
										} else {
											break;
										}
									} else {
										break;
									}
								case "THICK_SKIN":
									if(item.containsEnchantment(Enchantment.getByName("THICK_SKIN"))) {
										Random random = new Random();
										int chance = random.nextInt(100);
										
										if(chance <= EnchantmentListener.getCurrentLevel(item, "THICK_SKIN", player)) {
											if(!absorbtionHearts(player)) {
												PotionEffect absorbtion = new PotionEffect(PotionEffectType.ABSORPTION, 100, (int) (EnchantmentListener.getCurrentLevel(item, "THICK_SKIN", player) / 2.5));
												player.addPotionEffect(absorbtion);

												double x = player.getLocation().getX();
												double y = player.getLocation().getY();
												double z = player.getLocation().getZ();
												
												player.spawnParticle(Particle.CRIT_MAGIC, x, y + 1, z, 10);
												
												break;
											} else {
												break;
											}
										} else {
											break;
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
			}
		} else {
			return;
		}
	}
	
	private boolean absorbtionHearts(Player player) {
		for(PotionEffect effect : player.getActivePotionEffects()) {
			if(effect.getType().equals(PotionEffectType.ABSORPTION)) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unused")
	private Material[] helmet = {Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET};
	@SuppressWarnings("unused")
	private Material[] chestplate = {Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE};
	@SuppressWarnings("unused")
	private Material[] leggings = {Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS};
	private Material[] boots = {Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS};
	private Material[] all = {Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET,
			Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
			Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS,
			Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS};
	
}
