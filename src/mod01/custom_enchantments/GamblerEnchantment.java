package mod01.custom_enchantments;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

/**
 * This enchantment will be available to enchant tools.
 * Its effect will give players a small chance of gaining
 * many extra items from mining a block, similar to fortune,
 * but also a small chance of mining nothing from blocks.
 * 
 * The percentages will be set so that the enchantment
 * has a net positive effect over time.
 * @author Kevyn Browning
 *
 */
public class GamblerEnchantment extends Enchantment{
	
	public static final String NAME = "Gambler";
	public static final int ID = 200;
	private static final int COST = 10;
	private static final int MAX_LEVEL = 5;
	private static final int START_LEVEL = 1;
	
	/* Chance of enchanting table having enchantment out of 100. */
	public static final int OFFER_CHANCE = 5;

	/**
	 * List of values that can be used for bonus quantity in loot drops.
	 * Higher values appear less often than lower values. See initBonusQuantities.
	 */
	private static ArrayList<Integer> bonusQuantities = new ArrayList<>();

	public GamblerEnchantment(int id) {
		super(id);
		initBonuses();
	}

	@Override
	public boolean canEnchantItem(ItemStack stack) {
		//Only enchants tools
		switch(stack.getType()){
			case WOOD_PICKAXE:
			case STONE_PICKAXE:
			case IRON_PICKAXE:
			case DIAMOND_PICKAXE:
			case WOOD_SPADE:
			case STONE_SPADE:
			case IRON_SPADE:
			case DIAMOND_SPADE:
			case WOOD_AXE:
			case STONE_AXE:
			case IRON_AXE:
			case DIAMOND_AXE:
				return true;
			default:
				return false;
		}
	}

	@Override
	public boolean conflictsWith(Enchantment arg0) {
		return false;
	}
	
	public int getCost(){
		return COST;
	}

	@Override
	public EnchantmentTarget getItemTarget() {
		return EnchantmentTarget.TOOL;
	}

	@Override
	public int getMaxLevel() {
		return MAX_LEVEL;
	}

	@Override
	public String getName() {
		return NAME;
	}
	
	@Override
	public int getId(){
		return ID;
	}

	@Override
	public int getStartLevel() {
		return START_LEVEL;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}
	
	/**
	 * Uses the bonusQuantities list to select a set
	 * amount of items to drop.
	 * @return a random amount of items to drop.
	 */
	public int getAmount(){
		return bonusQuantities.get(ThreadLocalRandom.current().nextInt(0, bonusQuantities.size()));
	}
	
	/**
	 * Initializes the bonus quantities list.
	 * Certain values appear multiple times in the list to
	 * increase the probability that these items will be selected
	 * randomly from a generated interest.
	 */
	private void initBonuses(){
		// 15/100 chance
		for(int i = 0; i < 15; i++){
			bonusQuantities.add(0);
		}
		
		// 15/100 chance
		for(int i = 0; i < 15; i++){
			bonusQuantities.add(1);
		}
		
		// 30/100 chance
		for(int i = 0; i < 30; i++){
			bonusQuantities.add(2);
		}
		
		// 30/100 chance
		for(int i = 0; i < 30; i++){
			bonusQuantities.add(3);
		}
		
		// 5/100 chance
		for(int i = 0; i < 5; i++){
			bonusQuantities.add(5);
		}
		
		// 4/100 chance
		for(int i = 0; i < 4; i++){
			bonusQuantities.add(10);
		}
		
		// 1/100 chance
		bonusQuantities.add(100);
	}
	
	/**
	 * Generates random odds of being granted the gambler enchantment.
	 * @return true if the enchantment will be offered.
	 */
	public static boolean grantOffer(int enchantingBonus) {
		int rand = ThreadLocalRandom.current().nextInt(1, 100);
		return (rand <= OFFER_CHANCE + enchantingBonus);
	}
}
