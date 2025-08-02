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

    public Pokemon(String name, String type, String move, int hp, int attack, int defense, String rarity) {
        this.name = name;
        this.type = type;
        this.move = move;
        this.hp = hp;
        this.maxHp = hp;
        this.attack = attack;
        this.defense = defense;
        this.rarity = rarity;
        this.defendType = type; // for simplicity, defendType matches type
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public String getType() { return type; }
    public String getMove() { return move; }
    public String getRarity() { return rarity; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public String getDefendType() { return defendType; }
    public void increaseUsage() { usageCount++; if (usageCount >= 3) evolve(); }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHp + 50); // cap at evolved max
        if (this.hp < 0) this.hp = 0;
    }

    public void takeDamage(int dmg) {
        int actualDmg = Math.max(dmg - defense, 1);
        setHp(hp - actualDmg);
        System.out.println(name + " takes " + actualDmg + " damage. Remaining HP: " + hp);
    }

    public void heal(int amount) {
        setHp(hp + amount);
        System.out.println(name + " heals " + amount + " HP. Current HP: " + hp);
    }

    public boolean isFainted() { return hp <= 0; }

    private void evolve() {
        System.out.println(name + " is evolving!");
        this.maxHp += 50;
        this.attack += 5;
        this.hp = maxHp;
    }

    public String getStats() {
        return name + " | Type: " + type + " | HP: " + hp + "/" + maxHp + " | Move: " + move + " | Rarity: " + rarity;
    }
}