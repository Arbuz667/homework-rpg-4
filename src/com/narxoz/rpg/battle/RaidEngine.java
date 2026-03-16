package com.narxoz.rpg.battle;

import com.narxoz.rpg.bridge.Skill;
import com.narxoz.rpg.composite.CombatNode;

import java.util.Random;

public class RaidEngine {
    private Random random = new Random(1L);

    public RaidEngine setRandomSeed(long seed) {
        this.random = new Random(seed);
        return this;
    }

    public RaidResult runRaid(CombatNode teamA, CombatNode teamB,
                              Skill skillA, Skill skillB) {

        RaidResult result = new RaidResult();

        if (teamA == null || teamB == null || skillA == null || skillB == null) {
            result.addLine("Invalid inputs provided.");
            result.setWinner("Invalid");
            return result;
        }

        int rounds = 0;

        while (teamA.isAlive() && teamB.isAlive()) {
            rounds++;

            result.addLine("=== Round " + rounds + " ===");

            // TEAM A attacks
            if (teamA.isAlive()) {
                result.addLine("Team A uses " + skillA.getSkillName()
                        + " (" + skillA.getEffectName() + ")");
                skillA.cast(teamB);
                result.addLine("Team B HP now: " + teamB.getHealth());
            }

            // TEAM B attacks
            if (teamB.isAlive()) {
                result.addLine("Team B uses " + skillB.getSkillName()
                        + " (" + skillB.getEffectName() + ")");
                skillB.cast(teamA);
                result.addLine("Team A HP now: " + teamA.getHealth());
            }
        }

        result.setRounds(rounds);
        result.setWinner(teamA.isAlive() ? "Team A" : "Team B");
        result.addLine("Winner: " + result.getWinner());

        return result;
    }
}
