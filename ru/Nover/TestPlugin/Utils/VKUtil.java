package ru.Nover.TestPlugin.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import ru.Nover.TestPlugin.JavaMain;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

public class VKUtil
{
    private VKUtil ( ) {}

    private static String makeRequest ( String link ) {
        try{
            URL url = new URL(link);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(30000);

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }catch (Exception ex) {
            return null;
        }
    }

    public static VKResponse getLastPost ( String groupDomain ) {
        VKResponse vkResponse = new VKResponse(0, null, true);

        try {
            String response = makeRequest("https://api.vk.com/method/wall.get?access_token=7b1c555ab95dcb615cc61acbc3f3f7681f9fefa35665b44bff472a5bea99a383bb26164cd6d8b5757a712&count=2&domain=" + groupDomain + "&v=5.145");
            if ( response == null )
                throw new NullPointerException("Ответ пустой");

            JSONObject jsonResponse = (JSONObject) JSONValue.parse(response);
            JSONArray array = (JSONArray) ((JSONObject)jsonResponse.get("response")).get("items");

            for (Object o : array) {
                JSONObject jsonObject = (JSONObject) o;
                vkResponse = new VKResponse((long) jsonObject.get("id"), (String) jsonObject.get("text"), false);

                if ( jsonObject.containsKey("is_pinned") ) {
                    if ( (long)jsonObject.getOrDefault("is_pinned", 0) != 1 ) {
                        return vkResponse;
                    }
                }
            }
        }catch (Exception exception) {
            JavaMain.get().getLogger().severe("Ошибка при получение последних записей: " + exception.getMessage());
        }
        return vkResponse;
    }

    public static class VKResponse {
        private final long lastPostId;
        private final String text;
        private final boolean error;

        public VKResponse ( long lastPostId, String text, boolean error ) {
            this.lastPostId = lastPostId;
            this.text = text;
            this.error = error;
        }

        public Long getLastPostId ( ) {
            return lastPostId;
        }

        public String getText ( ) {
            return text;
        }

        public boolean isError ( ) {
            return error;
        }

        @Override
        public String toString() {
            return "[lastPostId=" + lastPostId + "], [text=" + text + "], [error=" + error + "]";
        }
    }
}
