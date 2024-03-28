package http;

import serialization.Quote;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server2 {
    public static final int TCP_PORT = 8081;
    public static List<Quote> quotes = new ArrayList<>();


    public static void main(String[] args) {

        try {
            addQuotes();
            ServerSocket ss = new ServerSocket(TCP_PORT);
            while (true) {
                Socket sock = ss.accept();
                new Thread(new ServerThread2(sock)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void addQuotes() {
        quotes.add(new Quote("Dusan Colic" , "Neki tekst"));
        quotes.add(new Quote("Dusan Colic 1" , "Neki tekst 1"));
        quotes.add(new Quote("Dusan Colic 2" , "Neki tekst 2"));
        quotes.add(new Quote("Dusan Colic 3" , "Neki tekst 3"));
        quotes.add(new Quote("Dusan Colic 4" , "Neki tekst 4"));
    }
}
