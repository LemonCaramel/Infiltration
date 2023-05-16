package moe.caramel.infiltration.authlib;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.response.Response;
import moe.caramel.infiltration.Util;
import java.net.Proxy;
import java.net.URL;

/**
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣀⣀⣀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣰⠞⠉⠀⠀⠀⠀⠀⠉⠓⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡼⠑⢲⡄⠀⠀⠀⠀⠀⠀⠀⠈⢳⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⡇⠀⢸⡟⠲⠄⠀⣀⣀⣀⣀⡀⠘⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢿⡇⠀⢸⣧⠀⠀⠀⠀⣿⣏⠀⠀⢠⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣇⠀⠉⠉⢠⣿⡀⠈⠉⠉⣧⣤⡞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⢆⠀⠀⠸⠛⠇⠀⠀⢠⣿⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣀⣠⠤⠬⢷⠶⡶⠶⠶⢶⡖⣿⣿⣧⠤⠤⢤⣄⣀⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⣰⠋⠁⢀⣠⣶⣾⣤⣧⣀⣆⣸⣇⣿⣿⣿⣿⣿⣿⠟⣣⣚⣛⠓⠒⠦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡟⢩⠟⠋⠉⠉⠉⠒⣄⠈⢣⡀⠀⠀⣀⡤⢖⣶⡄
 * ⠀⠀⠀⠀⢀⡠⠴⠶⣿⡀⠀⠀⠻⣿⡿⢧⣽⣸⣸⣀⣧⣿⣳⣿⠟⢀⠇⠀⠀⠀⠀⠀⠀⠈⣆⠀⠈⠉⢉⡥⠚⠉⠈⡾
 * ⠀⠀⢀⡞⠁⠀⠀⢀⡇⠑⢤⡀⠀⠈⠛⠶⣦⣥⣶⣶⣶⠿⠛⠁⢠⢾⠀⠀⠀⠀⠀⠀⠀⠀⢱⡉⠉⠉⠁⠀⠀⠀⠀⠀
 * ⠀⠀⡾⠀⠀⠀⠀⣾⠀⠀⠀⠙⢦⠀⣄⠀⠀⠈⠉⠉⠀⣀⡠⠖⠁⠸⡄⠀⠀⠀⠀⠀⠀⠀⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⢳⠀⠀⠀⠀⣿⠀⠀⠀⠀⠘⡆⢸⠳⣄⣀⢀⡴⠊⠁⠀⠀⠀⠀⠳⣄⡀⠀⠀⠀⣀⡞⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠈⣧⡀⠀⠀⠘⣇⠀⠀⠀⠀⠙⢾⣆⠀⠈⠛⠘⢯⠟⠁⠀⠀⠀⡼⠈⣿⠙⠚⢉⣹⠂⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⢠⡯⣉⡶⠒⠒⠛⠢⣀⠀⠀⠀⠀⠈⠁⠀⠀⠼⠶⠖⠂⠀⣠⠞⠁⠀⠈⡏⠉⡏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⢀⠏⢀⡎⠀⠀⠀⠀⠀⠈⠙⠲⠦⣄⣀⣀⣀⣀⣀⣀⡠⠴⠋⠁⠀⠀⠀⠀⢿⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⡞⢀⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣇⡀⠀⠀⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⣇⣼⣀⣠⠤⠤⣤⣀⠤⡄⠀⠀⠀⠀⢸⡉⠉⡏⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠘⠿⣿⠯⣭⣍⠭⣷⠏⠀⣸⠦⣄⡀⠀⢸⡇⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠓⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠈⠉⠉⠉⠉⣿⣤⣼⠁⠀⠀⣹⣆⢸⠇⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡼⣿⣳⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⢠⠒⣏⡉⠉⠁⠀⠀⠀⣇⠉⠛⠢⠤⣇⣀⣀⣀⡀⠀⠀⠀⠀⠀⢸⣿⠉⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠘⠷⣄⣉⠉⠉⠐⢀⣴⡇⠙⠲⢤⣀⣀⠀⢈⣉⡇⠀⠀⠀⠀⠀⠸⣿⣄⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠉⠉⠉⠁⡞⢹⢄⡀⠀⠀⠀⢉⣭⡟⠁⠀⠀⠀⠀⠀⢠⣷⣻⣼⣧⠀⠀⠀⠀⣀⠤⠒⠲⢦⡀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⠃⢸⠀⠉⠑⠒⠒⢻⡀⢧⠀⠀⠀⠀⠀⠀⠙⣇⠀⣀⣼⣀⣠⠴⠊⠁⠀⠀⠀⢀⡇⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣾⠀⡎⠀⠀⠀⠀⠀⠈⢧⠘⡆⠀⠀⠀⠀⠀⠀⠘⢿⠁⠀⠀⠰⡄⠀⠀⠀⣀⡴⠋⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣿⠀⣇⠀⠀⠀⠀⠀⠀⠘⡆⢹⣀⣠⢤⡀⠀⠀⠀⠈⠑⠦⣄⡀⠈⠛⠯⣍⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⣾⣿⠀⣿⣿⢦⠀⠀⠀⠀⢀⣵⠀⣿⠟⠁⢣⠀⠀⠀⠀⠀⠀⠈⠉⠓⠒⠒⠛⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⢸⠹⢿⢿⠤⠟⠁⡞⠀⠀⠀⠰⡟⠛⠉⣀⠴⠋⠘⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠈⡗⠤⠤⠄⠀⠀⡇⠀⠀⠀⠀⠙⢦⠈⢀⡠⠔⠋⠹⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⢳⠒⠤⠄⠀⠀⢧⠀⠀⠀⠀⠀⠈⠳⡀⠀⠀⣀⠔⠙⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⣀⣸⣒⡶⢦⠀⠀⠘⣆⠀⠀⠀⠀⠀⠀⠹⡄⠈⠀⠀⡠⠞⠓⠲⠶⢤⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⢀⡴⠊⠉⠁⠀⠀⠈⠉⠀⠀⠀⣸⠀⠀⠀⠀⠀⠀⠀⢹⠀⠀⠊⠀⠀⠀⠀⠀⠀⠀⠉⢢⡀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⡟⠀⠀⠀⠀⠀⠀⠀⠀⣠⠤⠚⠁⠀⠀⠀⠀⠀⠀⠀⠈⢧⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣷⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠳⣀⣀⣀⣀⣠⠴⠚⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠑⠦⢄⣀⣀⣀⣀⣀⠴⠃⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⣀⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⡀⠀⠈⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 */
public final class PapyrusService extends YggdrasilAuthenticationService {

    public PapyrusService(final Proxy proxy) {
        super(proxy);
    }

    @Override
    protected <T extends Response> T makeRequest(final URL url, final Object input, final Class<T> classOfT) throws AuthenticationException {
        return Util.loopAuth(() -> super.makeRequest(url, input, classOfT));
    }

    @Override
    protected <T extends Response> T makeRequest(final URL url, final Object input, final Class<T> classOfT, final String authentication) throws AuthenticationException {
        return Util.loopAuth(() -> super.makeRequest(url, input, classOfT, authentication));
    }
}
