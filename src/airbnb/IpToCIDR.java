package airbnb;

import java.util.*;

public class IpToCIDR {

  /**
   * 关键在于计算两个掩码：
   * 1）能够cover最右边1的mask, 例如最右边1在从右到左第3位，则mask为32 - 2 = 30；
   * 2）能够cover range的mask，即（end - start + 1)这个区间需要多少位cover：32 - Math.floor(Math.log(end - start + 1) / Math.log(2))
   * CIDR的掩码为这两个mask的较大值，然后将start + Math.pow(2, mask)，对新的start计算新一轮的掩码，知道start > end
   * @param ip
   * @param range
   * @return
   */
  public List<String> smallestCIDRToCoverAllIp(String ip, int range) {
    List<String> res = new ArrayList<>();
    long start = ipToLong(ip);
    long end = start + range - 1;
    while(start <= end) {
      long firstOnePos = start & (-start);
      int maskForFirstOne = 32 - (int)(Math.log(firstOnePos) / Math.log(2));
      double currentRange = Math.log(end - start + 1) / Math.log(2);
      int maskForRange = 32 - (int)Math.floor(currentRange);
      int mask = Math.max(maskForFirstOne, maskForRange);
      String cidr = String.join("/", longToIp(start), String.valueOf(mask));
      res.add(cidr);
      start += (Math.pow(2, 32 - mask));
    }
    return res;
  }

  private String longToIp(long val) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.valueOf(val >>> 24)).append(".");
    sb.append(String.valueOf(0x000000ff & (val >>> 16))).append(".");
    sb.append(String.valueOf(0x000000ff & (val >>> 8))).append(".");
    sb.append(String.valueOf(0x000000ff & val));
    return sb.toString();
  }

  private long ipToLong(String ip) {
    String[] segments = ip.split("\\.");
    long[] longs = new long[segments.length];
    for(int i = 0; i < segments.length; i++) {
      longs[i] = Long.valueOf(segments[i]);
    }
    return (longs[0] << 24) + (longs[1] << 16) + (longs[2] << 8) + longs[3];
  }
}
