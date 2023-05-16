package moe.caramel.infiltration;

import com.mojang.authlib.exceptions.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Supplier;

/**
 * 유틸리티
 */
public final class Util {

    private static final Logger LOGGER = LoggerFactory.getLogger("Infiltration");

    /**
     * 최대 재연결 가능 횟수
     */
    private static final int MAX_RETRY_COUNT = 10;

    /**
     * 작업에 오류가 발생 시 최대 {@link #MAX_RETRY_COUNT}번 다시 실행합니다.
     *
     * @param task 수행할 작업
     * @return 작업이 성공하였을 때 리턴 값
     * @param <T> 리턴 타입
     */
    public static <T> T loop(final Supplier<T> task) {
        for (int cnt = 1; cnt < Util.MAX_RETRY_COUNT; cnt++) {
            try {
                return task.get();
            } catch (Exception ignored) {
                LOGGER.warn("연결에 오류가 발생하여 다시 연결합니다. ({}/{})", cnt, MAX_RETRY_COUNT);
            }
        }

        try {
            return task.get();
        } catch (Exception exception) {
            LOGGER.error("연결에 오류가 지속적으로 발생하여 연결을 포기했습니다. :rofl:");
            throw exception;
        }
    }

    @FunctionalInterface
    public interface SupplierAuthService<T> {

        T get() throws AuthenticationException;
    }

    /**
     * 작업에 오류가 발생 시 최대 {@link #MAX_RETRY_COUNT}번 다시 실행합니다.
     *
     * @param task 수행할 작업
     * @return 작업이 성공하였을 때 리턴 값
     * @param <T> 리턴 타입
     */
    public static <T> T loopAuth(final SupplierAuthService<T> task) throws AuthenticationException {
        for (int cnt = 1; cnt < Util.MAX_RETRY_COUNT; cnt++) {
            try {
                return task.get();
            } catch (AuthenticationException ignored) {
                LOGGER.warn("연결에 오류가 발생하여 다시 연결합니다. ({}/{})", cnt, MAX_RETRY_COUNT);
            }
        }

        try {
            return task.get();
        } catch (AuthenticationException exception) {
            LOGGER.error("연결에 오류가 지속적으로 발생하여 연결을 포기했습니다. :rofl:");
            throw exception;
        }
    }
}
