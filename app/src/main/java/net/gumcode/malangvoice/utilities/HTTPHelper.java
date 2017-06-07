package net.gumcode.malangvoice.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by A. Fauzi Harismawan on 12/19/2015.
 */
public class HTTPHelper {

    public static InputStream sendGETRequest(String url) {
        try {
            URL uri = new URL(url);
            URLConnection connection = uri.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
