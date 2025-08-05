import java.util.Scanner;
import java.util.Random;

public class MysteryBox extends Item {
    private enum RewardType {
        ITEM, COINS, STAT_BUFF
    }

    private final Random random = new Random();

    public MysteryBox() {
        super("Mystery Box");
    }

    @Override
    public void applyEffect(Player player, Scanner scanner) {
        System.out.println("\nOpening Mystery Box...");
        System.out.println("You received:");

        // Give 3 random rewards
        for (int i = 0; i < 3; i++) {
            giveRandomReward(player, scanner);
            System.out.println("------------------");
        }
    }

    private void giveRandomReward(Player player, Scanner scanner) {
        RewardType rewardType = RewardType.values()[random.nextInt(RewardType.values().length)];

        switch (rewardType) {
            case ITEM:
                rewardItem(player);
                break;
            case COINS:
                rewardCoins(player);
                break;
            case STAT_BUFF:
                rewardStatBuff(player, scanner);
                break;
        }
    }

    private void rewardItem(Player player) {
        Item[] possibleItems = {
            new HealthPotion(30),
            new AttackBoost(15, 3),
            new Pokeball("Poké"),
            new Pokeball("Great"),
            new Pokeball("Ultra")
        };

        Item reward = possibleItems[random.nextInt(possibleItems.length)];
        player.addItem(reward);
        System.out.println("- " + reward.getName());
    }

    private void rewardCoins(Player player) {
        int coins = 20 + random.nextInt(50);
        player.addCoins(coins);
        System.out.println("- " + coins + " coins");
    }

    private void rewardStatBuff(Player player, Scanner scanner) {
        if (random.nextBoolean()) {
            int hpIncrease = 10 + random.nextInt(20);
            System.out.println("- HP Boost (+" + hpIncrease + " HP)");
            
            Pokemon target = player.selectPokemon(scanner);
            if (target != null) {
                target.heal(hpIncrease);
            }
        } else {
            System.out.println("- XP Boost (+1 battle win)");
            
            Pokemon target = player.selectPokemon(scanner);
            if (target != null) {
                target.recordVictory();
            }
        }
    }

    public static void awardRandom(Player player, Scanner scanner) {
        MysteryBox box = new MysteryBox();
        System.out.println("\nYou received a free Mystery Box!");
        box.applyEffect(player, scanner);
    }
}