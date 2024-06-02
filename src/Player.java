/**
 * The Player class represents a player in the quiz game.
 * It includes the player's name and score, and provides methods to manipulate these properties.
 */
public class Player {
    private String name;
    private int score;
    /**
     * Constructs a Player with the specified name and initializes the score to 0.
     *
     * @param name The name of the player.
     * @megim, lana
     */
    public Player(String name) {
        this.name = name;
        score = 0;
    }
    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     * @mergim, lana
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the score of the player.
     *
     * @return The score of the player.
     * @mergim, lana
     */
    public int getScore() {
        return score;
    }
    /**
     * Sets the name of the player.
     *
     * @param name The name to set.
     *  @author lana
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Increments the player's score by 1.
     *  @author lana
     */
    public void scorePoint() {
        score++;
    }
}