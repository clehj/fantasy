package fantasyclehj.renderer;

import fantasyclehj.tile.TileExpSpawner;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;

public class TileExpSpawnerRenderer extends TileEntitySpecialRenderer<TileExpSpawner> {

    public static final TileExpSpawnerRenderer INSTANCE = new TileExpSpawnerRenderer();

    @Override
    public void render(TileExpSpawner te, double x, double y, double z,
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