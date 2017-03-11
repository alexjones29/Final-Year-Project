package main;

import java.util.PriorityQueue;

public class Trie {

		// dummy node for root of trie
		final TrieNode root;
		
		// current number of unique words in trie
		private int size;
		
		// if this is a case sensitive trie
		protected boolean caseSensitive;
		
		/**
		 * @param caseSensitive If this Trie should be case-insensitive to the words it encounters. 
		 * Case-insensitivity is accomplished by converting String arguments to all functions to 
		 * lower-case before proceeding.
		 */
		public Trie(boolean caseSensitive) {
			root = new TrieNode((char)0);
			size = 0;
			this.caseSensitive = caseSensitive;
		}
		
		public int insert(String word) {
			if (word == null)
				return 0;
			
			int i = root.insert(caseSensitive ? word : word.toLowerCase(), 0);
			
			if (i == 1)
				size++;
			
			return i;
		}
		
		public boolean remove(String word) {
			if (word == null)
				return false;
			
			if (root.remove(caseSensitive ? word : word.toLowerCase(), 0)) {
				size--;
				return true;
			}
			
			return false;
		}
		
		public int frequency(String word) {
			if (word == null)
				return 0;
			
			TrieNode n = root.lookup(caseSensitive ? word : word.toLowerCase(), 0);
			return n == null ? 0 : n.occurances;
		}
		
		public boolean containsWord(String word)
		{
			if (word == null)
				return false;
			
			return root.lookupWord(caseSensitive ? word : word.toLowerCase(), 0) != null;			
		}
		
		public boolean contains(String word) {
			if (word == null)
				return false;
			
			return root.lookup(caseSensitive ? word : word.toLowerCase(), 0) != null;
		}
		
		public int size() {
			return size;
		}
		
		@Override
		public String toString() {
			return root.toString();
		}

		public String bestMatch(String word, long max_time) {
			
			if (!caseSensitive)
				word = word.toLowerCase();
			
			// we store candidate nodes in a pqueue in an attempt to find the optimal
			// match ASAP which can be useful for a necessary early exit
			PriorityQueue<DYMNode> pq = new PriorityQueue<DYMNode>();
		
			DYMNode best = new DYMNode(root, Distance.LD("", word), "", false);		
			DYMNode cur = best;
			TrieNode tmp;
			int count=0;
			long start_time = System.currentTimeMillis();
			
			while (true) {
				if (count++ % 1000 == 0 && (System.currentTimeMillis() - start_time) > max_time)
					break;
					
				if (cur.node.children != null) {
					for (char c : cur.node.children.keySet()) {
						tmp = cur.node.children.get(c);
						String tWord = cur.word + c;
						int distance = Distance.LD(tWord, word);
						
						// only add possibly better matches to the pqueue
						if (distance <= cur.distance) {
							if (tmp.occurances == 0)
								pq.add(new DYMNode(tmp, distance, tWord, false));
							else
								pq.add(new DYMNode(tmp, distance, tWord, true));
						}
					}
				}
				
				DYMNode n = pq.poll();
				
				if (n == null)
					break;
				
				cur = n;
				if (n.wordExists)
					// if n is more optimal, set it as best
					if (n.distance < best.distance || (n.distance == best.distance && n.node.occurances > best.node.occurances))
						best = n;
			}
			return best.word;
		}
}