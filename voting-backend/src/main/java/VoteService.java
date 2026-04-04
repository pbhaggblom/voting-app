import io.quarkus.redis.datasource.value.ValueCommands;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.set.SetCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class VoteService {

    private final SetCommands<String, String> setCommands;
    private final ValueCommands<String, Integer> valueCommands;

    private static final String OPTIONS_KEY = "poll:options";
    private static final String VOTE_PREFIX = "votes:";

    public VoteService(RedisDataSource ds) {
        this.setCommands = ds.set(String.class);
        this.valueCommands = ds.value(Integer.class);
    }

    void onStart(@Observes StartupEvent ev) {
        setCommands.sadd(OPTIONS_KEY,  "Java", "C", "Go", "Python");
    }

    public long vote(String option) {
        if (setCommands.sismember(OPTIONS_KEY, option)) {
            return valueCommands.incr(VOTE_PREFIX + option);
        } else {
            throw new IllegalArgumentException("Ogiltigt alternativ: " + option);
        }
    }

    public String getRandomOption() {
        return setCommands.srandmember(OPTIONS_KEY);
    }

    public Map<String, Integer> getAllVotes() {
        Set<String> options = setCommands.smembers(OPTIONS_KEY);
        Map<String, Integer> results = new HashMap<>();

        for (String option : options) {
            Integer count = valueCommands.get(VOTE_PREFIX + option);
            results.put(option, count != null ? count : 0);
        }
        return results;
    }
}