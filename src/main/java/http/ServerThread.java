package http;

import app.RequestHandler;
import com.google.gson.Gson;
import http.response.Response;
import serialization.Quote;

import java.io.*;
import java.net.Socket;

import java.util.StringTokenizer;

public class ServerThread implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private BufferedReader in2;
    private PrintWriter out2;
    private Socket socket;


    public ServerThread(Socket sock) {
        this.client = sock;
        try {
            socket = new Socket("localhost", 8081);
            //inicijalizacija ulaznog toka
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            //inicijalizacija izlaznog sistema
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            // uzimamo samo prvu liniju zahteva, iz koje dobijamo HTTP method i putanju
            String requestLine = in.readLine();

            StringTokenizer stringTokenizer = new StringTokenizer(requestLine);

            String method = stringTokenizer.nextToken();
            String path = stringTokenizer.nextToken();

            System.out.println("\nHTTP ZAHTEV KLIJENTA:\n");
            do {
                System.out.println(requestLine);
                requestLine = in.readLine();
            } while (!requestLine.trim().equals(""));

            if (method.equals(HttpMethod.POST.toString())) {
//                 TODO: Ako je request method POST, procitaj telo zahteva (parametre)
                char[] buffer = new char[512];
                in.read(buffer);
                String s  = new String(buffer);
                String author = s.split("&")[0].split("=")[1];
                String quote = s.split("&")[1].split("=")[1];
                author = author.replace("+", " ");
                quote = quote.replace("+", " ");
                Server.quotes.add(new Quote(author, quote));

            }

            out2 = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
            in2 = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out2.println("quote of the day");
            String responseLine = in2.readLine();
            Gson gson = new Gson();
            Quote quote = gson.fromJson(responseLine, Quote.class);
            System.out.println(responseLine);
            System.out.println(quote);


            Request request = new Request(HttpMethod.valueOf(method), path);

            RequestHandler requestHandler = new RequestHandler();
            Response response = requestHandler.handle(request);

            String responseString = response.getResponseString();
            responseString = responseString.replace("<label>Quotes: </label><br>", "<label>Quote of the day:<br>"+"<p>" + quote.getAuthor() + ": \""+ quote.getText() +"\"</p>" + "</label><br> <label>Quotes: </label><br>");
            System.out.println("\nHTTP odgovor:\n");
            System.out.println(response.getResponseString());

            out.println(responseString);

            in.close();
            out.close();
            client.close();
            in2.close();
            out2.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
