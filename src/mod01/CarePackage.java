package mod01;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Grants the player a few starting items and food to help
 * jump start their game. Can be used once per day.
 * @author Kevyn Browning
 *
 */
public class CarePackage implements CommandExecutor{
	
	public static final String COMMAND_LABEL = "carePackage";
	
	private static final long TWENTY_FOUR_HOURS_MS = 86400000L;
	
	/**
	 * Maps Player names to the time they last used the command.
	 */
	private static HashMap<String, Long> playerCommandUsages = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg3) {
		if(COMMAND_LABEL.equalsIgnoreCase(label) && sender instanceof Player){
			Player player = (Player) sender;
			
			//Check if player has used command in last 24 hours.
			if(lastUsage(player) >= TWENTY_FOUR_HOURS_MS){
				return applyCommand(player);
			}
		}
		
        return false;
	}

	/**
	 * Gets the last usage of this command by the player.
	 * @param player the player to query last usage for.
	 * @return the time of last usage.
	 */
	private long lastUsage(Player player){
		if(playerCommandUsages.containsKey(player.getPlayerListName())){
			return System.currentTimeMillis() - playerCommandUsages.get(player.getPlayerListName());
		}
		else{
			return TWENTY_FOUR_HOURS_MS;
		}
	}
	
	/**
	 * Applies the care package to the player's inventory.
	 * @param player the player to give the care package.
	 * @return true if successful, false otherwise.
	 */
	private boolean applyCommand(Player player){
		
		registerPlayerUsage(player);
		
		//1 Iron Pickaxe
		ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE, 1);
		
		//1 Iron Shovel
		ItemStack shovel = new ItemStack(Material.IRON_SPADE, 1);
		
		//1 Iron Axe
		ItemStack axe = new ItemStack(Material.IRON_AXE, 1);
		
		//5 Cooked Beef
		ItemStack beef = new ItemStack(Material.COOKED_BEEF, 5);
		
		//Place in inventory
		player.getInventory().addItem(pickaxe, shovel, axe, beef);
		
		return true;
	}
	
	/**
	 * Register the current time of this command usage in playerCommandUsages
	 * @param player the player to register this usage with.
	 */
	private void registerPlayerUsage(Player player){
		playerCommandUsages.put(player.getPlayerListName(), System.currentTimeMillis());
	}
}
