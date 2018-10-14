package me.noeffort.enhancedenchanter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import me.noeffort.enhancedenchanter.api.IEnchanter;
import me.noeffort.enhancedenchanter.command.EnchantCommand;
import me.noeffort.enhancedenchanter.enchantment.AbsorbtionEnchant;
import me.noeffort.enhancedenchanter.enchantment.ExplosiveEnchant;
import me.noeffort.enhancedenchanter.enchantment.GlowEffectEnchant;
import me.noeffort.enhancedenchanter.enchantment.GlowEnchant;
import me.noeffort.enhancedenchanter.enchantment.HasteEnchant;
import me.noeffort.enhancedenchanter.enchantment.LightweightEnchant;
import me.noeffort.enhancedenchanter.enchantment.MidasTouchEnchant;
import me.noeffort.enhancedenchanter.enchantment.NukeEnchant;
import me.noeffort.enhancedenchanter.enchantment.OneHitEnchant;
import me.noeffort.enhancedenchanter.enchantment.ReturnFireEnchant;
import me.noeffort.enhancedenchanter.enchantment.SmiteEnchant;
import me.noeffort.enhancedenchanter.enchantment.SummonerEnchant;
import me.noeffort.enhancedenchanter.enchantment.ThickSkinEnchant;
import me.noeffort.enhancedenchanter.util.enchantment.WeaponEnchantmentGetter;
import me.noeffort.enhancedenchanter.util.enchantment.ArmorEnchantmentGetter;
import me.noeffort.enhancedenchanter.util.enchantment.EnchantmentListener;
import me.noeffort.enhancedenchanter.util.enchantment.ToolEnchantmentGetter;
import me.noeffort.enhancedenchanter.util.handler.ExplosionHandler;
import me.noeffort.enhancedenchanter.util.handler.ItemHandler;
import me.noeffort.enhancedenchanter.util.handler.MobHandler;
import me.noeffort.enhancedenchanter.util.handler.ProjectileHandler;
import me.noeffort.simpletokens.api.IConfig;
import me.noeffort.simpletokens.api.IPermission;
import me.noeffort.simpletokens.api.IToken;

public class Main extends JavaPlugin {

	private static Main instance;
	
	private static IToken token = null;
	private static IPermission permission = null;
	private static IConfig config = null;
	
	public GlowEffectEnchant glowEffect = new GlowEffectEnchant(100);
	public OneHitEnchant onehit = new OneHitEnchant(101);
	public ExplosiveEnchant explosion = new ExplosiveEnchant(102);
	public GlowEnchant glow = new GlowEnchant(103);
	public NukeEnchant nuke = new NukeEnchant(104);
	public SmiteEnchant smite = new SmiteEnchant(105);
	public ReturnFireEnchant returnFire = new ReturnFireEnchant(106);
	public LightweightEnchant light = new LightweightEnchant(107);
	public AbsorbtionEnchant absorbtion = new AbsorbtionEnchant(108);
	public ThickSkinEnchant thickSkin = new ThickSkinEnchant(109);
	public MidasTouchEnchant midasTouch = new MidasTouchEnchant(110);
	public HasteEnchant haste = new HasteEnchant(111);
	public SummonerEnchant summoner = new SummonerEnchant(112);
	
	protected IEnchanter enchanter = new IEnchanter();
	
	@Override
	public void onEnable() {
		
		instance = this;
		
		loadEnchantments();
		registerEssentialsEnchantments();
		registerListeners();
		registerCommands();
		
		Bukkit.getLogger().log(Level.INFO, "Loaded " + EnchantmentListener.listEnchantments().length + " enchantments!");
		getServer().getServicesManager().register(IEnchanter.class, enchanter, this, ServicePriority.Normal);
		
		if(!setupEssentials()) {
			Bukkit.getLogger().severe("[EnhancedEnchanter] Essentials hook not found!");
			return;
		}
		
		if(!setupTokens()) {
			Bukkit.getLogger().info("[EnhancedEnchanter] Simpl-eTokens Token hook not found!");
			return;
		}
		setupPermissions();
		setupConfig();
		
		ToolEnchantmentGetter getter = new ToolEnchantmentGetter();
		getter.checkItemForHaste();
	}
	
	private boolean setupEssentials() {
        if (getServer().getPluginManager().getPlugin("Essentials") == null) {
            return false;
        }
        return true;
    }
	
	private boolean setupTokens() {
		if(getServer().getPluginManager().getPlugin("Simpl-eTokens") == null) {
			return false;
		}
		RegisteredServiceProvider<IToken> rsp = getServer().getServicesManager().getRegistration(IToken.class);
		if(rsp == null) {
			return false;
		}
		token = rsp.getProvider();
		return token != null;
	}
	
	private boolean setupPermissions() {
		RegisteredServiceProvider<IPermission> rsp = getServer().getServicesManager().getRegistration(IPermission.class);
		permission = rsp.getProvider();
		return permission != null;
	}
	
	private boolean setupConfig() {
		RegisteredServiceProvider<IConfig> rsp = getServer().getServicesManager().getRegistration(IConfig.class);
		config = rsp.getProvider();
		return config != null;
	}
	
	private void registerEssentialsEnchantments() {
		EnchantmentListener.registerEssentialsEnchantment();
	}
	
	private void registerListeners() {
		this.getServer().getPluginManager().registerEvents(new EnchantmentListener(this), this);
		this.getServer().getPluginManager().registerEvents(new WeaponEnchantmentGetter(this), this);
		this.getServer().getPluginManager().registerEvents(new ArmorEnchantmentGetter(this), this);
		this.getServer().getPluginManager().registerEvents(new ToolEnchantmentGetter(), this);
		this.getServer().getPluginManager().registerEvents(new ExplosionHandler(this), this);
		this.getServer().getPluginManager().registerEvents(new ProjectileHandler(this), this);
		this.getServer().getPluginManager().registerEvents(new MobHandler(this), this);
		this.getServer().getPluginManager().registerEvents(new ItemHandler(this), this);
	}
	
	private void registerCommands() {
		this.getCommand("enchanter").setExecutor(new EnchantCommand());
	}
	
	@SuppressWarnings({ "unchecked" })
	public void onDisable() {
		try {
			Field byIdField = Enchantment.class.getDeclaredField("byId");
			Field byNameField = Enchantment.class.getDeclaredField("byName");
			
			byIdField.setAccessible(true);
			byNameField.setAccessible(true);
			
			HashMap<Integer, Enchantment> byId = (HashMap<Integer, Enchantment>) byIdField.get(null);
			HashMap<Integer, Enchantment> byName = (HashMap<Integer, Enchantment>) byNameField.get(null);
			
			checkId(byId, glowEffect.getId());
			checkName(byName, glowEffect.getName());
			checkId(byId, onehit.getId());
			checkName(byName, onehit.getName());
			checkId(byId, explosion.getId());
			checkName(byName, explosion.getName());
			checkId(byId, glow.getId());
			checkName(byName, glow.getName());
			checkId(byId, nuke.getId());
			checkName(byName, nuke.getName());
			checkId(byId, smite.getId());
			checkName(byName, smite.getName());
			checkId(byId, returnFire.getId());
			checkName(byName, returnFire.getName());
			checkId(byId, light.getId());
			checkName(byName, light.getName());
			checkId(byId, absorbtion.getId());
			checkName(byName, absorbtion.getName());
			checkId(byId, thickSkin.getId());
			checkName(byName, thickSkin.getName());
			checkId(byId, midasTouch.getId());
			checkName(byName, midasTouch.getName());
			checkId(byId, haste.getId());
			checkName(byName, haste.getName());
			checkId(byId, summoner.getId());
			checkName(byName, summoner.getName());
			
		} catch (Exception ignored) { }
	}
	
	private void checkId(HashMap<Integer, Enchantment> number, int id) {
		if(number.containsKey(id)) {
			number.remove(id);
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	private void checkName(HashMap<Integer, Enchantment> string, String name) {
		if(string.containsKey(name)) {
			string.remove(name);
		}
	}
	
	private void loadEnchantments() {
		try {
			try {
				Field field = Enchantment.class.getDeclaredField("acceptingNew");
				field.setAccessible(true);
				field.set(null, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Enchantment.registerEnchantment(glowEffect);
				Enchantment.registerEnchantment(onehit);
				Enchantment.registerEnchantment(explosion);
				Enchantment.registerEnchantment(glow);
				Enchantment.registerEnchantment(nuke);
				Enchantment.registerEnchantment(smite);
				Enchantment.registerEnchantment(returnFire);
				Enchantment.registerEnchantment(light);
				Enchantment.registerEnchantment(absorbtion);
				Enchantment.registerEnchantment(thickSkin);
				Enchantment.registerEnchantment(midasTouch);
				Enchantment.registerEnchantment(haste);
				Enchantment.registerEnchantment(summoner);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public static Main get() {
		return instance;
	}
	
	public IToken getTokens() {
		return token;
	}
	
	public IPermission getPermissions() {
		return permission;
	}
	
	public IConfig getTokenConfig() {
		return config;
	}
	
}
