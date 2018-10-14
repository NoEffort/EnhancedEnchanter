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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.noeffort.enhancedenchanter.Main;

public class WeaponEnchantmentGetter implements Listener {
	
	Main plugin;
	
	public WeaponEnchantmentGetter(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onHit(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			
			Player player = (Player) event.getDamager();
			ItemStack mainhand = player.getInventory().getItemInMainHand();
			
			for(Material material : weapon) {
				if(mainhand.getType() != material) {
					continue;
				} else {
					for(Entry<Enchantment, Integer> entry : mainhand.getEnchantments().entrySet()) {
						switch(entry.getKey().getName().toUpperCase()) {
						case "EXPLOSIVE":
							if(mainhand.containsEnchantment(Enchantment.getByName("EXPLOSIVE"))) {
								if(event.getEntity() instanceof LivingEntity && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
									
									LivingEntity entity = (LivingEntity) event.getEntity();
									
									double x = entity.getLocation().getX();
									double y = entity.getLocation().getY();
									double z = entity.getLocation().getZ();
									
									player.getWorld().createExplosion(x, y, z, EnchantmentListener.getCurrentLevel(mainhand, "EXPLOSIVE", player), false, false);
									break;
								} else {
									break;
								}
							}
						case "ONE_HIT":
							if(mainhand.containsEnchantment(Enchantment.getByName("ONE_HIT"))) {
								player.getInventory().removeItem(mainhand);
								break;
							}
						case "GLOW":
							if(mainhand.containsEnchantment(Enchantment.getByName("GLOW"))) {
								if(event.getEntity() instanceof LivingEntity && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
									
									LivingEntity target = (LivingEntity) event.getEntity();
									PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, EnchantmentListener.getCurrentLevel(mainhand, "GLOW", player), 1);
									
									target.addPotionEffect(glow);
									break;
								} else {
									break;
								}
							}
						case "NUKE":
							if(mainhand.containsEnchantment(Enchantment.getByName("NUKE"))) {
								if(event.getEntity() instanceof LivingEntity && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
									break;
								}
							} else {
								break;
							}
						case "SMITE":
							if(mainhand.containsEnchantment(Enchantment.getByName("SMITE"))) {
								if(event.getEntity() instanceof LivingEntity && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
									
									Random random = new Random();
									int chance = random.nextInt(100);
									
									if(chance <= EnchantmentListener.getCurrentLevel(mainhand, "SMITE", player)) {
										LivingEntity target = (LivingEntity) event.getEntity();
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
								if(event.getEntity() instanceof LivingEntity && event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
									
									LivingEntity target = (LivingEntity) event.getEntity();
									
									Random random = new Random();
									int chance = random.nextInt(100);
									
									if(chance <= EnchantmentListener.getCurrentLevel(mainhand, "RETURN_FIRE", player)) {
										
										double lastDamage = player.getLastDamage();
										
										double x = player.getLocation().getX();
										double y = player.getLocation().getY();
										double z = player.getLocation().getZ();
										
										target.damage(lastDamage * 1.5);
										player.spawnParticle(Particle.SPELL, x, y + 1, z, 10);
										System.out.println("DMG BONUS: " + lastDamage * 1.5);
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
				}
			}
		} else {
			return;
		}
	}
	
	private Material[] weapon = {Material.WOOD_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD};
}
