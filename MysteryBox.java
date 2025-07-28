// Randomly gives an item (HealPotion or AttackBoost) when opened.

import java.util.*;

public class MysteryBox {
    private List<Item> items;

    public MysteryBox() {
        items = new ArrayList<>();
        items.add(new HealPotion(30));
        items.add(new AttackBoost(10));
    }

    // Randomly select one item from the list
    public Item open() {
        Random rand = new Random();
        Item selected = items.get(rand.nextInt(items.size()));
        System.out.println("🎁 You received a " + selected.getName() + "!");
        return selected;
    }
}