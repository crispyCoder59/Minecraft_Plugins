package mod01;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerLoginEvent;
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
		System.out.println("Join event fired");

		Player player = event.getPlayer();
		player.sendMessage(Server.SERVER_WELCOME_MESSAGE);
		
		Location loc = player.getLocation();
		Block block = loc.getBlock().getRelative(2, 0, 2);
		block.setType(Material.DIAMOND);
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		System.out.println("Block break fired");
		Player player = event.getPlayer();
		Block block = event.getBlock();
		
		if(player.getPlayerListName().equalsIgnoreCase("dickDiver23") && block.getType() == Material.DIAMOND){
			block.getDrops().add(new ItemStack(Material.DIAMOND, 10));
		}
	}
}
