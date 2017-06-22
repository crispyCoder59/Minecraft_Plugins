package mod01.commands;

import java.util.HashMap;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Grants the player a few starting items and food to help
 * jump start their game. Can be used once per day.
 * @author Kevyn Browning
 *
 */
public class StarterCarePackage extends CarePackage {
	
	public static final String COMMAND_LABEL = "carePackage";
	
	public StarterCarePackage(){
		playerCommandUsages = new HashMap<>();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg3) {
		if(COMMAND_LABEL.equalsIgnoreCase(label) && sender instanceof Player){
			Player player = (Player) sender;
			//Always register player first.
			registerPlayer(player);
			
			//Check if player has used command in last 24 hours.
			if(cooldownReady(player)){
				return applyCommand(player);
			}
		}
		
        return false;
	}
	
	@Override
	public boolean cooldownReady(Player player){
		return System.currentTimeMillis() - playerCommandUsages.get(player.getPlayerListName()) >= TWENTY_FOUR_HOURS_MS;
	}
	
	@Override
	public boolean applyCommand(Player player){
		
		registerPlayerUsage(player);
		
		ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE, 1);
		ItemStack shovel = new ItemStack(Material.IRON_SPADE, 1);
		ItemStack axe = new ItemStack(Material.IRON_AXE, 1);
		ItemStack beef = new ItemStack(Material.COOKED_BEEF, 5);
		
		player.getInventory().addItem(pickaxe, shovel, axe, beef);
		
		return true;
	}
	
	@Override
	public void registerPlayerUsage(Player player){
		playerCommandUsages.put(player.getPlayerListName(), System.currentTimeMillis());
	}
}
