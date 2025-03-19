// https://www.youtube.com/watch?v=4iz9JOkF3nk
#include<bits/stdc++.h>
using namespace std;

int * Z_algo(string s,int n){
    int *z=new int[n];
    int l=0,r=0;

    for(int i=1;i<n;i++){
        //condition 1
        if(i>r){
            l=i;
            r=i;
            while(r<n and s[r-l]==s[r]){
                r++;
            }
            r--;
            z[i]=r-l+1;

        }
        //condition 2
        else{
            int j=i-l;

            //condition 2a
            if(z[j]<r-i+1){
                z[i]=z[j];
            }
            //condition 2b
            else{
                l=i;
                while(r<n and s[r-l]==s[r]){
                    r++;
                }
                r--;
                z[i]=r-l+1;
            }
        }
    }
    return z;
}

int main(){
    string s="abc$abcdabcfabdabe";
    int n=s.size();
    int *z=Z_algo(s,n);
    for(int i=1;i<n;i++){
        cout<<z[i]<<" ";
    }
    cout<<endl;
    return 0;
}
