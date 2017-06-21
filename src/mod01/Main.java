package mod01;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		this.getCommand(CarePackage.COMMAND_LABEL).setExecutor(new CarePackage());
		this.getCommand(EXPBoost.COMMAND_LABEL).setExecutor(new EXPBoost());
		
	}
	
	@Override
	public void onDisable() {
		
	}	
}
