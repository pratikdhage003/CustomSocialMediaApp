package com.social.support;

public class FolloweeNeighbor {
	public final Vertex target;
	public final int weight;
	public FolloweeNeighbor next;

	public FolloweeNeighbor(Vertex v, int wt, FolloweeNeighbor nbr) {
		this.target = v;
		this.weight = wt;
		next = nbr;
	}

	@Override
	public String toString() {
		return "FolloweeNeighbor [target=" + target + ", weight=" + weight + "]";
	}
}
