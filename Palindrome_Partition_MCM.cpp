    bool ispalindrome(int i,int j,string &str){
        while(i<j){
            if(str[i] != str[j]) return false;
            i++;
            j--;
        }
        return true;
    }

    int f(int ind,int n,string &str,vector<int>&dp){
        if(ind == n){ return 0;}
        if(dp[ind]!=-1){ return dp[ind];}
        int mini = INT_MAX;
        for(int i=ind;i<n;i++){
            if(ispalindrome(ind,i,str)){
                int cost = 1 + f(i+1,n,str,dp);
                mini = min(mini,cost);
            }
        }
        return dp[ind] = mini;
    }
    
    int palindromicPartition(string str)
    {
        // code here
        int n=str.size();
        vector<int>dp(n,-1);
        return f(0,n,str,dp)-1;
    }
