// It calculates the z array of z function in which i'th index stores maximum prefix matching
void calculateZ(string &s,vector<int>&z){
        int left = 0,right = 0;
        int n = s.size();
        for(int i=1;i<s.size();i++){
            if(i>right){
                // this is not part of prefix so calculate for it
                left = right = i;
                while(right < n && s[right-left] == s[right]){
                    right++;
                }
                z[i] = right - left;
                right--;
            }else{
                //It is inside z window
                int k = i - left;
                if(z[i] < right - left + 1){
                    z[i] = z[k];
                }else{
                    //if it is inside window and matching value exceeds window
                    left = i;
                    while(right < n && s[right-left] == s[right]){
                    right++;
                    }
                    z[i] = right - left;
                    right--;
                }
            }
        }
    }
