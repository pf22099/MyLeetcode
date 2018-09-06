public class RateLimiter {
  private long capacity;
  private double availableTokens;
  private double generatedTokensPerMilis;
  private long lastTimestamp;

  public RateLimiter(long capacity, int perSecondRate) {
    this.capacity = capacity;
    this.generatedTokensPerMilis = perSecondRate / 1000.00;
    availableTokens = capacity;
    lastTimestamp = System.currentTimeMillis();
  }

  synchronized public boolean acquire(int numberTokens) {
    refill();
    if(availableTokens < numberTokens) {
      return false;
    }
    availableTokens -= numberTokens;
    return true;
  }

  private void refill() {
    long current = System.currentTimeMillis();
    if(current > lastTimestamp) {
      availableTokens += (current - lastTimestamp) * generatedTokensPerMilis;
      availableTokens = Math.min(capacity, availableTokens);
      System.out.println(String.format("diff=%d, available=%f", current - lastTimestamp, availableTokens));
      lastTimestamp = current;
    }
  }
}
