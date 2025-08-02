public class Pokemon {
    private String name;
    private String type;
    private String move;
    private String defendType;
    private String rarity;
    private int hp;
    private int maxHp;
    private int attack;
    private int defense;
    private int usageCount = 0;

    // Constructor to initialize all attributes
    public Pokemon(String name, String type, String move, int hp, int attack, int defense, String rarity) {
        this.name = name;
        this.type = type;
        this.move = move;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.rarity = rarity;
        this.defendType = type; // For simplicity, defendType matches the Pokémon's type
    }

    // Basic accessors
    public String getName() { return name; }
    public int getHp() { return hp; }
    public String getType() { return type; }
    public String getMove() { return move; }
    public String getRarity() { return rarity; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public String getDefendType() { return defendType; }

    // Used to track battle usage and evolve the Pokémon after 3 uses
    public void increaseUsage() {
        usageCount++;
        if (usageCount >= 3) evolve();
    }

    // Cap HP between 0 and max
    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp + 50); // Limit to evolved max HP
        if (this.hp < 0) this.hp = 0;
    }

    // Apply damage considering defense
    public void takeDamage(int dmg) {
        int actualDmg = Math.max(dmg - defense, 1);
        setHp(hp - actualDmg);
        System.out.println(name + " takes " + actualDmg + " damage. Remaining HP: " + hp);
    }

    // Heal the Pokémon
    public void heal(int amount) {
        setHp(hp + amount);
        System.out.println(name + " heals " + amount + " HP. Current HP: " + hp);
    }

    // Check if Pokémon fainted
    public boolean isFainted() {
        return hp <= 0;
    }

    // Stat buff when Pokémon evolves
    private void evolve() {
        System.out.println(name + " is evolving!");
        this.maxHp += 50;
        this.attack += 5;
        this.hp = maxHp; // Heal to full on evolution
    }

    // Return stat summary
    public String getStats() {
        return name + " | Type: " + type + " | HP: " + hp + "/" + maxHp + " | Move: " + move + " | Rarity: " + rarity;
    }
}