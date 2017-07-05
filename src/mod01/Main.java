package mod01;

import java.lang.reflect.Field;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import mod01.commands.DiamondCarePackage;
import mod01.commands.EXPCarePackage;
import mod01.commands.StarterCarePackage;
import mod01.custom_enchantments.GamblerEnchantment;
import mod01.custom_enchantments.GamblerEnchantmentListener;

public class Main extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private static TestPlayerListener playerListener;
	@SuppressWarnings("unused")
	private static GamblerEnchantmentListener gamblerEnchantmentListener;
	
	/**
	 * Runs when plugin is enabled by server.
	 */
	@Override
	public void onEnable() {
		//Register commands
		this.getCommand(StarterCarePackage.COMMAND_LABEL).setExecutor(new StarterCarePackage());
		this.getCommand(EXPCarePackage.COMMAND_LABEL).setExecutor(new EXPCarePackage());
		this.getCommand(DiamondCarePackage.COMMAND_LABEL).setExecutor(new DiamondCarePackage());
		
		//Register Enchantment
		GamblerEnchantment gambler = new GamblerEnchantment(GamblerEnchantment.ID);
		registerEnchantment(gambler);
		
		//Create event listeners
		playerListener = new TestPlayerListener(this);
		gamblerEnchantmentListener = new GamblerEnchantmentListener(this);
	}
	
	/**
	 * Occurs when plugin is disabled by server.
	 */
	@Override
	public void onDisable() {
		
	}
	
	/**
	 * Registers the enchantment with the Enchantment list.
	 * @param enchant
	 */
	private void registerEnchantment(Enchantment enchant){
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
			Enchantment.registerEnchantment(enchant);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


