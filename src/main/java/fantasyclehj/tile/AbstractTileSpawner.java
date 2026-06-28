package fantasyclehj.tile;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AbstractTileSpawner extends TileEntity implements ITickable {

    private int delay = 200;
    private int tickCounter = 0;

    @SideOnly(Side.CLIENT)
    private int rotate;

    public int setDelay(int delay) {
        if (delay <= 0) {
            return -1;
        }
        this.delay = delay;
        return this.delay;
    }

    public int getDelay() {
        return this.delay;
    }

    public abstract int setAmount(int amount);

    public abstract int getAmount();

    public abstract void spawnEntity();

    @Override
    public void update() {
        this.tickCounter++;
        if (this.tickCounter >= this.delay) {
            this.tickCounter = 0;
            this.spawnEntity();
        }
    }

    @SideOnly(Side.CLIENT)
    public abstract Entity getCachedEntity();

    @SideOnly(Side.CLIENT)
    public int getEntityRotate() {
        if (this.rotate >= 360) {
            this.rotate = 0;
        }
        return this.rotate++;
    }
}