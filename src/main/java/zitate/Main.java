package zitate;

import java.util.Scanner;

public class Main {

    static MySQL mySQL = new MySQL();
    public static String input = "";
    public static void resetInput() {
        input = "";
    }
    public static void main(String[] args) {

        mySQL.init_DatabaseAndTable();

        Scanner sc= new Scanner(System.in);


        while (!input.equalsIgnoreCase("exit")) {

            System.out.println("\nWillkommen in der Zitate-Datenbank!");
            System.out.println("Geben Sie eine der folgenden Optionen ein:");
            System.out.println("\t'add' zum Hinzufügen eines Zitats\n" +
                    "\t'list' zum Auflisten aller Zitate\n" +
                    "\t'reverse' zum Umkehren eines Zitats\n" +
                    "\t'exit' zum Beenden des Programms\n");
            System.out.print("-> ");

            input = sc.nextLine();

            switch(input.toLowerCase()){
                case "add": {
                    System.out.println("\nSie haben 'add' eingegeben. Sie können jetzt ein Zitat hinzufügen.");
                    System.out.println("Bitte geben Sie das Zitat ein:");
                    System.out.print("-> ");
                    String text = sc.next();
                    System.out.println("Bitte geben Sie die Person ein:");
                    System.out.print("-> ");
                    String person = sc.nextLine();

                    System.out.println();
                    mySQL.addQuote(text, person);
                    System.out.println("Zitat hinzugefügt.\n");
                    resetInput();
                }
                    break;
                case "list": {
                    System.out.println("\nSie haben 'list' eingegeben. Alle Zitate werden aufgelistet.");
                    mySQL.listAllQuotes();
                    resetInput();
                }
                    break;
                case "reverse": {
                    System.out.println("\nBitte geben Sie ein Teil des Zitates ein, das Sie umkehren möchten:\n");
                    System.out.print("-> ");
                    System.out.println();
                    String quote = sc.nextLine();
                    mySQL.reverseQuote(quote);
                    resetInput();
                }
                    break;
                case "exit": {
                    Display.artDisplay();
                }
                    break;
                default: {
                    System.out.println("\n\tUnbekannte Eingabe. Bitte erneut eingeben.\n");
                    break;
                }
            }

        }
    }


}
