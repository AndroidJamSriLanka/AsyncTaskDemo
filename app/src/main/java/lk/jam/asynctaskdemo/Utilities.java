package lk.jam.asynctaskdemo;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Tharu on 2015-03-25.
 */
public class Utilities {


    private static int counter = 0;
    public static String doSomethingHeavy(String a, String b) {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return a + b + " "+ counter++;
    }

    public static String getDataFromWebsite(String url) {
        try {
            HttpClient client = new DefaultHttpClient();

            HttpResponse response = client.execute(new HttpGet(new URI(url)));
            Log.i("AndroidJam", response.getStatusLine().getStatusCode() + "");
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String out = "", reply = "";
            while ((out = reader.readLine()) != null) {
                reply += out;
                Log.i("AndroidJam", out);
            }

            return reply;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
