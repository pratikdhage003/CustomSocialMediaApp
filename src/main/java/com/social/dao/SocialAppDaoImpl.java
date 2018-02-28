package com.social.dao;

import java.util.*;
import com.social.pojo.Message;
import com.social.pojo.User;
import com.social.restexceptionhandler.UserDataNotFoundException;

import java.sql.*;

public class SocialAppDaoImpl implements SocialAppDao {

	public SocialAppDaoImpl() {

	}

	private Connection connect() throws Exception {
		// SQLite connection string
		String sDbName = "SocialAppDB.sqlite";
		String sAbsolutePath = "/Users/pratikdhage/Documents/workspace/CustomSocialMediaApp/" + sDbName;
		String sJdbc = "jdbc:sqlite";
		String sDbUrl = sJdbc + ":" + sAbsolutePath;
		Connection connection = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(sDbUrl);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return connection;
	}

	@Override
	public List<User> getAllUsersDataAcess() throws Exception {

		String query = "SELECT uid, fname, lname, screen_name FROM users";

		List<User> userList = new ArrayList<User>();

		try (Connection connection = this.connect();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {

			while (rs.next()) {
				User user = new User();
				user.setUid(rs.getInt("uid"));
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setScreen_name(rs.getString("screen_name"));
				userList.add(user);
			}

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return userList;
	}

	@Override
	public Map<Integer, User> getUserInfoDataAcess() throws SQLException, UserDataNotFoundException {

		Map<Integer, User> userMap = new HashMap<>();

		String query = "SELECT uid, fname, lname, screen_name FROM users";

		try (Connection connection = this.connect();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {

			while (rs.next()) {
				User user = new User();

				user.setUid(rs.getInt("uid"));
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setScreen_name(rs.getString("screen_name"));

				userMap.put(rs.getInt("uid"), user);
			}

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return userMap;
	}

	@Override
	public void insertUserDataAcess(User user) throws Exception {

		int uid = user.getUid();
		String fname = user.getFname();
		String lname = user.getLname();
		String screen_name = user.getScreen_name();

		String query = "INSERT INTO users(uid, fname, lname, screen_name) VALUES(?,?,?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, uid);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, screen_name);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public List<User> getListOfFolloweesDataAcess(int uid) throws SQLException, UserDataNotFoundException {

		List<User> followsList = new ArrayList<>();

		String query = "SELECT users.uid, users.fname, users.lname, users.screen_name FROM users GROUP BY users.uid HAVING users.uid IN (SELECT follows.followee_id FROM follows JOIN users ON follows.followee_id = users.uid WHERE follows.user_id = ?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query);) {

			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();

				user.setUid(rs.getInt("uid"));
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setScreen_name(rs.getString("screen_name"));

				followsList.add(user);
			}

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return followsList;
	}

	@Override
	public List<User> getListOfFollowersDataAcess(int uid) throws SQLException, UserDataNotFoundException {

		List<User> followersList = new ArrayList<>();

		String query = "SELECT users.uid, users.fname, users.lname, users.screen_name FROM users GROUP BY users.uid HAVING users.uid IN (SELECT follows.user_id FROM follows JOIN users ON follows.followee_id = users.uid WHERE follows.followee_id = ?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query);) {

			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				User user = new User();

				user.setUid(rs.getInt("uid"));
				user.setFname(rs.getString("fname"));
				user.setLname(rs.getString("lname"));
				user.setScreen_name(rs.getString("screen_name"));

				followersList.add(user);
			}

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return followersList;
	}

	public int findAUser(int uid) throws SQLException, UserDataNotFoundException {
		int id = 0;
		String query = "SELECT users.uid FROM users WHERE users.uid = ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query);) {

			pstmt.setInt(1, uid);
			ResultSet rs = pstmt.executeQuery();

			if (!rs.isBeforeFirst()) {
				throw new UserDataNotFoundException("Could not find any user with Id : " + uid);
			}

			id = rs.getInt("uid");

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return id;
	}

	@Override
	public void followAPersonDataAcess(int userId, int followeeId) throws SQLException, UserDataNotFoundException {

		int user_id = findAUser(userId);
		int followee_id = findAUser(followeeId);

		String query = "INSERT INTO follows (user_id, followee_id) VALUES(?,?)";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, user_id);
			pstmt.setInt(2, followee_id);
			pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void unfollowAPersonDataAcess(int userId, int followeeId) throws SQLException, UserDataNotFoundException {

		int user_id = findAUser(userId);
		int followee_id = findAUser(followeeId);

		String query = "DELETE FROM follows WHERE user_id = ? AND followee_id = ?";

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {

			pstmt.setInt(1, user_id);
			pstmt.setInt(2, followee_id);
			pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	@Override
	public List<Message> getNewsFeedDataAccess(int user_id, String message_content)
			throws SQLException, UserDataNotFoundException {

		if (message_content != null) {
			message_content = message_content.replace("!", "!!").replace("%", "!%").replace("_", "!_").replace("[",
					"![");
		}

		String query = "SELECT * FROM (SELECT  follows.user_id, messages.message_id, messages.message_content, messages.posted_at FROM messages JOIN follows ON messages.user_id = follows.user_id WHERE follows.user_id = ? UNION SELECT  follows.followee_id, messages.message_id, messages.message_content, messages.posted_at FROM messages JOIN follows ON messages.user_id = follows.followee_id WHERE follows.user_id = ?) AS T1 WHERE (T1.message_content LIKE ? ESCAPE '!') ORDER BY T1.posted_at DESC";

		List<Message> messageList = new ArrayList<Message>();

		try (Connection conn = this.connect(); PreparedStatement pstmt = conn.prepareStatement(query);) {

			pstmt.setInt(1, user_id);
			pstmt.setInt(2, user_id);
			pstmt.setString(3, "%" + message_content + "%");

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Message message = new Message();

				message.setMessage_id(rs.getInt("message_id"));
				message.setMessage_content(rs.getString("message_content"));
				message.setPosted_at(rs.getString("posted_at"));
				message.setUser_id(rs.getInt("user_id"));

				messageList.add(message);
			}

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return messageList;
	}

	@Override
	public List<String> getFolloweeForEachUserDataAccess() throws SQLException, UserDataNotFoundException {

		List<String> followeePerUserList = new LinkedList<>();

		String query = "SELECT follows.user_id, follows.followee_id FROM follows";

		try (Connection connection = this.connect();
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(query);) {

			while (rs.next()) {

				String user = rs.getString("user_id");
				String followee = rs.getString("followee_id");

				followeePerUserList.add(user + "->" + followee);

			}

		} catch (Exception e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}

		return followeePerUserList;
	}

}
