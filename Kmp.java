// https://www.youtube.com/watch?v=Gr-eKRuWIQc&t=670s
static int[] constructLps(String s){
        int n = s.length();
        int lps[] = new int[n];
        for(int i = 1; i < n; i++){
            int prev_idx = lps[i - 1];
            while(prev_idx > 0 && s.charAt(i) != s.charAt(prev_idx)){
                prev_idx = lps[prev_idx - 1];
            }
            lps[i] = prev_idx + (s.charAt(i) == s.charAt(prev_idx) ? 1 : 0);
        }
        return lps;
}
