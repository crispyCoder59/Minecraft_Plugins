package mod01;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener implements Listener{
	
	public PlayerListener(JavaPlugin plugin){
		//Register Listener
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	
	@EventHandler
	public void onLogin(PlayerLoginEvent event){
		Player player = event.getPlayer();
		System.out.println("Player login event: " + player.getPlayerListName());
	}
	
	
	@EventHandler
	public void onLogin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		System.out.println("Player join event: " + player.getPlayerListName());
		player.sendMessage(Server.SERVER_WELCOME_MESSAGE);
		System.out.println("Sending message to " + player.getPlayerListName());
		System.out.println(Server.SERVER_WELCOME_MESSAGE);
	}
}
