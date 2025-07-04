package online.kiroBot;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GPTKiroChat {
    private static final Logger logger = Logger.getLogger(GPTKiroChat.class.getName());
    private static final String API_KEY = "sk-LpgeMC6mz8kvXUKNO1dkOoBx7HU7nonGbUgAK4xg32GgvVvw";
    private static final String BASE_URL = "https://api.chatanywhere.tech/v1";

    public static String gpt35Api(List<JSONObject> messages,String character) {
        String systemMessage = Same.getKiroCharacter(character);
        //加入character
        if (systemMessage == null) {
            return null;
        }
        JSONObject messageJson = new JSONObject();
        messageJson.put("role", "system");
        messageJson.put("content", systemMessage);
        // 将不可变列表转换为可变列表
        List<Object> mutableMessages = new ArrayList<>(messages);
        mutableMessages.add(messageJson);
        JSONObject requestBody = new JSONObject();
        requestBody.put("model", "gpt-4o-mini");
        requestBody.put("messages", new JSONArray(mutableMessages));
        HttpResponse<String> response = Unirest.post(BASE_URL + "/chat/completions")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + API_KEY)
                .body(requestBody.toString())
                .asString();
        JSONObject object = new JSONObject(response.getBody());
        logger.info("GPT");
        logger.info(object.toString());
        return object
                .getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");
    }

    public static void messages(List<JSONObject> messages, String character) {
        JSONObject responseObject = new JSONObject()
                .put("role", "assistant")
                .put("content", gpt35Api(messages,character));
        messages.add(responseObject);
    }
}
