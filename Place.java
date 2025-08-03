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
                return new Pokemon("Magmar", Pokemon.FIRE, "Lava Plume", 110, 65, 50, "Uncommon");
            case "ocean":
                return new Pokemon("Gyarados", Pokemon.WATER, "Aqua Tail", 120, 60, 45, "Uncommon");
            case "tundra":
                return new Pokemon("Glalie", Pokemon.WATER, "Ice Fang", 105, 58, 52, "Uncommon");
            case "desert":
                return new Pokemon("Sandslash", Pokemon.EARTH, "Earthquake", 115, 62, 65, "Uncommon");
            default: // Meadow
                return new Pokemon("Fearow", Pokemon.EARTH, "Drill Peck", 100, 60, 52, "Common");
        }
    }

    public void enter(Pokemon pokemon) {
        System.out.println("\n=== " + pokemon.getName() + " enters " + name + " ===");
        System.out.println("Weather: " + weather.getType());
        System.out.println("Effect: " + weather.getEffects());
    }
}