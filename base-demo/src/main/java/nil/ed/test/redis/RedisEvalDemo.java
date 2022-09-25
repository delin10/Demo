package nil.ed.test.redis;

import org.apache.commons.codec.binary.Hex;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author lidelin.
 */
public class RedisEvalDemo {
    public static void main(String[] args) {
        JedisPool jedisPool = new JedisPool("localhost", 6379);
        try (Jedis jedis = jedisPool.getResource()) {
            String sha = jedis.scriptLoad("local val = redis.call(\"get\", KEYS[1])\n" +
                    "if ((val == ARGV[1]) or (val == false)) then\n" +
                    "    redis.call(\"setex\", KEYS[1], tonumber(ARGV[3]), ARGV[2])\n" +
                    "    return 1\n" +
                    "else\n" +
                    "    return val\n" +
                    "end");
            System.out.println(jedis.evalsha(sha, Collections.singletonList("test"), Arrays.asList("0", "nil", "10000")));
        }

        System.out.println(Integer.toHexString(Integer.MAX_VALUE - 8));
    }
}
