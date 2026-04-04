import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

import java.util.Map;

@Path("/vote")
public class VoteResource {

    @Inject
    ResultSocket socket;

    @Inject
    VoteService voteService;

    @POST
    @Path("/{option}")
    public void vote(@PathParam("option") String option) {
        long count = voteService.vote(option);
        socket.broadcast("{\"option\": \"" + option + "\", \"total\": " + count + "}");
    }

    @POST
    @Path("/random")
    public void voteRandom() {
        String option = voteService.getRandomOption();
        long count = voteService.vote(option);
        socket.broadcast("{\"option\": \"" + option + "\", \"total\": " + count + "}");
    }

    @GET
    @Path("/results")
    public Map<String, Integer> getResults() {
        return voteService.getAllVotes();
    }
}
