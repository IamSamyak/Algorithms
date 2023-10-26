struct Node{
        Node* links[26];
        bool flag=false;
        bool containsKey(char ch){
            return (links[ch-'a']!=NULL);
        }
        Node* get(char ch){
            return links[ch-'a'];
        }
        void put(char ch){
            links[ch-'a']=new Node();
        }
        void setEnd(){
            flag = true;
        }
        bool getEnd(){
            return flag;
        }
    };
class Trie {
    private:Node* root;
public:
    
    Trie() {
        root=new Node();
    }
    
    void insert(string word) {
        Node* node = root;
        for(int i=0;i<word.size();i++){
             if(!node->containsKey(word[i])){
                 node->put(word[i]);
             }
             node = node->get(word[i]);
        }
        node->setEnd();
    }
    
    bool search(string word) {
        Node* node = root;
        for(int i=0;i<word.size();i++){
            if(node->containsKey(word[i])){
                node = node->get(word[i]);
            }else{
                return false;
            }
        }
        return node->getEnd();
    }
    
    bool startsWith(string prefix) {
        Node* node = root;
        for(int i=0;i<prefix.size();i++){
            if(node->containsKey(prefix[i])){
                node = node->get(prefix[i]);
            }else{
                return false;
            }
        }
        return true;
    }
};
