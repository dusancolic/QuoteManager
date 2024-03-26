package app;

import http.Request;
import http.Server2;
import http.response.HtmlResponse;
import http.response.Response;
import serialization.Quote;

import java.util.Random;

public class QuoteOfTheDayController extends Controller{
    private static boolean quotePicked = false;
    private static Quote quote;
    public QuoteOfTheDayController(Request request) {
        super(request);
    }
    @Override
    public Response doGet() {
        int limit = Server2.quotes.size();
        if(!quotePicked) {
            Random random = new Random();
            quote = Server2.quotes.get(random.nextInt(limit));
            quotePicked = true;
        }
        String content = quote.toString();

        return new HtmlResponse(content);    }

    @Override
    public Response doPost() {
        return null;
    }
}
