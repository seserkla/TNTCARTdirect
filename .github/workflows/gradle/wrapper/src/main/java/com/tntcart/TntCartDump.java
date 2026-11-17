package com.tntcart;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class TntCartDump implements ModInitializer {

    public static final Identifier DUMP_PACKET = new Identifier("tntcart", "dump");

    @Override
    public void onInitialize() {
        ServerPlayNetworking.registerGlobalReceiver(DUMP_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> dumpCarts(player));
        });
    }

    private void dumpCarts(ServerPlayerEntity player) {
        var inventory = player.getInventory();
        Vec3d pos = player.getPos().add(0, 0.5, 0);

        for (int i = 0; i < inventory.size(); i++) {
            var stack = inventory.getStack(i);
            if (stack.getItem() == Items.TNT_MINECART) {
                int count = stack.getCount();
                for (int j = 0; j < count; j++) {
                    double ox = (Math.random() - 0.5) * 1.5;
                    double oz = (Math.random() - 0.5) * 1.5;
                    TntMinecartEntity cart = new TntMinecartEntity(
                        player.getWorld(), pos.x + ox, pos.y, pos.z + oz
                    );
                    player.getWorld().spawnEntity(cart);
                }
                inventory.removeStack(i);
            }
        }
    }
}
