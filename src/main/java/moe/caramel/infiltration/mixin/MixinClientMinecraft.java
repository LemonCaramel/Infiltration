package moe.caramel.infiltration.mixin;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import moe.caramel.infiltration.authlib.PapyrusService;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import java.net.Proxy;

@Mixin(Minecraft.class)
public final class MixinClientMinecraft {

    @Redirect(
        method = "<init>",
        at = @At(
            value = "NEW",
            target = "com/mojang/authlib/yggdrasil/YggdrasilAuthenticationService",
            remap = false
        )
    )
    public YggdrasilAuthenticationService init(final Proxy proxy) {
        return new PapyrusService(proxy);
    }
}
