package main;

/**
 * Utility class for finding a best match to a word.
 * @author Brian Harris (brian_bcharris_net)
 */
class DYMNode implements Comparable<DYMNode> {
	TrieNode node;
	String word;
	int distance;
	boolean wordExists;
	
	DYMNode(TrieNode node, int distance, String word, boolean wordExists) {
		this.node = node;
		this.distance = distance;
		this.word = word;
		this.wordExists = wordExists;
	}
	
	public int compareTo(DYMNode n) {
		if (distance == n.distance)
			return n.node.occurances - node.occurances;
		return distance - n.distance;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		
		if (o == null)
			return false;
		
		if (!(o instanceof DYMNode))
			return false;
		
		DYMNode n = (DYMNode)o;
		return distance == n.distance && n.node.occurances == node.occurances;
	}

	@Override
	public int hashCode()
	{
		int hash = 31;
		hash += distance * 31;
		hash += node.occurances * 31;
		return hash;
	}

	@Override
	public String toString() {
		return word+":"+distance;
	}
}
