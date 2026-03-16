package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;
import java.util.List;

public class AreaSkill extends Skill {
    public AreaSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null || !target.isAlive()) return;
        applyToLeaves(target);
    }

    private void applyToLeaves(CombatNode node) {
        List<CombatNode> children = node.getChildren();
        if (children.isEmpty()) {
            // это лист — бьём напрямую
            if (node.isAlive()) {
                node.takeDamage(resolvedDamage());
            }
        } else {
            // это композит — рекурсивно идём вглубь
            for (CombatNode child : children) {
                if (child.isAlive()) {
                    applyToLeaves(child);
                }
            }
        }
    }
}