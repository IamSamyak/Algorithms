#include <bits/stdc++.h>
using namespace std;

vector<int> zMatch(string s) {
	vector<int> z(s.size(), 0);
	int l = 0, r = 0;
	for (int i = 1; i < s.size(); i++) {
		if (i < r) {
			z[i] = z[i - l];
			// Handle case of z[i] reaching for characters 
			// beyond what we have seen at r
			if (i + z[i] > r) {
				z[i] = r - i;
			}
		}
		
		// Look for more batch beyond the current calculation
		while(i + z[i] < s.size() && s[z[i]] == s[i + z[i]]) z[i]++;
		
		// Update l, r
		if (i + z[i] > r) {
			l = i;
			r = i + z[i];
		}
	}
	return z;
}


int main() {
	vector<int> z = zMatch("aba$ababbababaaa");
	for (auto it: z) cout << it << " ";
	cout << endl;
	return 0;
}
