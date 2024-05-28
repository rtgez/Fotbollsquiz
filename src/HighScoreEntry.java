class HighScoreEntry implements Comparable<HighScoreEntry> {
    private final String name;
    private final int score;

    public HighScoreEntry(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    @Override
    public int compareTo(HighScoreEntry other) {
        return Integer.compare(other.score, this.score); // Sort in descending order
    }

    @Override
    public String toString() {
        return name + " : " + score;
    }
}

