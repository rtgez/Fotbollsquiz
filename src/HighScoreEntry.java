/**
 * The HighScoreEntry class represents an entry in the high score table.
 * It includes the player's name and their score.
 * It implements the Comparable interface to allow sorting by score in descending order.
 *  @author lana
 */
class HighScoreEntry implements Comparable<HighScoreEntry> {
    private final String name;
    private final int score;
    /**
     * Constructs a HighScoreEntry with the specified name and score.
     *
     * @param name  The name of the player.
     * @param score The score of the player.
     */
    public HighScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }
    /**
     * Gets the name of the player.
     *
     * @return The name of the player.
     */
    public String getName() {
        return name;
    }
    /**
     * Gets the score of the player.
     *
     * @return The score of the player.
     */
    public int getScore() {
        return score;
    }
    /**
     * Compares this HighScoreEntry with another HighScoreEntry for order.
     * Returns a negative integer, zero, or a positive integer as this score
     * is greater than, equal to, or less than the specified score.
     *
     * @param other The other HighScoreEntry to be compared.
     * @return A negative integer, zero, or a positive integer as this score
     * is greater than, equal to, or less than the specified score.
     */
    @Override
    public int compareTo(HighScoreEntry other) {
        return Integer.compare(other.score, this.score); // Sort in descending order
    }
    /**
     * Returns a string representation of the HighScoreEntry.
     *
     * @return A string representation of the HighScoreEntry.
     */
    @Override
    public String toString() {
        return name + " : " + score;
    }
}

