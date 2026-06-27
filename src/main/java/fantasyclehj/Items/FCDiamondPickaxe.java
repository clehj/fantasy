package fantasyclehj.Items;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class FCDiamondPickaxe extends ItemPickaxe {
    public FCDiamondPickaxe(String name, Item.ToolMaterial material) {
        super(material);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(material.getMaxUses() + 5000);
    }


}


