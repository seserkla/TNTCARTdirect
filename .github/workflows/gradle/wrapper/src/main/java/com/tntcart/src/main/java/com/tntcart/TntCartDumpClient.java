package com.tntcart;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class TntCartDumpClient implements ClientModInitializer {

    private static KeyBinding dumpKey;

    @Override
    public void onInitializeClient() {
        dumpKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.tntcart.dump",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.tntcart"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (dumpKey.wasPressed()) {
                ClientPlayNetworking.send(TntCartDump.DUMP_PACKET, PacketByteBufs.empty());
            }
        });
    }
}
