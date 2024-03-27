import java.util.Scanner;

public class Controller {
    private Player player;
    private QnA[] questions;
    private QnA question;
    private Scanner input;

    private View view;

    public Controller(Player player, QnA[] questions, View view, QnA question) {
        this.player = player;
        this.questions = questions;
        input = new Scanner(System.in);
        this.question = question;
        this.view = view;
    }

    public String getString() {
        return input.next();
    }

    public String getGuessOfPlayer() {
        String guess = input.next();
        if(!guess.equalsIgnoreCase("A") && !guess.equalsIgnoreCase("B")
                && !guess.equalsIgnoreCase("C") && !guess.equalsIgnoreCase("D")) {
            throw new IllegalArgumentException("Enter A, B, C or D");
        }
        return guess;
    }

    public void mainLoop() {
        view.printNameRequest();
        player.setName(getString());

        while(true) {
            String actualQuestion = question.getRandomQuestion();

            view.printQuestion(actualQuestion,null);
            view.printInputRequest();
            String input = getGuessOfPlayer();
            if(question.check(input)) {
                player.scorePoint();
                view.printSuccessMessage();
            } else {
                view.printGameOverMessage();
                view.printScoreOfPlayer(player);
                break;
            }
        }
    }
}