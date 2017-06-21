package mod01;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EXPBoost implements CommandExecutor{
	
	private static final int LEVEL_BONUS = 5;
	
	private static final int MAX_ALLOWED_PER_DAY = 6;
	
	private static final long TWENTY_FOUR_HOURS_MS = 86400000L;
	
	private static long LAST_RESET = System.currentTimeMillis();
	
	public static final String COMMAND_LABEL = "Praise_Lord_Kevyn";
	
	/**
	 * Maps Player names to the time they last used the command.
	 */
	private static HashMap<String, Integer> playerCommandUsages = new HashMap<>();
		
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] arg3) {
		if(COMMAND_LABEL.equalsIgnoreCase(label) && sender instanceof Player){
			Player player = (Player) sender;
			
			if((System.currentTimeMillis() - LAST_RESET) >= TWENTY_FOUR_HOURS_MS){
				resetUsages();
			}
			
			boolean playerInMap = playerCommandUsages.containsKey(player.getPlayerListName());
			
			if(!playerInMap){
				playerCommandUsages.put(player.getPlayerListName(), 0);
			}
			
			int playerUsages = playerCommandUsages.get(player.getPlayerListName());
			
			if(	playerUsages < MAX_ALLOWED_PER_DAY &&
				isPlayerWorthy(player)){
				return applyCommand(player);
			}
			else{
				player.sendMessage("You are not worthy of Kevyn's grace.");
			}
		}
		
        return false;
	}
	
	/**
	 * Only players chosen by Lord Kevyn may receive His
	 * blessing.
	 * @param player the player
	 * @return true if player worthy, false otherwise.
	 */
	private boolean isPlayerWorthy(Player player) {
		
		String name = player.getPlayerListName();
		//Null check and check if player names match worthy players.
		return  name != null &&
				
				(name.equalsIgnoreCase("dickDiver23") ||
				name.equalsIgnoreCase("ImHammered") ||
				name.equalsIgnoreCase("aflyingcougar"));
	}

	/**
	 * Applies the command to the player
	 * @param player the player
	 * @return true if successful, false otherwise.
	 */
	private boolean applyCommand(Player player){
		registerPlayerUsage(player);
		int level = player.getLevel();
		player.setLevel(level + LEVEL_BONUS);
		return true;
	}
	
	/**
	 * Register the current time of this command usage in playerCommandUsages
	 * @param player the player to register this usage with.
	 */
	private void registerPlayerUsage(Player player){
		int current = playerCommandUsages.get(player.getPlayerListName());
		playerCommandUsages.put(player.getPlayerListName(), current + 1);
	}
	
	/**
	 * Resets player command usages for all players every 24 hours.
	 * Only updates on calls to the command.
	 */
	private void resetUsages(){
		LAST_RESET = System.currentTimeMillis();
		Set<String> keySet = playerCommandUsages.keySet();
		if(!keySet.isEmpty()){
			for(String playerName : playerCommandUsages.keySet()){
				if(playerName != null){
					playerCommandUsages.put(playerName, 0);
				}
			}
		}
	}
}
