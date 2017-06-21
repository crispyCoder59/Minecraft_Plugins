package mod01;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class GamblerEnchantment extends Enchantment{
	
	
	private static final String NAME = "Gambler I";
	
	private static final int ID = 200;
	
	

	public GamblerEnchantment(int id) {
		super(id);
	}

	@Override
	public boolean canEnchantItem(ItemStack stack) {
		
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

	@Override
	public EnchantmentTarget getItemTarget() {
		return null;
	}

	@Override
	public int getMaxLevel() {
		return 2;
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
		return 1;
	}

	@Override
	public boolean isCursed() {
		return false;
	}

	@Override
	public boolean isTreasure() {
		return false;
	}

}
