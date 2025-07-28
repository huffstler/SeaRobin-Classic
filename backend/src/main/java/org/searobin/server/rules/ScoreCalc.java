package org.searobin.server.rules;

import org.searobin.server.catches.Catch;
import org.searobin.server.fish.Fish;
import org.searobin.server.tier.Tier;
import org.springframework.stereotype.Component;

import java.util.function.Function;

/**
 * score should be "itemized"
 * angler should have a score for each catch they make, as well as a total score
 * Essentially, emulate an excel sheet for the final scoreboard
 *
 * Indicates this should operate on and return Map's.
 * That's essentially what a scoreboard is anyway
 */
@Component
public class ScoreCalc {

	// private static final Function<Catch, Float> categoryRule = (Catch haul) -> {
	// Fish species = haul.getFish();
	// Tier tier = species.getTier();
	// return Tier.getTier(species).getScorePerInch() * haul.getLength();
	// };
	// private List<Rule> ruleList;

	// public void init(){
	//
	// initialize the ruleList
	//
	// }

}
