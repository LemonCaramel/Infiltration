package moe.caramel.infiltration.authlib;

import com.mojang.authlib.exceptions.MinecraftClientException;
import com.mojang.authlib.exceptions.MinecraftClientHttpException;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.authlib.minecraft.client.ObjectMapper;
import com.mojang.authlib.yggdrasil.response.ErrorResponse;
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
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⡼⠉⠙⠲⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣄⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⢿⠲⢄⠀⠙⢦⡀⠀⠀⠀⣠⠔⠋⣉⠟⠀⠀⠀⠀⠀⣠⠞⠁⠀⠀⢣⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠘⡆⠀⠑⢄⠀⠙⠦⡤⠞⠁⠠⣎⠁⠀⠀⠀⠀⣠⠞⠁⣠⠖⠉⡇⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⢠⠃⠀⢷⠀⠀⣨⠇⠀⠀⠀⠀⠀⠀⠈⠉⠒⠲⠤⠞⠁⠀⢸⠁⠀⠀⡇⢸⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⣠⠋⠀⠀⠈⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠙⠢⠤⠃⢰⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⣸⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢳⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⢠⠇⠀⠀⠀⠀⠀⠀⠀⠀⠀⡴⠋⠀⢳⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢳⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⡘⠀⠀⠀⠀⠀⠀⠀⠀⣠⠞⠀⠀⠀⠀⠱⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⢀⠇⠀⠀⠀⠀⠀⠀⣠⠞⠁⠀⠀⠀⠀⠀⠀⠈⠓⠤⣀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⣼⠀⠀⠀⠀⠀⢀⡞⢁⣴⣶⣶⡄⠀⠀⠀⠀⠀⠀⠀⣀⣀⠉⢧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀
 * ⠀⣠⠖⠉⠙⢦⠀⠀⠀⠀⡞⠀⠘⢿⣿⣿⠇⠀⠀⠀⠀⠀⠀⣾⣿⣿⣷⠸⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⣇⣀⣀⣀⣀⣀⠀⠀⠀⠀⠀
 * ⡸⠁⠀⠀⠀⠀⢧⠀⠀⢰⠃⠀⠀⠀⠀⠀⠀⠀⢠⣶⣄⠀⠀⠙⠻⠿⠋⠀⡇⠀⠀⠀⠀⠀⠀⠀⠀⢀⡞⠀⠀⠀⠀⠀⠀⠈⠙⣆⠀⠀
 * ⣇⠀⠀⠀⠀⠀⠈⡇⠀⠸⡇⠀⠀⠀⠀⡄⠀⠀⠈⠉⠁⠀⠀⢠⠄⠀⠀⢠⡇⠀⠀⠀⠀⠀⠀⠀⢀⠞⠀⠀⠀⠀⠀⠀⠀⠀⢀⡇⠀⠀
 * ⠘⢦⡀⠀⠀⠀⠀⡇⠀⠀⠙⣄⠀⠀⠀⠘⢤⣀⡴⠛⠢⣀⡠⠊⠀⠀⠀⢸⠇⠀⠀⠀⠀⠀⠀⠀⡎⠀⠀⠀⠀⠀⠀⠀⠀⣠⠋⠀⠀⠀
 * ⠀⠀⠙⠲⠤⣀⣀⡇⠀⠀⠀⠈⢦⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀⠀⢸⣇⣀⡀⠀⠀⠀⢀⣠⠞⠁⣀⡠⠤⢤
 * ⠀⠀⠀⠀⠀⠀⠀⣿⠀⠀⠀⠀⠀⠳⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣼⠀⠀⠀⠀⠀⠀⢀⠞⠀⠀⠀⠉⠉⠉⠁⢀⡴⠋⠀⠀⠀⢀
 * ⠀⠀⠀⠀⠀⠀⠀⢹⠀⠀⠀⠀⠀⣸⡏⠙⠒⠲⠤⠤⠤⠤⠤⠤⠔⠚⠉⢸⠀⠀⠀⠀⠀⢠⣎⣀⣀⣀⣀⣀⣀⡠⠖⠉⠀⠀⠀⣠⠔⠁
 * ⠀⠀⠀⠀⠀⠀⠀⢸⡀⠀⠀⠀⡰⢹⠈⠓⠤⣀⡀⠀⠀⠀⠀⣀⣀⠤⠖⢻⠀⠀⠀⠀⢠⠏⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠤⠒⠉⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⢇⠀⠀⡴⠁⣸⠀⠀⠀⠀⠀⠉⠉⠉⠀⠀⠀⠀⠀⠘⡆⠀⠀⠀⡞⠀⠀⡂⠀⠀⠀⠀⠀⠀⠈⠳⡀⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠘⠦⠞⠀⠀⡏⠀⠀⠀⠀⣤⡀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠢⢄⠼⠁⠀⠀⡇⠀⠀⠀⡄⠀⠀⠀⠀⢣⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⠛⠧⢤⡀⠀⠀⠀⢰⣿⡀⠀⠀⠀⠀⠀⠀⣸⠁⠀⠀⣰⢻⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⡇⠀⢰⡇⠀⠀⠀⠈⡏⠧⣀⣀⠀⠀⢀⣴⠃⠀⢀⡴⠁⢸⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⡇⠀⠀⠀⠀⡇⠀⢸⡇⠀⠀⠀⠀⡇⠀⠀⠀⠈⠉⣡⠏⠀⢰⠁⠀⢀⡼⠀⠀⠀⠀⣸⠀⠀⠀⠀⠀
 * ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢣⡀⠀⠀⣰⠃⠀⠨⣧⡀⠀⢀⡴⠃⠀⠀⠀⣔⠉⠀⠀⢀⡼⠰⠎⠁⠀⠀⢀⣀⡰⠃⠀⠀⠀⠀⠀
 */
public class TemmieClient extends MinecraftClient {

    // not work access widener ;(
    private final String copiedToken;
    private final ObjectMapper objectMapper = ObjectMapper.create();

    public TemmieClient(final String accessToken, final Proxy proxy) {
        super(accessToken, proxy);
        this.copiedToken = accessToken;
    }

    @Override
    public <T> T get(final URL url, final Class<T> responseClass) {
        Validate.notNull(url);
        Validate.notNull(responseClass);

        try {
            // Build new Url
            final String originalHost = url.getHost();
            final boolean changeHost = Util.DEATH_NOTE.contains(originalHost);

            // Send request
            try (final var client = HttpClients.custom().setSSLHostnameVerifier((hostname, session) -> true).build()) {
                final HttpGet request = new HttpGet( changeHost ? Util.getNewUrl(url) : url.toString() );
                request.addHeader("Authorization", "Bearer " + copiedToken);
                if (changeHost) request.addHeader("Host", originalHost);

                return readInput(client.execute(request), responseClass);
            }
        } catch (final IOException exception) {
            throw new MinecraftClientException(
                MinecraftClientException.ErrorType.SERVICE_UNAVAILABLE ,
                "Failed to read from " + url + " due to " + exception.getMessage(),
                exception
            );
        }
    }

    @Override
    public <T> T post(final URL url, final Class<T> responseClass) {
        return post(url, null, responseClass);
    }

    @Override
    public <T> T post(final URL url, final Object body, final Class<T> responseClass) {
        Validate.notNull(url);
        Validate.notNull(responseClass);

        // Init post data
        final byte[] postAsBytes;
        if (body == null) {
            postAsBytes = new byte[0];
        } else {
            final String bodyAsJson = objectMapper.writeValueAsString(body);
            postAsBytes = bodyAsJson.getBytes(StandardCharsets.UTF_8);
        }

        // Send request
        try {
            // Build new Url
            final String originalHost = url.getHost();
            final boolean changeHost = Util.DEATH_NOTE.contains(originalHost);

            // Send request
            try (final var client = HttpClients.custom().setSSLHostnameVerifier((hostname, session) -> true).build()) {
                final HttpPost request = new HttpPost( changeHost ? Util.getNewUrl(url) : url.toString() );

                request.addHeader("Authorization", "Bearer " + copiedToken);
                request.addHeader("Content-Type", "application/json; charset=utf-8");
                request.setEntity(new ByteArrayEntity(postAsBytes));
                if (changeHost) request.addHeader("Host", originalHost);

                return readInput(client.execute(request), responseClass);
            }
        } catch (final IOException exception) {
            throw new MinecraftClientException(
                MinecraftClientException.ErrorType.SERVICE_UNAVAILABLE ,
                "Failed to read from " + url + " due to " + exception.getMessage(),
                exception
            );
        }
    }

    private <T> T readInput(final CloseableHttpResponse response, final Class<T> responseClass) throws IOException {
        final int status = response.getStatusLine().getStatusCode();

        if (status < 400) {
            return objectMapper.readValue(EntityUtils.toString(response.getEntity()), responseClass);
        } else {
            final HttpEntity entity = response.getEntity();
            throw new MinecraftClientHttpException(status,
                (entity != null) ?
                    objectMapper.readValue(EntityUtils.toString(entity), ErrorResponse.class) :
                    null
            );
        }
    }
}
