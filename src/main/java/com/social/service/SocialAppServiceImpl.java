package com.social.service;

import java.sql.SQLException;
import java.util.*;

import com.social.dao.SocialAppDao;
import com.social.dao.SocialAppDaoImpl;
import com.social.pojo.Message;
import com.social.pojo.User;
import com.social.restexceptionhandler.UserDataNotFoundException;
import com.social.support.FolloweeGraph;

public class SocialAppServiceImpl {

	SocialAppDao mDao = new SocialAppDaoImpl();

	public SocialAppServiceImpl() {

	}

	public List<User> getListOfFolloweesService(int uid) throws SQLException, UserDataNotFoundException {
		return mDao.getListOfFolloweesDataAcess(uid);
	}

	public List<User> getListOfFollowersService(int uid) throws SQLException, UserDataNotFoundException {
		 List<User> listOfFollowers = mDao.getListOfFollowersDataAcess(uid);
		 if(listOfFollowers.isEmpty()){
			 throw new UserDataNotFoundException("Sorry the user does not exist, so can not display its followers !");
		 }
		return mDao.getListOfFollowersDataAcess(uid);
	}

	public void followAPersonService(int user_id, int followee_id) throws SQLException, UserDataNotFoundException {
		mDao.followAPersonDataAcess(user_id, followee_id);
	}

	public void unfollowAPersonService(int user_id, int followee_id) throws SQLException, UserDataNotFoundException {
		mDao.unfollowAPersonDataAcess(user_id, followee_id);
	}

	public List<Message> getNewsFeedService(int user_id, String message_content) throws SQLException, UserDataNotFoundException {
		return mDao.getNewsFeedDataAccess(user_id, message_content);
	}

	public int findShortestDistanceBetweenUsersService(int sourceUser, int destinationUser) throws SQLException, UserDataNotFoundException {
		
		FolloweeGraph graph = new FolloweeGraph();
		graph.print();
		
		int minDistance = graph.findShortestDistanceUtil( sourceUser, destinationUser);
		return minDistance;
	}

}
