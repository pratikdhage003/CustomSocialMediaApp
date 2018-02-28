package com.social.restcontroller;

import java.sql.SQLException;
import java.util.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.social.pojo.Message;
import com.social.pojo.User;
import com.social.service.SocialAppServiceImpl;

import com.social.restexceptionhandler.UserDataNotFoundException;

/*
 * contextPath
 * http://localhost:8080/CustomSocialMediaApp/API
 * 
 * */
@Path("/app/v1.1")
public class SocialAppController {
	
	SocialAppServiceImpl appServiceImpl = new SocialAppServiceImpl();

	/*
	 * list of messages posted by userId=1 and its followees; followees are :=
	 * whom userId=1 is actually following
	 * 
	 * userId = 1 follows userId = 4, meanwhile userId = 4 has posted something
	 * which contains a buzzword "Renovation"
	 * 
	 * API/app/v1.1/messages/users/1? messageContains=Renovat
	 * 
	 */
	@GET
	@Path("messages/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> findAllMessegesOfUserWithFolloweesByUserId(@PathParam("id") int userId,
			@QueryParam("messageContains") String message_content) throws SQLException, UserDataNotFoundException {


		List<Message> messegeListOfUserWithFollowees = null;

		if (message_content == null) {
			message_content = "";
		}
		messegeListOfUserWithFollowees = appServiceImpl.getNewsFeedService(userId, message_content);

		if (messegeListOfUserWithFollowees.isEmpty())
			throw new UserDataNotFoundException(
					"News feed for id " + userId + "not found, either it does exist, wrong messagecontent");

		return messegeListOfUserWithFollowees;
	}

	/*
	 * list of followers of an user whose userId = 5;
	 * API/app/v1.1/followers/users/5
	 * 
	 */
	@GET
	@Path("followers/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAllFollowersByUserId(@PathParam("id") int userId)
			throws SQLException, UserDataNotFoundException {
		
		List<User> listOfFollowers = null;

		listOfFollowers = appServiceImpl.getListOfFollowersService(userId);

		if (listOfFollowers.isEmpty())
			throw new UserDataNotFoundException("User status not found with id " + userId);

		return listOfFollowers;
	}

	/*
	 * list of followees of an user whose userId = 2; (to whom userId= 2 is
	 * actually following)
	 * 
	 * API/app/v1.1/followees/users/2
	 * 
	 */
	
	@GET
	@Path("followees/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> findAllFolloweesByUserId(@PathParam("id") int userId)
			throws SQLException, UserDataNotFoundException {
		
		List<User> listOfFollowees = null;

		listOfFollowees = appServiceImpl.getListOfFolloweesService(userId);
		if (listOfFollowees.isEmpty())
			throw new UserDataNotFoundException(
					"Could not load any list of followees as the user id : " + userId + " does exist:");

		for (User user : listOfFollowees) {
			System.out.println(user);
		}
		return listOfFollowees;
	}

	/*
	 * userId=4 decides to unfollow another user (followee) whose followeeId=1;
	 * 
	 * API/app/v1.1/users/unfollow?userId=4&followeeId=1
	 * 
	 */
	@GET
	@Path("users/unfollow")
	@Produces(MediaType.APPLICATION_JSON)
	public void unfollowAnotherPerson(@QueryParam("userId") int userId, @QueryParam("followeeId") int followeeId)
			throws SQLException, UserDataNotFoundException {

		appServiceImpl.unfollowAPersonService(userId, followeeId);
	}

	/*
	 * userId=4 decides to follow another user (followee) with followeeId=1;
	 * 
	 * API/app/v1.1/users/follow?userId=4&followeeId=1
	 */
	@GET
	@Path("users/follow")
	@Produces(MediaType.APPLICATION_JSON)
	public void followAnotherPerson(@QueryParam("userId") int userId, @QueryParam("followeeId") int followeeId)
			throws SQLException, UserDataNotFoundException {
		appServiceImpl.followAPersonService(userId, followeeId);
	}

	/*
	 * shortest distance between two users using Dijkstra's algorithm uses
	 * adjacency list, graph of vertices and edges.
	 * 
	 * API/app/v1.1/users/shortestdistance?sourceId=3&destinationId=6
	 * 
	 */
	@GET
	@Path("users/shortestdistance")
	@Produces(MediaType.APPLICATION_JSON)
	public int findShortestDistanceBetweenUsers(@QueryParam("sourceId") int sourceId,
			@QueryParam("destinationId") int destinationId) throws SQLException, UserDataNotFoundException {

		int shortestDistance = appServiceImpl.findShortestDistanceBetweenUsersService(sourceId, destinationId);
		return shortestDistance;
	}

}