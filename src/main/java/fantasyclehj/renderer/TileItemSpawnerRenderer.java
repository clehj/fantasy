package fantasyclehj.renderer;

import fantasyclehj.tile.TileItemSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;

public class TileItemSpawnerRenderer extends TileEntitySpecialRenderer<TileItemSpawner> {

    public static final TileItemSpawnerRenderer INSTANCE = new TileItemSpawnerRenderer();

    @Override
    public void render(TileItemSpawner te, double x, double y, double z,
                       float partialTicks, int destroyStage, float alpha) {
        this.renderEntity(te.getCachedEntity(), x, y, z, 0.0F, 1.0F, te.getEntityRotate());
    }

    private void renderEntity(Entity entity, double x, double y, double z,
                              float uf, float scale, float rotate) {
        if (entity == null) return;

        GlStateManager.pushMatrix();
        GlStateManager.translate((float) x + 0.5F, (float) y + uf, (float) z + 0.5F);
        GlStateManager.rotate(rotate, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(scale, scale, scale);
        entity.setPosition(x, y, z);
        Minecraft.getMinecraft().getRenderManager().renderEntity(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F, false);
        GlStateManager.popMatrix();
    }
}