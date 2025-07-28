package org.searobin.server.rules;

import org.searobin.server.catches.Catch;
// import org.searobin.server.fish.Fish;
import org.searobin.server.tier.Tier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

import static org.searobin.server.Constants.*;

@Component
public class Rule {

    /**
     * Different categories of fish are worth different amounts of points per inch.
     */
    private static final Function<Catch, Float> categoryRule = (Catch haul) -> {
        Tier tier = haul.getFish().getTier();
        float fishLength = haul.getFishLength();
        // can be improved. Later
        float pointsPerInch = switch (tier.getName()) {
            case "GAME" -> fishLength < 18 ? GAME_TIER_SMALL : GAME_TIER_LARGE;
            case "SPORT" -> fishLength < 13 ? SPORT_TIER_SMALL : fishLength < 18 ? SPORT_TIER_MEDIUM : SPORT_TIER_LARGE;
            case "TRASH" -> fishLength < 12 ? TRASH_TIER_SMALL : fishLength < 17 ? TRASH_TIER_MEDIUM : TRASH_TIER_LARGE;
            case "SEA ROBIN" -> SEA_ROBIN_POINTS_PER_INCH;
            default -> 0;
        };
        return pointsPerInch * fishLength;
    };

    /**
     * If a fish is caught using a lure, a bonus is added for each inch in how long
     * it is.
     */
    private static final Function<Catch, Float> lureRule = (Catch haul) -> haul.isUsedLure() ? haul.getFishLength() * LURE_POINTS_PER_INCH : 0;

    /**
     * If game fish caught is over 24 inches, you get an extra 100 points per inch
     */
    private static final Function<Catch, Float> trophyFishRule = (Catch c) -> switch (c.getFish().getTier().getName()) {
        case "GAME", "SPORT" -> c.getFishLength() > 24 ? c.getFishLength() * TROPHY_GAME_FISH_POINT_PER_INCH : 0;
        default -> 0F;
    };
    //
    // // Extra 1,000 points to a fisherman who catches the largest game AND trash
    // fish
    // // private static final Function<Angler> fullMontyRule = (Angler fisherman)
    // -> {
    // // }
    //
    // // later on gonna use a database call for this one
    // // private static final Function<Catch, Float> recordCatchRule = (Catch haul)
    // -> {
    // // return haul.isRecordCatch ? haul.getLength() * 25 : 0;
    // // };
    //
    /**
     * More docs to come, assumptions were off a bit
     */
    private static final Function<Catch, Float> sizeBonusRule = (Catch haul) -> switch (haul.getFish().getTier().getName()) {
        case "GAME" -> Math.max(5 * (haul.getFishLength() - 10), 0);
        case "SPORT" -> Math.max(2 * (haul.getFishLength() - 10), 0);
        default -> Math.max(1 * (haul.getFishLength() - 10), 0);
    };

    private static final List<Function<Catch, Float>> STATIC_RULES = List.of(categoryRule, lureRule, sizeBonusRule, trophyFishRule);
    //
    private static final List<Function<Catch, Float>> DYNAMIC_RULES = List.of(
            // , recordCatchRule
            // fullMontyRule
    );

    //
    // // Thanks to whispersilk for helping collect my thoughts on how to do scoring
    // :)
    // //
    // https://tildes.net/~comp/14pv/what_programming_technical_projects_have_you_been_working_on#comment-7rnd
    public float getPreScore(Catch haul) {
        return STATIC_RULES.stream().map(r -> r.apply(haul)).reduce(0f, Float::sum);
    }
    //
    // public float getPostScore(Catch haul){
    // return DYNAMIC_RULES.stream()
    // .map(r -> r.apply(haul))
    // .reduce(0f, Float::sum);
    // }

}
