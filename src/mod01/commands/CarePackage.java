package mod01.commands;

import java.util.HashMap;

import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public abstract class CarePackage implements CommandExecutor{
	
	protected static final long TWENTY_FOUR_HOURS_MS = 86400000L;
	
	protected static final long ONE_WEEK = TWENTY_FOUR_HOURS_MS * 7;
		
	/**
	 * Maps player names to the last time they used the command.
	 * 
	 * For some care packages, maps player names to the number of times they used the command.
	 */
	protected HashMap<String, Long> playerCommandUsages;
	
	/**
	 * Applies the care package to the player's inventory.
	 * @param player the player to give the care package.
	 * @return true if successful, false otherwise.
	 */
	protected abstract boolean applyCommand(Player player);
	
	/**
	 * Checks if the cooldown is ready for this command.
	 * @param player the player
	 * @return true if last usage was more than twenty four hours ago.
	 */
	protected abstract boolean cooldownReady(Player player);
	
	/**
	 * Register the current time of this command usage in playerCommandUsages
	 * @param player the player to register this usage with.
	 */
	protected abstract void registerPlayerUsage(Player player);
	
	protected void registerPlayer(Player player){
		if(!playerCommandUsages.containsKey(player.getPlayerListName())){
			playerCommandUsages.put(player.getPlayerListName(), 0L);
		}
	}
}
