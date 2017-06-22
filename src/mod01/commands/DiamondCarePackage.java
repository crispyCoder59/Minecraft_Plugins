package mod01.commands;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Grants the player a few diamonds per week.
 * @author Kevyn Browning
 *
 */
public class DiamondCarePackage extends CarePackage{
	
	public static final String COMMAND_LABEL = "diamondCarePackage";
	
	private static final String DIAMOND_MESSAGE = "Diamonds added to inventory.";
	
	public DiamondCarePackage(){
		playerCommandUsages = new HashMap<>();
	}
	
	@Override
	public boolean applyCommand(Player player) {

		ItemStack diamonds = new ItemStack(Material.DIAMOND, 3);
		
		player.getInventory().addItem(diamonds);
		
		player.sendMessage(DIAMOND_MESSAGE);
		
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
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
	public void registerPlayerUsage(Player player){
		playerCommandUsages.put(player.getPlayerListName(), System.currentTimeMillis());
	}

	@Override
	public boolean cooldownReady(Player player) {
		return System.currentTimeMillis() - playerCommandUsages.get(player.getPlayerListName()) >= ONE_WEEK;
	}
}
