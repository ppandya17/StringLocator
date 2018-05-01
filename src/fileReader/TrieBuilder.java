package fileReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Scanner;

public class TrieBuilder {
	
	// to store main Trie starting point
	static HashMap<Character, Node> root = new HashMap<Character, Node>();

	// Class to every node of trie
	class Node{
		Node parent;
		boolean isEndofWord = false;
		HashMap<Character, Node> children = new HashMap<Character, Node>();
	}	
	
	// For Checking if it exist in Trie or not
	public void insert(String word){
		if(!root.containsKey(word.charAt(0))){
			root.put(word.charAt(0), new Node());
		}
		
		insertTrie(word.substring(1), root.get(word.charAt(0)));
//		System.out.println(root);
//		printTrie(word.substring(1), root.get(word.charAt(0)));
	}
	
	// To insert in Trie
	public void insertTrie(String word, Node node){
		Node nextNode = null;
		
		if(node.children.containsKey(word.charAt(0))){
			nextNode = node.children.get(word.charAt(0));
		} else {
			node.children.put(word.charAt(0), new Node());
			nextNode = node.children.get(word.charAt(0));
		}
		
		if(word.length() == 1){
			nextNode.isEndofWord = true;
		//	node.children.put(word.charAt(0), nextNode);
		} else {
			insertTrie(word.substring(1), nextNode);
		}
		
	}
	
	// For searching the word in root
	public static void searchWord(String word){
		
		System.out.print("Entire Word Search: ");
		if(!root.containsKey(word.charAt(0))){
			System.out.println(false);
			return;
		} 
		
		searchTrie(word.substring(1), root.get(word.charAt(0)));
	}
	
	
	// For Searching the word in Trie
	private static void searchTrie(String word, Node node){
		Node nextnode;
	
		if(!node.children.containsKey(word.charAt(0))){
			System.out.println(false);
			return;
		}
	
		nextnode = node.children.get(word.charAt(0));
	
		if(word.length() == 1 ){
			if(nextnode.isEndofWord){
				System.out.println(true);	
			} else {
				System.out.println(false);
			}
		
		} else {
			searchTrie(word.substring(1), nextnode);
		}
	}

	// For Searching Prefix in Root node
	public static void prefix(String word){
		System.out.println();
		System.out.print("Prefix Search: ");
		if(!root.containsKey(word.charAt(0))){
			System.out.println("No words start with: "+ word);
			return;
		} 
	
		prefixTrie(word ,word.substring(1), root.get(word.charAt(0)));
	}

	// For Searching Prefix in entire Trie
	public static void prefixTrie(String parentWord, String word, Node node){
	
		Node nextnode;
	
		if(!node.children.containsKey(word.charAt(0))){
			System.out.println("No words start with: "+ parentWord);
			return;
		}
	
		nextnode = node.children.get(word.charAt(0));
	
		if(word.length() == 1){
			System.out.println("There is some words that starts with: "+ parentWord);
		} else {
			prefixTrie(parentWord, word.substring(1), nextnode);
		}
	}

	// To print Trie
	public void printTrie(String word, Node node){
	
		Node nextNode;
	
		if(node.children.containsKey(word.charAt(0))){
			System.out.println(word.charAt(0));
			nextNode = node.children.get(word.charAt(0));
		} else {
			System.out.println("The charactor '"+word.charAt(0)+"' is not here!!");
			return;
		}
	
		if(word.length() == 1 && nextNode.isEndofWord){
			System.out.println("End of Word");
		} else {
			printTrie(word.substring(1), nextNode);
		}
	}
	
	// Main method to run the trie
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		//TrieDictionary tdict = new TrieDictionary();
		TrieBuilder tdict = new TrieBuilder(); 
		
		try{
    	
    		StreamTokenizer tokenizer= new StreamTokenizer(
    				new BufferedReader(
    						new InputStreamReader(
    								TrieBuilder.class.getResourceAsStream("dict.txt"), "UTF-8")));
    		
    		tokenizer.lowerCaseMode(true); 
//    		int count = 0;
    		while(tokenizer.nextToken()!=tokenizer.TT_EOF)  {
    			if(tokenizer.ttype==tokenizer.TT_WORD) {
//    				count++;
    				String word=tokenizer.sval; // get the next word
//    				if(word.equals("prenegotiation")) {
//    					System.out.println("");
//    				}
    				tdict.insert(word);
//    				if(count == 20){
//    					System.exit(0);
//    				}
    			}
    		}
    	} catch(IOException e) {
    			System.out.println(e);             
    			System.exit(1);                    
    	}	
    	
		System.out.println("check values frlom file");
    	
    	Scanner sc = new Scanner( 
    			new BufferedReader(
    					new InputStreamReader(
    							TrieBuilder.class.getResourceAsStream("hw5.txt"), "UTF-8")));
    	
    	sc.nextLine();
    	while(sc.hasNext()){
    		String lookup = sc.nextLine().trim().replaceAll("\\s+","").toLowerCase();
    		prefix(lookup);
    		searchWord(lookup);
    	}
    	sc.close();
	}

}
