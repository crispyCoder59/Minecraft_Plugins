package mod01;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener{
	
	public PlayerListener(JavaPlugin plugin){
		//Register Listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * When the player signs in to the server.
	 * @param event
	 */
	@EventHandler
	public void onLogin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		player.sendMessage(Server.SERVER_WELCOME_MESSAGE);
		
		if(player.getPlayerListName().equalsIgnoreCase("test")){
			Location loc = player.getLocation();
			Block block = loc.getBlock().getRelative(1, 0, 0);
			BlockState blockState = block.getState();
			blockState.setType(Material.DIAMOND_ORE);
			blockState.update(true);
		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		if(block.getType() == Material.DIAMOND_ORE){
			
			//Cancel event
			event.setCancelled(true);
			//Change block state to air
			BlockState blockState = block.getState();
			blockState.setType(Material.AIR);
			blockState.update(true);
			//Manually drop items at blocks location.
			ItemStack diamonds = new ItemStack(Material.DIAMOND, 10);
			Location location = block.getLocation();
			location.getWorld().dropItemNaturally(location, diamonds);
		}
	}
}
