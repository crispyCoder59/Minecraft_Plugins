package mod01;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Currently used for testing other plugins.
 * Spawns a diamond ore block when test player joins server.
 * @author Kevyn Browning
 *
 */
public class TestPlayerListener implements Listener{
	
	public TestPlayerListener(JavaPlugin plugin){
		//Register Listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * When the player signs in to the server,
	 * spawn 10 diamond ore blocks in front of them.
	 * @param event
	 */
	@EventHandler
	public void onLogin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		player.sendMessage(Server.SERVER_WELCOME_MESSAGE);
		
		if(player.getPlayerListName().equalsIgnoreCase("test")){
			Location loc = player.getLocation();
			for( int i = 0; i < 10; i++){
				Block block = loc.getBlock().getRelative(i, 0, 0);
				BlockState blockState = block.getState();
				blockState.setType(Material.DIAMOND_ORE);
				blockState.update(true);
			}
		}
	}
}
