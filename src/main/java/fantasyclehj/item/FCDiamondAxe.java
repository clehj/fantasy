package fantasyclehj.item;

import net.minecraft.item.ItemAxe;

public class FCDiamondAxe extends ItemAxe {
    public FCDiamondAxe(String name, ToolMaterial material) {
        super(material, 8.0F, -3.1F);  // 斧头：攻击力8，速度-3.1
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(material.getMaxUses() + 5000);

    }
}