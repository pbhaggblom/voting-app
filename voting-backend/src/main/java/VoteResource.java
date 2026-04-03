import io.quarkus.redis.datasource.RedisDataSource;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@Path("/vote")
public class VoteResource {

    @Inject
    RedisDataSource ds;

    @Inject
    ResultSocket socket;

    @POST
    @Path("/{option}")
    public void vote(@PathParam("option") String option) {
        long count = ds.value(Long.class).incr("votes:" + option);
        socket.broadcast("{\"option\": \"" + option + "\", \"total\": " + count + "}");
    }
}
