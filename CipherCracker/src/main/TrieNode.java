package main;

import java.util.HashMap;
import java.util.Map;

class TrieNode {
	char c;
	int occurances;
	Map<Character, TrieNode> children;
	
	TrieNode(char c) {
		this.c = c;
		occurances = 0;
		children = null;
	}
	
	int insert(String s, int pos) {
		if (s == null || pos >= s.length())
			return 0;
		
		// allocate on demand
		if (children == null)
			children = new HashMap<Character, TrieNode>();

		char c = s.charAt(pos);
		TrieNode n = children.get(c);

		// make sure we have a child with char c
		if (n == null) {
			n = new TrieNode(c);
			children.put(c, n);
		}
		
		// if we are the last node in the sequence of chars
		// that make up the string
		if (pos == s.length()-1) {
			n.occurances++;
			return n.occurances;
		} else
			return n.insert(s, pos+1);
	}
	
	boolean remove(String s, int pos) {
		if (children == null || s == null)
			return false;
		
		char c = s.charAt(pos);
		TrieNode n = children.get(c);

		if (n == null)
			return false;

		boolean ret;
		if (pos == s.length()-1) {
			int before = n.occurances;
			n.occurances = 0;
			ret = before > 0;
		} else {
			ret = n.remove(s, pos+1);
		}
		
		// if we just removed a leaf, prune upwards
		if (n.children == null && n.occurances == 0) {
			children.remove(n.c);
			if (children.size() == 0)
				children = null;
		}
		
		return ret;
	}
	
	TrieNode lookup(String s, int pos) {
		if (s == null)
			return null;
		
		if (pos >= s.length() || children == null)
			return null;
		else if (pos == s.length()-1)
			return children.get(s.charAt(pos));
		else {
			TrieNode n = children.get(s.charAt(pos)); 
			return n == null ? null : n.lookup(s, pos+1);
		}
	}
	
	TrieNode lookupWord(String s, int pos) {
		if (s == null)
			return null;
		
		else if (pos == s.length()-1)
			//change so if no children then its a word
			if (children.get(s.charAt(pos)) == null)
			{
				//return true
				TrieNode n = children.get(s.charAt(pos-1));
				return n;
			}
			else
			{
				return null;
				
			}
		else {
			TrieNode n = children.get(s.charAt(pos)); 
			return n == null ? null : n.lookup(s, pos+1);
		}
	}

	// debugging facility
	private void dump(StringBuilder sb, String prefix) {
		sb.append(prefix+c+"("+occurances+")"+"\n");
		
		if (children == null)
			return;
		
		for (TrieNode n : children.values())
			n.dump(sb, prefix+(c==0?"":c));
	}
 
}
