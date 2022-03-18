package nil.ed.test.cache;

import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;

public class CaffeineDemo {

    public static void main(String[] args) {
        Cache<String, Object> cache = Caffeine.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .refreshAfterWrite(30, TimeUnit.SECONDS)
                .build();
        cache.put("dsddsdfds", new Object());
        cache.put("dsddsdfds", new Object());
    }

}
