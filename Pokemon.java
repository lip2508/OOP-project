/**
 * Represents a Pokémon with attributes, battle stats, and growth mechanics
 */
public class Pokemon {
    // Type constants
    public static final String FIRE = "Fire";
    public static final String WATER = "Water";
    public static final String EARTH = "Earth";
    
    private String name;
    private String type;
    private String move;  // Primary battle move (used in getStats())
    private int hp;
    private int maxHp;
    private int attack;
    private int baseAttack;
    private int defense;
    private String rarity;
    private int battlesWon;
    private int tempAttackBoost = 0;
    private int boostDuration = 0;

    /**
     * Creates a new Pokémon
     * @param name Pokémon's name
     * @param type Elemental type (Fire/Water/Earth)
     * @param move Primary battle move
     * @param maxHp Maximum hit points
     * @param attack Base attack power
     * @param defense Base defense
     * @param rarity Rarity category (Common/Uncommon/Rare)
     */
    public Pokemon(String name, String type, String move, int maxHp, int attack, int defense, String rarity) {
        this.name = name;
        this.type = type;
        this.move = move;
        this.maxHp = maxHp;
        this.hp = maxHp;
        this.baseAttack = attack;
        this.attack = attack;
        this.defense = defense;
        this.rarity = rarity;
        this.battlesWon = 0;
    }

    // Battle methods
    public void takeDamage(int damage) {
        hp = Math.max(hp - damage, 0);
    }

    public void heal(int amount) {
        hp = Math.min(hp + amount, maxHp);
    }

    public boolean isFainted() {
        return hp <= 0;
    }

    /**
     * Applies temporary attack boost
     * @param amount Boost value
     * @param duration Turns remaining
     */
    public void applyAttackBoost(int amount, int duration) {
        this.tempAttackBoost = amount;
        this.boostDuration = duration;
        this.attack = baseAttack + amount;
    }

    /**
     * Updates status effects at turn end
     */
    public void updateStatus() {
        if (boostDuration > 0) {
            boostDuration--;
            if (boostDuration == 0) {
                attack = baseAttack;
                tempAttackBoost = 0;
            }
        }
    }

    /**
     * Records battle victory and checks for evolution
     */
    public void recordVictory() {
        battlesWon++;
        if (battlesWon >= 3) {
            evolve();
        }
    }

    private void evolve() {
        System.out.println("\n" + name + " is evolving!");
        maxHp += 20;
        baseAttack += 5;
        defense += 5;
        hp = maxHp;
        System.out.println(name + "'s stats improved!");
    }

    // Getters
    public String getName() { return name; }
    public String getType() { return type; }
    public String getMove() { return move; }
    public int getHp() { return hp; }
    public int getMaxHp() { return maxHp; }
    public int getAttack() { return attack; }
    public int getBaseAttack() {return baseAttack;}
    public int getDefense() { return defense; }
    public String getRarity() { return rarity; }
    public String getDefendType() { return type; }

    /**
     * @return Formatted stats string
     */
    public String getStats() {
        return String.format("%s (%s) | HP: %d/%d | ATK: %d%s | DEF: %d | Move: %s",
            name, type, hp, maxHp,
            attack, (tempAttackBoost > 0 ? "↑+" + tempAttackBoost : ""),
            defense, move);
    }
}