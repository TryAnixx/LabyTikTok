package de.labytiktok.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WebUtils {
    private String URL;
    private int requestInterval;
    private String username;
    private boolean active;

    private static final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.114 Safari/537.36";

    private int follower;

    private ScheduledExecutorService executor;

    private JsonObject jsonObject;

    public WebUtils(String username, String URL, int requestInterval){
        follower = 0;
        this.username = username;
        this.URL = URL;
        this.requestInterval = requestInterval;
    }
    public WebUtils(){
        follower = 0;

    }
    public void start() {
        executor = Executors.newSingleThreadScheduledExecutor();
        active = true;
        executor.execute(()->{
            if(checkUsername()){
                executor.scheduleAtFixedRate(() -> {
                    fetch();
                    try {
                        follower = jsonObject.get("followerCount").getAsInt();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, 0,requestInterval, TimeUnit.SECONDS);

            }
        });
    }


    public void stop(){
        active = false;
        executor.shutdownNow();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    private boolean checkUsername() {
        fetch();
        return jsonObject.get("success").getAsBoolean();
    }

    private void fetch() {
        try {
            URL url = new URL(getFinalURL());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("User-Agent", userAgent);
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());
            jsonObject = new JsonParser().parse(reader).getAsJsonObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private String getFinalURL(){
        return URL + username;
    }



    public void setURL(String URL) {
        this.URL = URL;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRequestInterval(int requestInterval) {
        this.requestInterval = requestInterval;
    }

    public int getFollower() {
        return follower;
    }
}
