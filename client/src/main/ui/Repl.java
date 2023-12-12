package ui;

import chess.*;
import connect.NotificationHandler;
import exception.ResponseException;
import webSocketMessages.serverMessages.ServerMessage;

import java.util.Scanner;

public class Repl {

    private final Client client;
    public Repl(String serverURL) throws ResponseException {
        client = new Client(serverURL, this);
    }

    public void run() {
        System.out.println("Welcome to Chess! Sign in or register to begin. Type 'help' at any time for more options.");
        System.out.print(client.help());

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("See ya! :(")) {
            printPrompt();
            String line = scanner.nextLine();

            try {
                result = client.eval(line);
                System.out.print(EscapeSequences.SET_TEXT_COLOR_BLUE + result);
            } catch (Throwable e) {
                System.out.print(e.getMessage());
            }
        }
        System.out.println();
    }

    public void notifyError(String notification) {
        System.out.println(EscapeSequences.SET_TEXT_COLOR_RED + notification);
    }

    void printPrompt() {
        System.out.print("\n" + EscapeSequences.SET_TEXT_COLOR_WHITE + ">>> " + EscapeSequences.SET_TEXT_COLOR_GREEN);
    }


}

