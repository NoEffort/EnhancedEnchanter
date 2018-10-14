package me.noeffort.enhancedenchanter;

public interface Messages {

	String prefix = "&6[&9Enhanced&1Enchanter&6] ";
	String permissions = prefix + "&cYou do not have the valid permissions to execute this command!";
	String invalid = prefix + "&cThe command you have entered is invalid!";
	String toomanyargs = prefix + "&cYou have entered too many arguments for the command!";
	String toolittleargs = prefix + "&cYou have not entered enough arguments for the command!";
	String player = prefix + "&cYou must be a player to execute this command!";
	String target = prefix + "&cThe player you have specified is not online!";
	String wip = prefix + "&fThis command or action is a work in progress, give it time.";
	String error = prefix + "&cAn error has occured!";
	String unknown = prefix + "&4An unknown error has occured! Please inform the plugin developer!";
	String noslot = prefix + "&cNo inventory slot was specified! Choose from: mainhand, offhand, helmet, "
			+ "chestplate, leggings, or boots!";
	String noenchant = prefix + "&cPlease specify an enchant! Use \"/ee list\" to see all enchantments!";
	String itemgiven = prefix + "&aYou have been given your item!";
	String itemenchant = prefix + "&aYour item has been enchanted!";
	String noitem = prefix + "&cYou do not have an item in that slot!";
	String noint = prefix + "&cPlease specify an integer!";
	String essentialsunsafe = prefix + "&cPlease make sure unsafe enchantments is enabled in your Essentials config!";
	String list = prefix + "&aEnchantments:&6\n";
	
}
