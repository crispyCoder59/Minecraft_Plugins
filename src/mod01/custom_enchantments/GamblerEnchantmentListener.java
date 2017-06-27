package mod01.custom_enchantments;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class GamblerEnchantmentListener implements Listener{
	
	private GamblerEnchantment gamblerEnchantment;
	private JavaPlugin plugin;
	
	private final int BUTTON = 0;
	private boolean offered = false;
	
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
		Player player = event.getPlayer();
		System.out.println(player.getPlayerListName());
		if(isPlayerUsingEnchantment(player)){
			System.out.println("Player is using gambler enchantment.");
			handleBlockBreak(event);
		}
	}
	
	/**
	 * Add gambler enchantment to list of offers.
	 * @param event
	 */
	@EventHandler
	private void onPrepareItemEnchant(PrepareItemEnchantEvent event){
		EnchantmentOffer [] offers = event.getOffers();
		offered = GamblerEnchantment.grantOffer(event.getEnchantmentBonus()) ? true : false;
		
		if(offered && gamblerEnchantment.canEnchantItem(event.getItem())){
			addEnchantmentToOffers(offers);
		}
	}

	/**
	 * Cancels the event and applies the enchantment to the user's item.
	 * @param event
	 */
	@EventHandler
	public void onEnchantItemEvent(EnchantItemEvent event){
		Player enchanter = event.getEnchanter();
		if(gamblerEnchantmentSelected(event.whichButton())){
			event.setCancelled(true);
			applyEnchantment(event.getItem(), gamblerEnchantment, enchanter);
		}
	}
	
	/**
	 * Checks if gambler enchantment was selected from the enchanting table.
	 * This is a work around since even when the player selects the gambling enchantment,
	 * the game will not apply it. The enchantment must be added through manipulation of the
	 * ItemEnchant event.
	 * @param button the button the player selected.
	 * @param player the player doing the enchanting.
	 * @param item the item the player is enchanting
	 * @param block the enchantment table.
	 * @return true if all these parameters match the parameters for the last prepare item enchant event.
	 */
	private boolean gamblerEnchantmentSelected(int button) {
		return button == BUTTON && offered;
	}

	/**
	 * Applies the enchantment to the item.
	 * @param item the item to enchant.
	 * @param enchant the enchantment to apply.
	 */
	private void applyEnchantment(ItemStack item, GamblerEnchantment enchant, Player enchanter) {
		item.addEnchantment(enchant, enchant.getStartLevel());
		
		if(item.containsEnchantment(gamblerEnchantment)){
			int level = enchanter.getLevel();
			enchanter.setLevel(level - gamblerEnchantment.getCost());
		}
	}

	/**
	 * Adds the enchantment to the first slot in the list of offers.
	 * @param offers the enchantment offers from the enchanting table.
	 */
	private void addEnchantmentToOffers(EnchantmentOffer[] offers) {		
		//Replace first enchantment with the new enchantment.
		offers[BUTTON].setCost(gamblerEnchantment.getCost());
		offers[BUTTON].setEnchantmentLevel(gamblerEnchantment.getStartLevel());
		offers[BUTTON].setEnchantment(gamblerEnchantment);
	}

	/**
	 * Checks if player's main hand item has the enchantment.
	 * @param player the player
	 * @return true if the player's main hand item contains the enchantment.
	 */
	private boolean isPlayerUsingEnchantment(Player player){
		
		try{
			return player.getEquipment().getItemInMainHand().containsEnchantment(gamblerEnchantment);
		}
		catch(Exception e){
			System.out.println("Enchanting error: isPlayerUsingEnchantment() threw an exception");
		}
		
		return false;		
	}
	
	/**
	 * Changes the amount of diamonds dropped from Diamond Ore.
	 * The amount is determined by the gambler enchantment's
	 * getAmount method.
	 * @param event the event
	 */
	private void handleBlockBreak(BlockBreakEvent event){
		Block block = event.getBlock();
		Material blockType = block.getType();
		
		if(blockType == Material.DIAMOND_ORE){
			event.setCancelled(true);			
			breakBlock(block);

			int amount = gamblerEnchantment.getAmount();
			Location location = block.getLocation();
			dropItems(Material.DIAMOND, amount, location);
		}
		
		if(blockType == Material.EMERALD_ORE){
			event.setCancelled(true);			
			breakBlock(block);

			int amount = gamblerEnchantment.getAmount();
			Location location = block.getLocation();
			dropItems(Material.EMERALD, amount, location);
		}
	}
	
	/**
	 * Breaks a block by setting its material to air.
	 * @param block the block to break.
	 */
	private void breakBlock(Block block){
		BlockState blockState = block.getState();
		blockState.setType(Material.AIR);
		blockState.update(true);
	}
	
	/**
	 * Drops the given amount of the given material at the given location.
	 * @param material the material to drop.
	 * @param amount the amount to drop.
	 * @param location the world location to drop the items at.
	 */
	private void dropItems(Material material, int amount, Location location){
		ItemStack diamonds = new ItemStack(material, amount);
		location.getWorld().dropItemNaturally(location, diamonds);
	}
}


