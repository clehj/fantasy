package fantasyclehj.Items;

import net.minecraft.item.ItemSpade;

public class FCDiamondShovel extends ItemSpade {
    public FCDiamondShovel(String name, ToolMaterial material) {
        super(material);
        this.setTranslationKey(name);
        this.setRegistryName(name);

        this.setMaxDamage(material.getMaxUses() + 5000);
    }
}
