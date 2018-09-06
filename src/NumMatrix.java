class NumMatrix {
  int[][] sums;

  public NumMatrix(int[][] matrix) {
    int m = matrix.length;
    int n = matrix[0].length;
    sums = new int[m][n];
    sumUp(matrix, m, n);
  }

  public int sumRegion(int row1, int col1, int row2, int col2) {
    int upToNow = sums[row2][col2];
    int up = sums[row1-1][col2];
    int left = sums[row2][col1-1];
    return upToNow - up - left + (row1-1 >= 0 && col1-1 >= 0? sums[row1-1][col1-1] : 0);
  }

  private void sumUp(int[][] matrix, int m, int n) {
    for(int i = 0; i < m; i++) {
      int rowSum = 0;
      for(int j = 0; j < n; i++) {
        rowSum += matrix[i][j];
        int lastRowSums = (i == 0? 0 : sums[i-1][j]);
        sums[i][j] = rowSum + lastRowSums;
      }
    }
  }
}