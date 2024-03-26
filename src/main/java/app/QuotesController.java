package app;

import http.Request;
import http.Server;
import http.response.HtmlResponse;
import http.response.RedirectResponse;
import http.response.Response;
import serialization.Quote;

public class QuotesController extends Controller {

    public QuotesController(Request request) {
        super(request);
    }
    @Override
    public Response doGet() {
        String htmlBody = "" +
                "<form method=\"POST\" action=\"/save-quote\">" +
                "<label>Author: </label><input name=\"author\" type=\"author\"><br><br>" +
                "<label>Quote: </label><input name=\"quote\" type=\"quote\"><br><br>" +
                "<button>Save Quote</button>" +"</form>"+
                "<label>Quotes: </label><br>";
                for (Quote quote : Server.quotes) {
                    htmlBody += "<p>" + quote.getAuthor() + ": \"" + quote.getText() + "\"</p>";
                }


        String content = "<html><head><title>Odgovor servera</title></head>\n";
        content += "<body>" + htmlBody + "</body></html>";

        return new HtmlResponse(content);    }

    @Override
    public Response doPost() {
        // TODO: obradi POST zahtev
        System.out.println("Quote sacuvan");
        return new RedirectResponse("/quote");
//        return null;
    }
}
