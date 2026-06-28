package fantasyclehj.block;

import fantasyclehj.init.FCBlockFlower;
import net.minecraft.block.SoundType;

public class BlueRose extends FCBlockFlower {
    public BlueRose(){
        this.setSoundType(SoundType.PLANT);
        this.setTickRandomly(false);
        this.createBlockState();

    }


}