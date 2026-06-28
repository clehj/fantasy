package fantasyclehj.registry.helper;

import fantasyclehj.init.FCCreativeTab;
import net.minecraft.creativetab.CreativeTabs;

import java.util.HashMap;
import java.util.Map;

public class CreativeTabHelper {

    private static final Map<String, CreativeTabs> TAB_MAP = new HashMap<>();

    static {
        // 原版 CreativeTabs
        TAB_MAP.put("building_blocks", CreativeTabs.BUILDING_BLOCKS);
        TAB_MAP.put("decorations", CreativeTabs.DECORATIONS);
        TAB_MAP.put("redstone", CreativeTabs.REDSTONE);
        TAB_MAP.put("transportation", CreativeTabs.TRANSPORTATION);
        TAB_MAP.put("misc", CreativeTabs.MISC);
        TAB_MAP.put("search", CreativeTabs.SEARCH);
        TAB_MAP.put("food", CreativeTabs.FOOD);
        TAB_MAP.put("tools", CreativeTabs.TOOLS);
        TAB_MAP.put("combat", CreativeTabs.COMBAT);
        TAB_MAP.put("brewing", CreativeTabs.BREWING);
        TAB_MAP.put("materials", CreativeTabs.MATERIALS);

        // 自定义 CreativeTabs
        TAB_MAP.put("fantasy", FCCreativeTab.FTY);
    }

    public static CreativeTabs getTab(String tabId) {
        CreativeTabs tab = TAB_MAP.get(tabId);
        return tab != null ? tab : CreativeTabs.MISC; // 默认返回 MISC
    }
}