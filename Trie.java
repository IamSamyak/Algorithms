class Node{
    Node links[] = new Node[26];
    boolean flag;

    public Node(){}

    boolean containsKey(char ch){
        return (links[ch - 'a'] != null);
    }

    void put(char ch, Node node){
        links[ch - 'a'] = node;
    }

    Node get(char ch){
        return links[ch - 'a'];
    }

    void setEnd(){
        flag = true;
    }

    boolean isEnd(){
        return flag;
    }
}

public class Trie {

    private static Node root;

    //Initialize your data structure here

    Trie() {
        root = new Node();
    }


    //Inserts a word into the trie

    public static void insert(String word) {
        Node node = root;
        for(char ch : word.toCharArray()){
            if(!node.containsKey(ch)){
                node.put(ch, new Node());
            }
            node = node.get(ch);
        }
        node.setEnd();
    }


    //Returns if the word is in the trie

    public static boolean search(String word) {
        Node node = root;
        for(char ch : word.toCharArray()){
            if(!node.containsKey(ch)) return false;
            node = node.get(ch);
        }
        return node.isEnd();
    }

    
    //Returns if there is any word in the trie that starts with the given prefix

    public static boolean startsWith(String prefix) {
        Node node = root;
        for(char ch : prefix.toCharArray()){
            if(!node.containsKey(ch)) return false;
            node = node.get(ch);
        }
        return true;
    }
}
