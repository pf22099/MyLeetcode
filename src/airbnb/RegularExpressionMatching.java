package airbnb;

public class RegularExpressionMatching {

  /**
   * Support ".", "*", "+"
   * if s.charAt(i) == p.charAt(j): dp[i][j] = dp[i-1][j-1]
   * if p.charAt(j) == '.': dp[i][j] = dp[i-1][j-1]
   * if p.charAt(j) == '+': dp[i][j] =
   *                                   dp[i][j-1] // a+ counts as a single a
   *                               OR  dp[i-1][j] // a+ counts as multiple a
   * if p.charAt(j) == '*':
   * Here are two conditions:
   *        1) if s.charAt(i) != p.charAt(j) && p.charAt(j) != '.'
   *           dp[i][j] = dp[i-1][j-2] // a* counts as empty
   *        2) else
   *           dp[i][j] = dp[i][j-1] // a* counts as a single a
   *                   OR dp[i-1][j] // a* counts as multiple a
   *                   OR dp[i-1][j-2] // a* counts as empty
   * @param s
   * @param p
   * @return
   */
  public boolean isMatch(String s, String p) {
    boolean[][] dp = new boolean[s.length()+1][p.length()+1];
    dp[0][0] = true;
    for(int i = 0; i < p.length(); i++) {
      if(p.charAt(i) == '*' && dp[0][i-1]) {
        dp[0][i+1] = true;
      }
    }
    for(int i = 0; i < s.length(); i++) {
      for(int j = 0; j < p.length(); j++) {
        char sc = s.charAt(i);
        char pc = p.charAt(j);
        if(sc == pc) {
          dp[i+1][j+1] = dp[i][j];
        } else if(pc == '.') {
          dp[i+1][j+1] = dp[i][j];
        } else if(pc == '*') {
          if(p.charAt(j-1) != '.' && p.charAt(j-1) != s.charAt(i)) {
            dp[i+1][j+1] = dp[i+1][j-1];
          } else {
            dp[i+1][j+1] = (dp[i+1][j] || dp[i+1][j-1] || dp[i][j+1]);
          }
        } else if(pc == '+') {
          if(p.charAt(j-1) == '.' || p.charAt(j-1) == s.charAt(i)) {
            dp[i+1][j+1] = (dp[i][j+1] || dp[i+1][j]);
          }
        }
      }
    }
    return dp[s.length()][p.length()];
  }
}
