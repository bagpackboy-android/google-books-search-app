package ooo.alphaman.googlebooksearch;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.beans.IndexedPropertyChangeEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();
    private static final String GOOGLE_BOOKS_SELF_LINK = "https://www.googleapis.com/books/v1/volumes/";

    private QueryUtils() {
    }


    //Create a URL object with given String
    @Nullable
    private static URL createUrl(String stringToUrl) {
        URL url = null;
        if (stringToUrl == null || stringToUrl.isEmpty()) {
            System.out.println("Error creating URL with given String");
            return null;
        }
        try {
            url = new URL(stringToUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Malicious URL", e);
        }
        return url;
    }

    //Reading from InputSteam and storing it in String variable
    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }

        }

        return output.toString();
    }

    //Making HTTP Request to server with given URL
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error retrieving JSON from server. Request completed with Response Code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {

            Log.e(LOG_TAG, "Error while making a connection", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //JSON Parser for parsing the JSON and making Books objects from given information

    List<Books> jsonParserMakeBooks(String booksJson) {
        if (TextUtils.isEmpty(booksJson)) {
            return null;
        }

        List<Books> books = new ArrayList<>();

        try {

            JSONObject baseJsonResponse = new JSONObject(booksJson);

            JSONArray itemArray = baseJsonResponse.getJSONArray("items");

            for (int i = 0; i < itemArray.length(); i++) {

                JSONObject itemObj = itemArray.getJSONObject(i);

                String bookId = itemObj.getString("id");
                String selfLink = itemObj.getString("selfLink");
                JSONObject volumeInfo = itemObj.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authors = volumeInfo.getJSONArray("authors");
                String[] author = new String[authors.length()];
                for (i = 0; i < authors.length(); i++) {
                    author[i] = authors.getString(i);
                }
                String publisher = volumeInfo.getString("publisher");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");
                String smallThumbnail = imageLinks.getString("smallThumbnail");
                String thumbnail = imageLinks.getString("thumbnail");

                Books book = new Books(bookId, selfLink, title, author, publisher, smallThumbnail, thumbnail);
                books.add(book);
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);

        }

        return books;
    }

}
