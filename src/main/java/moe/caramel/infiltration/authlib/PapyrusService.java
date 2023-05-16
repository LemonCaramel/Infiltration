package moe.caramel.infiltration.authlib;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.response.Response;
import moe.caramel.infiltration.Util;
import org.apache.commons.lang3.Validate;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

    @Override
    public String performGetRequest(final URL url, final String authentication) throws IOException {
        Validate.notNull(url);

        // Build new Url
        final String originalHost = url.getHost();
        final boolean changeHost = Util.DEATH_NOTE.contains(originalHost);

        // Send request
        try (final var client = HttpClients.custom().setSSLHostnameVerifier((hostname, session) -> true).build()) {
            final HttpGet request = new HttpGet(changeHost ? Util.getNewUrl(url) : url.toString());
            if (authentication != null) request.addHeader("Authorization", authentication);
            if (changeHost) request.addHeader("Host", originalHost);

            final CloseableHttpResponse response = client.execute(request);
            final HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new IOException();
            }

            return EntityUtils.toString(entity);
        }
    }

    @Override
    public String performPostRequest(final URL url, final String post, final String contentType) throws IOException {
        Validate.notNull(url);
        Validate.notNull(post);
        Validate.notNull(contentType);

        // Init post data
        final byte[] postAsBytes = post.getBytes(StandardCharsets.UTF_8);

        // Build new Url
        final String originalHost = url.getHost();
        final boolean changeHost = Util.DEATH_NOTE.contains(originalHost);

        // Send request
        try (final var client = HttpClients.custom().setSSLHostnameVerifier((hostname, session) -> true).build()) {
            final HttpPost request = new HttpPost( changeHost ? Util.getNewUrl(url) : url.toString() );

            request.addHeader("Content-Type", contentType + "; charset=utf-8");
            request.setEntity(new ByteArrayEntity(postAsBytes));
            if (changeHost) request.addHeader("Host", originalHost);

            final CloseableHttpResponse response = client.execute(request);
            final int statusCode = response.getStatusLine().getStatusCode();
            final HttpEntity entity = response.getEntity();
            if (entity == null && statusCode != 204) {
                throw new IOException();
            }

            return (entity == null) ? "" : EntityUtils.toString(entity);
        }
    }
}
