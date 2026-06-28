package fantasyclehj.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class FCDiamondPickaxe extends ItemPickaxe {
    public FCDiamondPickaxe(String name, Item.ToolMaterial material) {
        super(material);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(material.getMaxUses() + 5000);
    }


}


