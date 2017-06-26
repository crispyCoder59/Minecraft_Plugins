package mod01.custom_enchantments;

import java.util.Map;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GamblerEnchantmentListener implements Listener{
	
	private GamblerEnchantment gamblerEnchantment;
	private JavaPlugin plugin;
	
	private Player currPlayer;
	private int BUTTON = 0;
	private Block currTable;
	private ItemStack currItem;
	
	public GamblerEnchantmentListener(JavaPlugin plugin){
		//Not sure about initialization of this enchantment.
		gamblerEnchantment = (GamblerEnchantment) Enchantment.getByName(GamblerEnchantment.NAME);
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	/**
	 * Listens for block break events and applies enchantment effect
	 * if player is using the enchantment.
	 * @param event
	 */
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event){
		System.out.print("Block broken by ");
		Player player = event.getPlayer();
		System.out.println(player.getPlayerListName());
		if(isPlayerUsingEnchantment(player)){
			System.out.println("Player is using enchantment.");
			handleBlockBreak(event);
		}
	}
	
	/**
	 * Add gambler enchantment to list of offers.
	 * @param event
	 */
	@EventHandler
	private void onPrepareItemEnchant(PrepareItemEnchantEvent event){
		
		System.out.println("Handling prepare item enchant event...");
		EnchantmentOffer [] offers = event.getOffers();
		
		currPlayer = event.getEnchanter();
		currTable = event.getEnchantBlock();
		currItem = event.getItem();
		
		System.out.println("Item: " + currItem.toString());
		if(gamblerEnchantment.canEnchantItem(currItem)){
			addEnchantmentToOffers(offers);
		}
	}
	
	@EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event){
		System.out.println("Handling enchant item event");
		int button = event.whichButton();
		Player player = event.getEnchanter();
		ItemStack item = event.getItem();
		Block block = event.getEnchantBlock();
		printEnchantments(event.getEnchantsToAdd());
		
		//Check if parameters match the prepareEnchantEvent and that the gambler enchantment was set to
		//position 0. If the button == 0, Gambler was selected.
		//Apply gambler enchantment.
		
		if(gamblerEnchantmentSelected(button, player, item, block)){
			System.out.println("Applying gambler Enchantment");
			event.setCancelled(true);
			applyEnchantment(item, gamblerEnchantment);
		}
	}
	
	private boolean gamblerEnchantmentSelected(int button, Player player, ItemStack item, Block block) {
		return button == 0 && player.equals(currPlayer) && item.equals(currItem) && block.equals(currTable);
	}

	private void applyEnchantment(ItemStack item, GamblerEnchantment enchant) {
		System.out.println("Applying enchantment to item...");
		item.addEnchantment(enchant, enchant.getStartLevel());
		if(item.containsEnchantment(gamblerEnchantment)){
			System.out.println("Item has enchantment");
		}
		
	}

	private void addEnchantmentToOffers(EnchantmentOffer[] offers) {
		System.out.println("Adding enchantment to offers");
		boolean found = false;
		
		//Search for existing enchantment in offer.
		for(EnchantmentOffer offer : offers){
			if(offer != null && offer.getEnchantment().equals(gamblerEnchantment)){
				found = true;
			}
		}
		
		//Replace first enchantment with the new enchantment.
		if(!found){
			offers[BUTTON].setCost(gamblerEnchantment.getCost());
			offers[BUTTON].setEnchantmentLevel(gamblerEnchantment.getStartLevel());
			offers[BUTTON].setEnchantment(gamblerEnchantment);
		}
		
		System.out.println("offers[0] = " + offers[BUTTON].getEnchantment().getName());
	}

	private boolean isPlayerUsingEnchantment(Player player){
		if(player != null){
			EntityEquipment equipment = player.getEquipment();
			if(equipment != null){
				ItemStack mainHandItem = equipment.getItemInMainHand();
				if(mainHandItem != null){
					return mainHandItem.containsEnchantment(gamblerEnchantment);
				}
				else{
					System.out.println("MainHandItem is null");
					return false;
				}
			}
			else{
				System.out.println("Equipment is null");
				return false;
			}
		}
		System.out.println("Player is null");
		return false;
		
	}
	
	/**
	 * Change the amount of diamonds dropped from Diamond Ore
	 * @param event
	 */
	private void handleBlockBreak(BlockBreakEvent event){
		System.out.println("Dropping bonus items");
		Block block = event.getBlock();
		if(block.getType() == Material.DIAMOND_ORE){
			//Cancel event
			event.setCancelled(true);
			
			//Change block state to air
			BlockState blockState = block.getState();
			blockState.setType(Material.AIR);
			blockState.update(true);
			
			//Manually drop items at blocks location.
			int amount = gamblerEnchantment.getAmount();
			System.out.println("Amount: " + amount);
			ItemStack diamonds = new ItemStack(Material.DIAMOND, amount);
			Location location = block.getLocation();
			location.getWorld().dropItemNaturally(location, diamonds);
		}
	}
	
	/**
	 * Prints all the elements of a map of enchantments.
	 * @param enchantments
	 */
	private void printEnchantments(Map<Enchantment, Integer> enchantments){
		Set<Enchantment> keys = enchantments.keySet();
		System.out.println("Looping through keys");
		for(Enchantment ench : keys){
			if(ench != null){
				System.out.println("Enchantment is " + ench.getName());
			}
		}
	}
}


