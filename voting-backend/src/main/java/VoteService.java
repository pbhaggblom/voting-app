import io.quarkus.runtime.StartupEvent;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.set.SetCommands;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class VoteService {

    private final SetCommands<String, String> setCommands;

    public VoteService(RedisDataSource ds) {
        this.setCommands = ds.set(String.class);
    }

    void onStart(@Observes StartupEvent ev) {
        setCommands.sadd("poll:options", "Java", "C", "C#", "Python");
    }
}