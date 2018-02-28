package com.social.dao;

import com.social.pojo.Message;
import com.social.pojo.User;
import com.social.restexceptionhandler.UserDataNotFoundException;

import java.sql.SQLException;
import java.util.*;

public interface SocialAppDao {
	public List<User> getAllUsersDataAcess() throws Exception;

	public Map<Integer, User> getUserInfoDataAcess() throws SQLException, UserDataNotFoundException;

	public void insertUserDataAcess(User user) throws Exception;

	public List<User> getListOfFolloweesDataAcess(int uid) throws SQLException, UserDataNotFoundException;

	public List<User> getListOfFollowersDataAcess(int uid) throws SQLException, UserDataNotFoundException;

	public void followAPersonDataAcess(int user_id, int followee_id) throws SQLException, UserDataNotFoundException;

	public void unfollowAPersonDataAcess(int user_id, int followee_id) throws SQLException, UserDataNotFoundException;

	public List<Message> getNewsFeedDataAccess(int user_id, String message_content) throws SQLException, UserDataNotFoundException;

	public List<String> getFolloweeForEachUserDataAccess() throws SQLException, UserDataNotFoundException;

}
