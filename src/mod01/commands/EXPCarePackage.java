package mod01.commands;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Grants bonus exp levels to players.
 * The playerCommandUsagesMap keeps track
 * of how many times the player has used the command
 * instead of when they used it.
 * This is because this command may be used multiple times per cooldown.
 * 
 * @author Kevyn Browning
 *
 */
public class EXPCarePackage extends CarePackage{
	
	public static final String COMMAND_LABEL = "DivineInspiration";
	
	/**
	 * The amount of bonus exp to grant the player.
	 */
	private static final int LEVEL_BONUS = 5;
	
	/**
	 * The time between usage resets.
	 */
	private static final long COOLDOWN = TWENTY_FOUR_HOURS_MS;
	
	/**
	 * The maximum number of usages per cooldown.
	 */
	private static final int MAX_ALLOWED_PER_COOLDOWN = 1;
	
	/**
	 * The time of the last reset of the cooldown.
	 */
	private static long LAST_RESET;
	
	public EXPCarePackage(){
		playerCommandUsages = new HashMap<>();
		LAST_RESET = System.currentTimeMillis();
	}
		
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg3) {
		if(COMMAND_LABEL.equalsIgnoreCase(label) && sender instanceof Player){
			Player player = (Player) sender;
			//Always register player first. If the player is not registered, exceptions will be thrown.
			registerPlayer(player);
			
			//Reset usages after cooldown expires.
			if(cooldownReady(player)){
				resetUsages();
			}
			
			//If usages available, and if player is "worthy", grant bonus exp.
			if(	getPlayerUsages(player) < MAX_ALLOWED_PER_COOLDOWN && isPlayerWorthy(player)){
				return applyCommand(player);
			}
			else{
				player.sendMessage("You are not worthy.");
			}
		}
		
        return false;
	}
	
	/**
	 * Check if player is "worthy" of bonus exp.
	 * @param player the player
	 * @return true if player worthy, false otherwise.
	 */
	private boolean isPlayerWorthy(Player player) {
		return true;
	}

	@Override
	protected boolean applyCommand(Player player){
		//Register that player has used the command during this cooldown.
		registerPlayerUsage(player);
		player.setLevel(player.getLevel() + LEVEL_BONUS);
		return true;
	}
	
	@Override
	protected void registerPlayerUsage(Player player){
		Long current = playerCommandUsages.get(player.getPlayerListName());
		playerCommandUsages.put(player.getPlayerListName(), current + 1);
	}
	
	/**
	 * Gets the number of usages by the player. This is how many times the player
	 * has used the command since the last reset.
	 * @param player the player
	 * @return the number of usages by the player.
	 */
	private long getPlayerUsages(Player player){
		return playerCommandUsages.get(player.getPlayerListName());
	}
	
	/**
	 * Resets player command usages for all players every cooldown period.
	 * Only updates on calls to the command.
	 */
	private void resetUsages(){
		LAST_RESET = System.currentTimeMillis();
		Set<String> keySet = playerCommandUsages.keySet();
		if(!keySet.isEmpty()){
			for(String playerName : playerCommandUsages.keySet()){
				playerCommandUsages.put(playerName, 0L);
			}
		}
	}

	@Override
	protected boolean cooldownReady(Player player) {
		return System.currentTimeMillis() - LAST_RESET >= COOLDOWN;
	}
}
