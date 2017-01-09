import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Diana on 05/12/2016.
 */
public class Main {
    private static Scanner input = new Scanner(System.in);
    private static Controller controller = new Controller();

    private static void printMenu() {
        System.out.println("1 - Read grammar (from file)");
        System.out.println("2 - Elimination of single productions (renaming rules)");

        int selection = input.nextInt();

        switch(selection) {
            case 1:
                controller.readGrammarFromFile();
                System.out.println("Grammar read successfully!");
                printMenu();
                break;
            case 2:
                Grammar newGrammar = controller.buildGrammarWithoutSingleProductions();
                System.out.println(newGrammar.getSetOfNonterminals().toString());
                System.out.println(newGrammar.getSetOfTerminals().toString());
                for (Map.Entry e : newGrammar.getProductions().entrySet()) {
                    String rights = " ";
                    for (int i=0; i < ((List<String>)e.getValue()).size(); i++) {
                        if (i == ((List<String>)e.getValue()).size() - 1) {
                            rights += ((List<String>)e.getValue()).get(i);
                        }
                        else {
                            rights += ((List<String>)e.getValue()).get(i) + " | ";
                        }
                    }
                    System.out.println(e.getKey() + "->" + rights);
                }
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    public static void main(String[] args) {
        printMenu();
    }
}
