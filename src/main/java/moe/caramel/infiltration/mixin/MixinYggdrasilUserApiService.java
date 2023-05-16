package moe.caramel.infiltration.mixin;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.authlib.yggdrasil.YggdrasilUserApiService;
import moe.caramel.infiltration.authlib.SansClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.net.Proxy;

@Mixin(value = YggdrasilUserApiService.class, remap = false)
public final class MixinYggdrasilUserApiService {

    @Redirect(
        method = "<init>",
        at = @At(
            value = "NEW",
            target = "com/mojang/authlib/minecraft/client/MinecraftClient",
            remap = false
        ),
        remap = false
    )
    public MinecraftClient init(final String accessToken, final Proxy proxy) {
        return new SansClient(accessToken, proxy);
    }
}
