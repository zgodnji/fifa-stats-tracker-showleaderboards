package com.zgodnji.fifastatstracker;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@RequestScoped
@Path("showleasderboards")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ShowLeaderboardResource {

    @GET
    @Path("{gameId}")
    public Response showLeaderboard(@PathParam("gameId") String gameId) {
        return Response.noContent().build();
    }
}
