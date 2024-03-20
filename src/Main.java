public class Main {
    public static void main(String[] args) {
        QnA[] questionList = {
                new QnA("In which country Kaiser Wilhelm II was born?",
                        new String[] {
                                "America",
                                "Germany",
                                "North Korea",
                                "England"
                        }, 'B'),

                new QnA("Which flowers are the most beautiful?",
                        new String[] {
                                "Tulips",
                                "Roses",
                                "Lilies",
                                "Weeping willows"
                        }, 'C'),

                new QnA("Where does England live?",
                        new String[] {
                                "On an island",
                                "Near poland",
                                "In the white house",
                                "In he yellow house"
                        }, 'A'),

                new QnA("Who's a free software activist?",
                        new String[] {
                                "Bill Gates",
                                "Donald Trump",
                                "Richard Stallman",
                                "The GNU operating system"
                        }, 'C'),

                new QnA("Which MMORPG has the most players?",
                        new String[] {
                                "Arthoria.de",
                                "Nostale",
                                "GTA 5",
                                "World of Warcraft"
                        }, 'D')
        };

        // problem: the players name can not be initialized with functions from controller
        // before the controller is initialized
        Player player = new Player("");
        QnA questions = new QnA(questionList);
        View view = new View();


        Controller controller = new Controller(player, questionList, view,null);
        controller.mainLoop();
    }
}