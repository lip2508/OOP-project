import java.util.Random;

/**
 * Represents weather conditions that affect battles
 */
public class Weather {
    public static final String SUNNY = "Sunny";
    public static final String RAINY = "Rainy"; 
    public static final String HAIL = "Hail";
    public static final String SANDSTORM = "Sandstorm";
    public static final String CLEAR = "Clear";
    
    private String type;
    private static final Random random = new Random();
    
    public Weather(String type) {
        this.type = type;
    }
    
    /**
     * @return Random weather condition
     */
    public static Weather randomWeather() {
        String[] types = {SUNNY, RAINY, HAIL, SANDSTORM, CLEAR};
        return new Weather(types[random.nextInt(types.length)]);
    }
    
    /**
     * @return Description of weather effects
     */
    public String getEffects() {
        switch(type) {
            case SUNNY: return "Boosts Fire-type moves";
            case RAINY: return "Boosts Water-type moves";
            case HAIL: return "Damages non-Water types";
            case SANDSTORM: return "Boosts Earth-type moves";
            default: return "No special effects";
        }
    }
    
    public String getType() { return type; }
}