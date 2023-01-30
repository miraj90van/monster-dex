package com.assignment.demo.service;

import com.assignment.demo.helper.JsonHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReactiveRedisService {

    private final ReactiveRedisOperations<String, Object> redisOperations;

    public <T> Mono<Boolean> createCache(String key, T value, Duration timeout) {
        return redisOperations.opsForValue().set(key, value, timeout);
    }

    public <T> Mono<T> getCache(String key, Class<T> clazz) {
        return Mono.defer(() -> Optional.of(redisOperations.opsForValue().get(key))
                        .map(Mono::just)
                        .orElseGet(Mono::empty))
                .elapsed()
                .map(tupleData -> JsonHelper.objectMapper(tupleData.getT2(), clazz))
                .onErrorResume(throwable -> {
                    log.error("getCache() - failed get this key={}. with this error={}", key, throwable);
                    return Mono.empty();
                });
    }

    public Mono<Boolean> isExist(String key) {
        return redisOperations.opsForValue().get(key)
                .filter(Objects::nonNull)
                .flatMap(o -> Mono.just(true))
                .switchIfEmpty(Mono.defer(() -> Mono.just(false)));
    }

    /**
     * Delete a key that contained in a hash key.
     * @param key key value - must not be null.
     * @return 1 Success or 0 Error
     */
    public Mono<Boolean> deleteCache(String key) {
        return Objects.requireNonNull(redisOperations.delete(key))
                .map(aLong -> aLong.intValue() == 1);
    }

    /**
     * Set key and value into a hash key
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @param val Object value
     * @return Mono of object
     */
    public Mono<Object> set(String key, String hashKey, Object val) {
        return redisOperations.opsForHash().put(key, hashKey, val).map(b -> val);
    }
    /**
     * @param key key value - must not be null.
     * @return Flux of Object
     */
    public Flux<Object> get(String key){
        return redisOperations.opsForHash().values(key);
    }

    /**
     * Get value for given hashKey from hash at key.
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @return Object
     */
    public Mono<Object> get(String key, Object hashKey) {
        return redisOperations.opsForHash().get(key, hashKey);
    }

    /**
     * Delete a key that contained in a hash key.
     * @param key key value - must not be null.
     * @param hashKey hash key value -  must not be null.
     * @return 1 Success or 0 Error
     */
    public Mono<Long> remove(String key, Object hashKey) {
        return redisOperations.opsForHash().remove(key, hashKey);
    }
}
