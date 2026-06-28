package fantasyclehj.item;


import net.minecraft.item.ItemHoe;

public class FCDiamondHoe extends ItemHoe {
    public FCDiamondHoe(String name, ToolMaterial material) {
        super(material);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(material.getMaxUses() + 5000);

    }
}
