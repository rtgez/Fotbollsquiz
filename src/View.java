public class View {

    public void printQuestion(String question, QnA qna) {
        String[] answers = qna.getAnswers();

        System.out.println(question + "\n");
        System.out.println("A: " + answers[0]);
        System.out.println("B: " + answers[1]);
        System.out.println("C: " + answers[2]);
        System.out.println("D: " + answers[3]);
    }

    public void printScoreOfPlayer(Player player) {
        System.out.println(player.getName() + " has reached " + player.getScore() + " points.");
    }

    public void printNameRequest() {
        System.out.print("Your name: ");
    }

    public void printInputRequest() {
        System.out.print("Please chose a letter: ");
    }

    public void printSuccessMessage() {
        System.out.println("That was right!\n");
    }

    public void printGameOverMessage() {
        System.out.println("This was wrong. Game over.");
    }
}