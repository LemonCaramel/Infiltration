package moe.caramel.infiltration.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import moe.caramel.infiltration.authlib.PapyrusService;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.net.Proxy;

@Mixin(Main.class)
public final class MixinServerMinecraft {

    @Redirect(
        method = "main",
        at = @At(
            value = "NEW",
            target = "com/mojang/authlib/yggdrasil/YggdrasilAuthenticationService",
            remap = false
        ),
        remap = false
    )
    private static YggdrasilAuthenticationService init(final Proxy proxy) {
        return new PapyrusService(proxy);
    }
}
