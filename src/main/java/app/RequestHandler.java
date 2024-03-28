package app;

import http.HttpMethod;
import http.Request;
import http.response.Response;

public class RequestHandler {
    public Response handle(Request request,String cookie) throws Exception {
        if (request.getPath().equals("/quote") && request.getHttpMethod().equals(HttpMethod.GET)) {
            return (new QuotesController(request,cookie)).doGet();
        } else if (request.getPath().equals("/save-quote") && request.getHttpMethod().equals(HttpMethod.POST)) {
            return (new QuotesController(request,cookie)).doPost();
        }
        else if (request.getPath().equals("/quote-of-the-day") && request.getHttpMethod().equals(HttpMethod.GET)) {
            return (new QuoteOfTheDayController(request,cookie)).doGet();
        }

        throw new Exception("Page: " + request.getPath() + ". Method: " + request.getHttpMethod() + " not found!");
    }
}
