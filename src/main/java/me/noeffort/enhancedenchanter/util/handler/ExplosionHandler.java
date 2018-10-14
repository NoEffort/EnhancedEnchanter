package me.noeffort.enhancedenchanter.util.handler;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.inventory.ItemStack;

import me.noeffort.enhancedenchanter.Main;

public class ExplosionHandler implements Listener {

	Main plugin;
	
	public ExplosionHandler(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onExplosion(EntityDamageEvent event) {
		if(event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			ItemStack mainhand = player.getInventory().getItemInMainHand();
			
			if(mainhand.containsEnchantment(Enchantment.getByName("EXPLOSIVE"))) {
				if(event.getEntity() instanceof TNTPrimed) {
					return;
				}
				else if(event.getEntity() instanceof Creeper) {
					return;
				}
				else if(event.getCause().equals(DamageCause.BLOCK_EXPLOSION)) {
					event.setDamage(0.0D);
					event.setCancelled(true);
					return;
				}
				else if(event.getCause().equals(DamageCause.ENTITY_EXPLOSION)) {
					return;
				} else {
					return;
				}
			} else {
				return;
			}
		}
	}
}
