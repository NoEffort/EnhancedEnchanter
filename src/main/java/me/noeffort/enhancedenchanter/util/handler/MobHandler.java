package me.noeffort.enhancedenchanter.util.handler;

import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import me.noeffort.enhancedenchanter.Main;

public class MobHandler implements Listener {
	
	Main plugin;
	
	public MobHandler(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onSilverfishTarget(EntityTargetLivingEntityEvent event) {
		if(event.getTarget() instanceof Player) {
			if(event.getEntityType().equals(EntityType.SILVERFISH)) {
				Entity entity = event.getEntity();
				if(entity.hasMetadata("summonedBy")) {
					event.setCancelled(true);
					return;
				} else {
					event.setCancelled(false);
					return;
				}
			} else {
				event.setCancelled(false);
				return;
			}
		} else {
			event.setCancelled(false);
			return;
		}
	}
	
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent event) {
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			@Override
			public void run() {
				if(event.getEntityType().equals(EntityType.SILVERFISH)) {
					Entity entity = event.getEntity();
					if(event.getSpawnReason().equals(SpawnReason.CUSTOM)) {
						if(entity.hasMetadata("summonedBy")) {
							entity.setGlowing(true);
							return;
						} else {
							return;
						}
					} else {
						return;
					}
				} else {
					return;
				}
			}
		});
	}
	
	@EventHandler
	public void onMobDeath(EntityDeathEvent event) {
		if(event.getEntity() instanceof LivingEntity) {
			LivingEntity target = (LivingEntity) event.getEntity();
			if(target.getType().equals(EntityType.SILVERFISH)) {
				return;
			} else {
				if(target.getKiller() != null) {
					Player player = (Player) target.getKiller();
					ItemStack mainhand = player.getInventory().getItemInMainHand();
					for(Material material : weapon) {
						if(mainhand.getType() != material) {
							continue;
						} else {
							for(Entry<Enchantment, Integer> entry : mainhand.getEnchantments().entrySet()) {
								if(entry.getKey().getName().equals("SUMMONER")) {
									Location location = target.getLocation();
									target.getWorld().spawnEntity(location, EntityType.SILVERFISH).setMetadata("summonedBy", new FixedMetadataValue(plugin, player.getName()));;
								} else {
									return;
								}
							}
						}
					}
				} else {
					return;
				}
			}
		} else {
			return;
		}
	}
	
	private Material[] weapon = {Material.WOOD_SWORD, Material.STONE_SWORD, Material.GOLD_SWORD, Material.IRON_SWORD, Material.DIAMOND_SWORD};
}
