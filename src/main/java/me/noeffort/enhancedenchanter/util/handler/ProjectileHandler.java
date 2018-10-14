package me.noeffort.enhancedenchanter.util.handler;

import java.util.Random;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.noeffort.enhancedenchanter.Main;
import me.noeffort.enhancedenchanter.Messages;
import me.noeffort.enhancedenchanter.util.MessageUtil;
import me.noeffort.enhancedenchanter.util.enchantment.EnchantmentListener;

public class ProjectileHandler implements Listener {

	private static Projectile projectile;
	Main plugin;
	
	static int nukeCount = 10;
	
	public ProjectileHandler(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent event) {
		if(event.getEntity() instanceof Projectile) {
			
			Projectile projectile = (Projectile) event.getEntity();
			ProjectileHandler.projectile = projectile;
			
			if(projectile.getShooter() instanceof LivingEntity) {
				
				LivingEntity shooter = (LivingEntity) projectile.getShooter();
				
				if(shooter == null) {
					return;
				} else {
					if(event.getHitEntity() instanceof Player) {
						
						Player player = (Player) event.getHitEntity();
						
						if(event.getHitEntity().equals(player) || event.getHitEntity() == null) {
							return;
						} else {
							
							ItemStack[] armor = player.getInventory().getArmorContents();
							
							for(ItemStack item : armor) {
								if(item == null || item.getType().equals(Material.AIR)) {
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
															player.setHealth(player.getHealth() + player.getLastDamage());
															
															double x = player.getLocation().getX();
															double y = player.getLocation().getY();
															double z = player.getLocation().getZ();
															
															player.spawnParticle(Particle.TOTEM, x, y + 1, z, 10);
															break;
														} else {
															break;
														}
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
														}
													}
												default:
													break;
												}
											}
										}
									}
								}
							}
						}
					} else {
						
						if(projectile.getShooter() instanceof Player) {
							
							
							Player player = (Player) projectile.getShooter();
							ItemStack mainhand = player.getInventory().getItemInMainHand();
							
							for(Entry<Enchantment, Integer> entry: mainhand.getEnchantments().entrySet()) {
								switch(entry.getKey().getName()) {
								case "ONE_HIT":
									if(mainhand.containsEnchantment(Enchantment.getByName("ONE_HIT"))) {
										player.getInventory().removeItem(mainhand);
										break;
									}
								case "EXPLOSIVE":
									if(mainhand.containsEnchantment(Enchantment.getByName("EXPLOSIVE"))) {
										if(event.getHitEntity() == null) {
											double x = event.getHitBlock().getX();
											double y = event.getHitBlock().getY() + 1;
											double z = event.getHitBlock().getZ();
											
											int currentLevel = EnchantmentListener.getCurrentLevel(mainhand, "EXPLOSIVE", player);
											
											player.getWorld().createExplosion(x, y, z, currentLevel, false, false);
											projectile.setBounce(false);
											projectile.remove();
											break;	
										} 
										else if(!(event.getHitEntity().equals(player)) && event.getHitEntity() != null) {
											double x = event.getHitEntity().getLocation().getX();
											double y = event.getHitEntity().getLocation().getY();
											double z = event.getHitEntity().getLocation().getZ();
											
											int currentLevel = EnchantmentListener.getCurrentLevel(mainhand, "EXPLOSIVE", player);
											
											player.getWorld().createExplosion(x, y, z, currentLevel, false, false);
											projectile.setBounce(false);
											projectile.remove();
											break;	
										} else {
											break;
										}
									} else {
										break;
									}
								case "GLOW":
									if(mainhand.containsEnchantment(Enchantment.getByName("GLOW"))) {
										if(event.getHitEntity() == null) {
											break;
										}
										else if(!(event.getHitEntity().equals(player)) && event.getHitEntity() != null) {
											
											LivingEntity target = (LivingEntity) event.getHitEntity();
											int currentLevel = EnchantmentListener.getCurrentLevel(mainhand, "GLOW", player);
											
											PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, currentLevel, 1);
											
											target.addPotionEffect(glow);
											break;
										} else {
											break;
										}
									} else {
										break;
									}
								case "NUKE":
									if(mainhand.containsEnchantment(Enchantment.getByName("NUKE"))) {
										if(event.getHitEntity() == null) {
											double x = event.getHitBlock().getX();
											double y = event.getHitBlock().getY() + 1;
											double z = event.getHitBlock().getZ();
											
											Bukkit.broadcastMessage(MessageUtil.translate(Messages.prefix + "&a" + player.getName() +
													" has dropped a nuke at: &c" + x + "&a, &c" + y + "&a, &c" + z + "&a! &6[Power: &c150&6]"));
											player.getWorld().strikeLightning(event.getHitBlock().getLocation());
											
											player.getInventory().removeItem(mainhand);
											player.sendMessage(MessageUtil.translate(Messages.prefix + "&cYour item has shattered!"));
											
											int copy = nukeCount;
											
											new BukkitRunnable() {
												@Override
												public void run() {
													if(nukeCount == 0) {
														Bukkit.broadcastMessage(MessageUtil.translate("&4RUN!"));
														player.getWorld().createExplosion(x, y, z, 150, true, true);
														projectile.setBounce(false);
														projectile.remove();
														cancel();
														nukeCount = copy;
													} else {
														nukeCount--;
														if(nukeCount >= (copy / 1.5)) {
															Bukkit.broadcastMessage(MessageUtil.translate("&e" + nukeCount));
														}
														else if(nukeCount <= (copy / 1.5) && nukeCount >= (copy / 4.5)) {
															Bukkit.broadcastMessage(MessageUtil.translate("&6" + nukeCount));
															player.getWorld().strikeLightning(event.getHitBlock().getLocation());
														}
														else if(nukeCount <= (copy / 4.5)) {
															Bukkit.broadcastMessage(MessageUtil.translate("&c" + nukeCount));
														}
													}
												}
											}.runTaskTimer(plugin, 0L, 20L);
											break;	
										} 
										else if(!(event.getHitEntity().equals(player)) && event.getHitEntity() != null) {
											double x = event.getHitEntity().getLocation().getX();
											double y = event.getHitEntity().getLocation().getY();
											double z = event.getHitEntity().getLocation().getZ();
											
											Bukkit.broadcastMessage(MessageUtil.translate(Messages.prefix + "&a" + player.getName() +
													" has dropped a nuke at: &c" + x + "&a, &c" + y + "&a, &c" + z + "&a! &6[Power: &c150&6]"));
											player.getWorld().strikeLightning(event.getHitEntity().getLocation());
											
											player.getInventory().removeItem(mainhand);
											player.sendMessage(MessageUtil.translate(Messages.prefix + "&cYour item has shattered!"));
											
											int copy = nukeCount;
											
											new BukkitRunnable() {
												@Override
												public void run() {
													if(nukeCount == 0) {
														Bukkit.broadcastMessage(MessageUtil.translate("&4RUN!"));
														player.getWorld().createExplosion(x, y, z, 150, true, true);
														projectile.setBounce(false);
														projectile.remove();
														cancel();
														nukeCount = copy;
													}
													else {
														nukeCount--;
														if(nukeCount >= (copy / 1.5)) {
															Bukkit.broadcastMessage(MessageUtil.translate("&e" + nukeCount));
														}
														else if(nukeCount <= (copy / 1.5) && nukeCount >= (copy / 4.5)) {
															Bukkit.broadcastMessage(MessageUtil.translate("&6" + nukeCount));
															player.getWorld().strikeLightning(event.getHitEntity().getLocation());
														}
														else if(nukeCount <= (copy / 4.5)) {
															Bukkit.broadcastMessage(MessageUtil.translate("&c" + nukeCount));
														}
													}
												}
											}.runTaskTimer(plugin, 0L, 20L);
											break;	
										} else {
											break;
										}
									}
								case "SMITE":
									if(mainhand.containsEnchantment(Enchantment.getByName("SMITE"))) {
										if(event.getHitEntity() == null) {
											
											Random random = new Random();
											int chance = random.nextInt(100);
											
											if(chance <= EnchantmentListener.getCurrentLevel(mainhand, "SMITE", player)) {
												Block target = (Block) event.getHitBlock();
												target.getWorld().strikeLightning(target.getLocation());
												break;
											} else {
												break;
											}		
										}
										else if(!event.getHitEntity().equals(player) && event.getHitEntity() != null) {
											
											Random random = new Random();
											int chance = random.nextInt(100);
											
											if(chance <= EnchantmentListener.getCurrentLevel(mainhand, "SMITE", player)) {
												LivingEntity target = (LivingEntity) event.getHitEntity();
												target.getWorld().strikeLightning(target.getLocation());
												break;
											} else {
												break;
											}
										} else {
											break;
										}
									}
								case "RETURN_FIRE":
									if(mainhand.containsEnchantment(Enchantment.getByName("RETURN_FIRE"))) {
										if(event.getHitEntity() == null) {
											break;
										}
										else if(!event.getHitEntity().equals(player) && event.getHitEntity() != null) {
											
											LivingEntity target = (LivingEntity) event.getHitEntity();
											
											Random random = new Random();
											int chance = random.nextInt(100);
											
											if(chance <= EnchantmentListener.getCurrentLevel(mainhand, "RETURN_FIRE", player)) {
												
												double lastDamage = player.getLastDamage();
												
												double x = player.getLocation().getX();
												double y = player.getLocation().getY();
												double z = player.getLocation().getZ();
												
												target.damage(lastDamage * 1.5);
												player.spawnParticle(Particle.SPELL, x, y + 1, z, 10);
												break;
											} else {
												break;
											}
										} else {
											break;
										}
									}
								default:
									break;
								}
							}
							
						} else {
							return;
						}
					}
				}
				return;
			} else {
				return;
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
	
	public static Projectile getProjectile() {
		return projectile;
	}
	
	@SuppressWarnings("unused")
	private Material[] helmet = {Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET};
	@SuppressWarnings("unused")
	private Material[] chestplate = {Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE};
	@SuppressWarnings("unused")
	private Material[] leggings = {Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS};
	@SuppressWarnings("unused")
	private Material[] boots = {Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS};
	private Material[] all = {Material.LEATHER_HELMET, Material.CHAINMAIL_HELMET, Material.GOLD_HELMET, Material.IRON_HELMET, Material.DIAMOND_HELMET,
			Material.LEATHER_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE, Material.GOLD_CHESTPLATE, Material.IRON_CHESTPLATE, Material.DIAMOND_CHESTPLATE,
			Material.LEATHER_LEGGINGS, Material.CHAINMAIL_LEGGINGS, Material.GOLD_LEGGINGS, Material.IRON_LEGGINGS, Material.DIAMOND_LEGGINGS,
			Material.LEATHER_BOOTS, Material.CHAINMAIL_BOOTS, Material.GOLD_BOOTS, Material.IRON_BOOTS, Material.DIAMOND_BOOTS};
	
}
