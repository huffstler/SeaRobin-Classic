package org.searobin.server;

public class Constants {
  public static final int LURE_POINTS_PER_INCH = 200;

  // Over 24 inches is 300 per inch (200 + 100)
  public static final int TROPHY_GAME_FISH_POINT_PER_INCH = 100;
  public static final int RECORD_BREAKER_POINTS_PER_INCH = 100;
  public static final int SEA_ROBIN_POINTS_PER_INCH = 500;

  // < 18 inches
  public static final int GAME_TIER_SMALL = 120;
  // >= 18 inches
  public static final int GAME_TIER_LARGE = 200;

  // < 13 inches
  public static final int SPORT_TIER_SMALL = 50;
  // >= 13 inches & < 18 inches
  public static final int SPORT_TIER_MEDIUM = 100;
  // >= 18 inches
  public static final int SPORT_TIER_LARGE = 120;

  // < 12 inches
  public static final int TRASH_TIER_SMALL = 20;
  // >= 12 inches & < 17 inches
  public static final int TRASH_TIER_MEDIUM = 30;
  // >= 17 inches
  public static final int TRASH_TIER_LARGE = 50;

}
