package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int sum = 0;
        for (CombatNode c : children) {
            sum += c.getHealth();
        }
        return sum;
    }

    @Override
    public int getAttackPower() {
        int sum = 0;
        for (CombatNode c : children) {
            if (c.isAlive()) {
                sum += c.getAttackPower();
            }
        }
        return sum;
    }

    @Override
    public void takeDamage(int amount) {
        List<CombatNode> alive = getAliveChildren();
        if (alive.isEmpty() || amount <= 0) {
            return;
        }
        int share = amount / alive.size();
        int remainder = amount % alive.size();
        for (int i = 0; i < alive.size(); i++) {
            int dmg = share + (i < remainder ? 1 : 0);
            alive.get(i).takeDamage(dmg);
        }
    }

    @Override
    public boolean isAlive() {
        for (CombatNode c : children) {
            if (c.isAlive()) return true;
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "+ " + name
                + " [HP=" + getHealth()
                + ", ATK=" + getAttackPower() + "]");
        for (CombatNode c : children) {
            c.printTree(indent + "  ");
        }
    }

    private List<CombatNode> getAliveChildren() {
        List<CombatNode> list = new ArrayList<>();
        for (CombatNode c : children) {
            if (c.isAlive()) {
                list.add(c);
            }
        }
        return list;
    }
}
