package me.noeffort.enhancedenchanter.enchantment;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class OneHitEnchant extends Enchantment {

	public OneHitEnchant(int id) {
		super(id);
	}
	
	@Override
	public int getId() {
		return 101;
	}

	@Override
	public boolean canEnchantItem(ItemStack arg0) {
		return true;
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return 1;
	}

	@Override
	public String getName() {
		return "ONE_HIT";
	}

	@Override
	public int getStartLevel() {
		return 0;
	}

	@Override
	public boolean isCursed() {
		return true;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}
	
}
