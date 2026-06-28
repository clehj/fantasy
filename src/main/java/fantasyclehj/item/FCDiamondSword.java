package fantasyclehj.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemSword;

import java.util.UUID;

public class FCDiamondSword extends ItemSword {
    // 定义一个唯一的UUID
    private static final UUID ATTACK_DAMAGE_MODIFIER = UUID.fromString("707B58F1-1AAE-43C0-9DA9-25C3D8C90790");
    private static final UUID ATTACK_SPEED_MODIFIER = UUID.fromString("25BE7B5C-9DB0-4D88-8190-1CAEE54C4150");

    public FCDiamondSword(String name, ToolMaterial material) {
        super(material);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setMaxDamage(material.getMaxUses() + 5000);
    }

    // 覆盖getItemAttributeModifiers方法来添加自定义的属性修饰符
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 10.0D, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", 10D, 0));
        }
        return multimap;
    }
}