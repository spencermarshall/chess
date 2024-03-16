package client;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Repl  {
    private final ChessClient client;

    public Repl(String serverUrl) {
        client = new ChessClient(serverUrl);
    }

    public void run() {
        System.out.println("\uD83D\uDC36 Welcome to Chess. Hope you're ready to play :). Type help to get started");
       // System.out.print(client.help());

        Scanner scanboi = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")) {
            printPrompt();
            String line = scanboi.nextLine();

            try {
                result = client.eval(line);
                System.out.print(result);
            } catch (Throwable e) {
                var msg = e.toString();
                System.out.print(msg);
            }
        }
        System.out.println();
    }

   /* public void notify(Notification notification) {
        System.out.println(RED + notification.message());
        printPrompt();
    }*/

    private void printPrompt() {
        System.out.print("processing...");

    }

}