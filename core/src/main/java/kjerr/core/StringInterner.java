package kjerr.core;

import java.util.HashMap;
import java.util.Map;

/**
 * All strings should be interned for performance reasons
 * To allow for separate namespaces and not disturb the VM-s string-pool
 * we do not use String.intern() for this, but keep our own pool.
 * <p>
 * The integer-value of a string is referred to as code.
 */
public class StringInterner {

  Map<String, Integer> forwardMap;
  Map<Integer, String> reverseMap;

  public int getCount() {
    return count;
  }

  protected int count = 0;

  /**
   * Construct StringInterner with default options
   */
  public StringInterner() {
    forwardMap = new HashMap<>(256);
    reverseMap = new HashMap<>(256);
  }

  /**
   * Interns a string
   *
   * @param s String to be interned
   * @return A code representing the string.
   */
  public int intern(String s) {
    if (forwardMap.containsKey(s))
      return forwardMap.get(s);
    count++;
    forwardMap.put(s, count);
    reverseMap.put(count, s);

    return count;
  }

  /**
   * Encode a given string. Returns 0 when no code was found
   *
   * @param s String to be encoded
   * @return Code for s if s has been interned before, 0 otherwise
   */
  public int encode(String s) {
    if (forwardMap.containsKey(s))
      return forwardMap.get(s);
    return 0;
  }

  /**
   * Returns the string-representation of a given code
   *
   * @param i code representing a string
   * @return String for code i
   */
  public String decode(int i) {
    return reverseMap.get(i);
  }
}
