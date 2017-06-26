package mod01.custom_enchantments;

import org.bukkit.block.Block;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.Player;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class GamblerPrepareEnchantEvent extends PrepareItemEnchantEvent{

	public GamblerPrepareEnchantEvent(Player enchanter, InventoryView view, Block table, ItemStack item,
			EnchantmentOffer[] offers, int bonus) {
		
		super(enchanter, view, table, item, offers, bonus);
	}

}
