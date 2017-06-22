package mod01;

import org.bukkit.plugin.java.JavaPlugin;

import mod01.commands.DiamondCarePackage;
import mod01.commands.EXPCarePackage;
import mod01.commands.StarterCarePackage;

public class Main extends JavaPlugin {
	
	@SuppressWarnings("unused")
	private static PlayerListener playerListener;
	
	
	@Override
	public void onEnable() {
		//Register commands
		this.getCommand(StarterCarePackage.COMMAND_LABEL).setExecutor(new StarterCarePackage());
		this.getCommand(EXPCarePackage.COMMAND_LABEL).setExecutor(new EXPCarePackage());
		this.getCommand(DiamondCarePackage.COMMAND_LABEL).setExecutor(new DiamondCarePackage());
		
		//Create event listeners
		playerListener = new PlayerListener(this);
	}
	
	@Override
	public void onDisable() {
		
	}	
}
