package com.assignment.demo.helper;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SortHelper {

  private SortHelper() {
    throw new IllegalStateException("Utility class");
  }

  public static Sort of(Direction direction, String[] properties) {
    if (isPropertiesValid(properties)) {
      return Sort.by(direction, properties);
    } else {
      String[] defaultProperties = {"id"};
      return Sort.by(direction, defaultProperties);
    }
  }

  private static boolean isPropertiesValid(String[] properties) {
    return properties != null && properties.length > 0;
  }

}