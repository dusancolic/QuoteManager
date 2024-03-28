package http.response;

public class HtmlResponse extends Response {

    private final String html;
    private String cookie;

    public HtmlResponse(String html, String cookie) {
        this.html = html;
        this.cookie = cookie;
    }

    @Override
    public String getResponseString() {
        String response = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n" +"Set-Cookie:"+ cookie + "\r\n\r\n" ;
        response += html;

        return response;
    }
}