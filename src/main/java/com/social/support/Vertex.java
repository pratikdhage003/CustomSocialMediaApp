package com.social.support;

public class Vertex implements Comparable<Vertex> {
	public final String name;
	public FolloweeNeighbor adjList;

	public int minDistance = Integer.MAX_VALUE;

	public Vertex(String name, FolloweeNeighbor neighbors) {
		this.name = name;
		this.adjList = neighbors;
	}

	@Override
	public int compareTo(Vertex other) {
		return Integer.compare(minDistance, other.minDistance);
	}

	@Override
	public String toString() {
		return "Vertex [name=" + name + "]";
	}

}
