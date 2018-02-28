package com.social.support;

import java.sql.SQLException;
import java.util.*;

import com.social.dao.SocialAppDao;
import com.social.dao.SocialAppDaoImpl;
import com.social.restexceptionhandler.UserDataNotFoundException;

public class FolloweeGraph {
	private Vertex[] adjLists;
	private SocialAppDao mDao = new SocialAppDaoImpl();
	private List<String> followeePerUserList = mDao.getFolloweeForEachUserDataAccess();
	private Set<String> setOfUsers = new HashSet<>();
	private Map<String, Integer> indexMapForVertex = new HashMap<>();

	public FolloweeGraph() throws SQLException, UserDataNotFoundException {
		final int weightForDirectNeighbor = 1;

		for (String str : followeePerUserList) {
			String[] pArr = str.split("->");
			setOfUsers.add(pArr[0]);
			setOfUsers.add(pArr[1]);
		}

		adjLists = new Vertex[setOfUsers.size()];
		int v = 0;

		for (String user : setOfUsers) {
			// fill the vertex array
			Vertex vertex = new Vertex(user, null);
			indexMapForVertex.put(vertex.name, v);
			adjLists[v++] = vertex;
		}

		for (String s : followeePerUserList) {
			String[] params = s.split("->");

			int v1 = indexMapForVertex.get(params[0]);
			int followee = Integer.parseInt(params[1]);

			Vertex v2 = new Vertex(String.valueOf(followee), null);

			// add v2 in front of v1 vertex
			adjLists[v1].adjList = new FolloweeNeighbor(v2, weightForDirectNeighbor, adjLists[v1].adjList);
		}
	}

	public Vertex getVertex(String vert) {
		int vertexIndex = indexMapForVertex.get(vert);
		return adjLists[vertexIndex];
	}

	public int findShortestDistanceUtil(int sourceUser, int destinationUser) throws SQLException, UserDataNotFoundException {
		FolloweeGraph graph = new FolloweeGraph();
		return findShortestDistance(graph, adjLists, sourceUser, destinationUser);
	}

	public int findShortestDistance(FolloweeGraph graph, Vertex[] adjLists, int sourceUser, int destinationUser)
			throws SQLException, UserDataNotFoundException {

		return findMinimumDistanceUsingDijkstra(graph, adjLists, graph.getVertex(String.valueOf(sourceUser)),
				graph.getVertex(String.valueOf(destinationUser)));
	}

	/*
	 * 1. Take the unvisited node with minimum weight. 2. Visit all the
	 * neighbors of this unvisited node. 3. Update the distances for all the
	 * neighbours (In the Priority Queue). Repeat the steps till all the
	 * connected nodes are visited.
	 */
	private int findMinimumDistanceUsingDijkstra(FolloweeGraph graph, Vertex[] adjLists, Vertex source,
			Vertex destination) throws SQLException, UserDataNotFoundException {

		source.minDistance = 0;
		PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();

		vertexQueue.add(source);
		int updatedMinDistanceForDestination = 0;

		while (!vertexQueue.isEmpty()) {

			Vertex currentVertex = vertexQueue.poll();
			int index = indexMapForVertex.get(currentVertex.name);

			for (FolloweeNeighbor neighbor = adjLists[index].adjList; neighbor != null; neighbor = neighbor.next) {

				int newDistanceThroughV = currentVertex.minDistance + neighbor.weight;
				if (newDistanceThroughV < neighbor.target.minDistance) {

					// remove the node from PriorityQueue to update the distance
					vertexQueue.remove(neighbor.target);

					neighbor.target.minDistance = newDistanceThroughV;

					if (neighbor.target.name.equals(destination.name)) {
						updatedMinDistanceForDestination = neighbor.target.minDistance;
					}

					vertexQueue.add(neighbor.target);
				}
			}
		}
		System.out.println("finally updatedMinDistanceForDestination from  "+ source.name + " to " + destination.name + " is : "
				+ updatedMinDistanceForDestination);
		return updatedMinDistanceForDestination;
	}

	public void print() {
		System.out.println();

		for (int v = 0; v < adjLists.length; v++) {
			System.out.print(adjLists[v].name);
			for (FolloweeNeighbor neighbor = adjLists[v].adjList; neighbor != null; neighbor = neighbor.next) {
				System.out.print(" --> " + neighbor);
			}
			System.out.println("\n");
		}
	}

}
