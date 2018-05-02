package com.afikur.resource;

import com.afikur.dao.UserDAO;
import com.afikur.model.User;
import org.hibernate.engine.spi.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Path("/users")
public class UserResource {
    private final UserDAO userDAO;

    @Autowired
    public UserResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GET
    public Response getAllUsers() {
        List<User> users = userDAO.getAll();
        return Response.status(Response.Status.OK).entity(new GenericEntity<List<User>>(users) {}).build();
    }

    @GET
    @Path("{id}")
    public Response getUser(@PathParam("id") int id) {
        User user = userDAO.get(id);
        if(user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @POST
    public Response createUser(User user) {
        userDAO.insertNew(user);
        return Response.status(Response.Status.CREATED).entity(user).build();
    }


    @PUT
    @Path("{id}")
    public Response updateUser(@PathParam("id") int id, User user) {
        user.setId(id);
        userDAO.update(user);
        return Response.status(Response.Status.OK).entity(user).build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteUser(@PathParam("id") int userId) {
        User user = new User();
        user.setId(userId);
        userDAO.remove(user);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
