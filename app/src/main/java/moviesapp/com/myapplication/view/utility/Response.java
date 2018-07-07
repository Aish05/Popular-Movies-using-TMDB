package moviesapp.com.myapplication.view.utility;



import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Response {

    public String getResponse(URL paramURL)
            throws Exception {

        String inputLine;
        String result;
        //Create a connection
        HttpURLConnection connection = (HttpURLConnection)
                paramURL.openConnection();
        //Set methods and timeouts

        connection.setReadTimeout(25000);
        connection.setConnectTimeout(25000);

        //Connect to our url
        connection.connect();
        //Create a new InputStreamReader
        InputStreamReader streamReader = new
                InputStreamReader(connection.getInputStream());
        //Create a new buffered reader and String Builder
        BufferedReader reader = new BufferedReader(streamReader);
        StringBuilder stringBuilder = new StringBuilder();
        //Check if the line we are reading is not null
        while ((inputLine = reader.readLine()) != null) {
            stringBuilder.append(inputLine);
        }
        //Close our InputStream and Buffered reader
        reader.close();
        streamReader.close();
        //Set our result equal to our stringBuilder
        result = stringBuilder.toString();


        return result;
    }

}
