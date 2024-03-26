package http;

import app.RequestHandler;
import com.google.gson.Gson;
import http.response.Response;
import serialization.Quote;

import java.io.*;
import java.net.Socket;
import java.util.StringTokenizer;

public class ServerThread2 implements Runnable{
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;


    public ServerThread2(Socket sock) {
        this.client = sock;

        try {
            //inicijalizacija ulaznog toka
            in = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));

            //inicijalizacija izlaznog sistema
            out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    client.getOutputStream())), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        try {
            // uzimamo samo prvu liniju zahteva, iz koje dobijamo HTTP method i putanju
            String received = in.readLine();
            if(received.equals("quote of the day"))
            {
                Request request = new Request(HttpMethod.GET, "/quote-of-the-day");
                Response response = (new RequestHandler()).handle(request);
                String responseString = response.getResponseString();
                System.out.println(responseString);
                Gson gson = new Gson();
                String author = responseString.substring(responseString.indexOf("\r\n\r\n") + 4, responseString.lastIndexOf(":"));
                String text = responseString.substring(responseString.lastIndexOf(":")+2);
                Quote quote = new Quote(author, text);
                String json = gson.toJson(quote);
                System.out.println(responseString + " response ");
                System.out.println(json + " json ");
                out.println(json);
            }

            in.close();
            out.close();
            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
