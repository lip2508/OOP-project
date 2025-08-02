/**
 * Represents a location with weather effects and native Pokémon
 */
public class Place {
    private String name;
    private Weather weather;

    public Place(String name, Weather weather) {
        this.name = name;
        this.weather = weather;
    }

    public String getName() { return name; }
    public Weather getWeather() { return weather; }
    public void setWeather(Weather weather) { this.weather = weather; }

    /**
     * @return A Pokémon native to this location with complete stats
     */
    public Pokemon generateNativePokemon() {
        switch (name.toLowerCase()) {
            case "volcano":
                return new Pokemon("Magmar", Pokemon.FIRE, "Lava Plume", 110, 45, 40, "Uncommon");
            case "ocean":
                return new Pokemon("Gyarados", Pokemon.WATER, "Aqua Tail", 120, 50, 35, "Uncommon");
            case "tundra":
                return new Pokemon("Glalie", Pokemon.WATER, "Ice Fang", 105, 48, 42, "Uncommon");
            case "desert":
                return new Pokemon("Sandslash", Pokemon.EARTH, "Earthquake", 115, 52, 45, "Uncommon");
            default: // Meadow
                return new Pokemon("Fearow", Pokemon.EARTH, "Drill Peck", 100, 40, 38, "Common");
        }
    }

    public void enter(Pokemon pokemon) {
        System.out.println("\n=== " + pokemon.getName() + " enters " + name + " ===");
        System.out.println("Weather: " + weather.getType());
        System.out.println("Effect: " + weather.getEffects());
    }
}