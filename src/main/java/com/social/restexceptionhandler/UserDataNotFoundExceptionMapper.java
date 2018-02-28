package com.social.restexceptionhandler;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.sun.jersey.spi.resource.Singleton;

@Provider
@Singleton
public class UserDataNotFoundExceptionMapper implements ExceptionMapper<UserDataNotFoundException> {

	@Override
	public Response toResponse(UserDataNotFoundException ude) {
		ErrorMessage errorMessage = new ErrorMessage(ude.getMessage(), 404, "");
		return Response.status(Status.NOT_FOUND).entity(errorMessage).type(MediaType.APPLICATION_JSON).build();
	}
}
