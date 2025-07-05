package online.kiroBot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Same {
    private static final Logger logger = Logger.getLogger(GPTKiroChat.class.getName());
    public static void saveMessagesToFile(List<JSONObject> messages, String filePath) {
        Path path1 = Path.of(System.getProperty("user.dir") + "\\data\\" + filePath);
        try {
            Files.writeString(path1, messages.toString(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveMessagesToFile(JSONArray messages, String filePath) {
        Path path1 = Path.of(System.getProperty("user.dir") + "\\data\\" + filePath);
        try {
            Files.writeString(path1, messages.toString(), StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static JSONArray loadMessagesFromFile(String fileName) {
        JSONArray messages = new JSONArray();
        try {
            // 使用类加载器获取资源文件
            Path path = Path.of(System.getProperty("user.dir") +  "\\data\\"+ fileName);
            String content = Files.readString(path, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dialogue = jsonArray.getJSONObject(i);
                messages.put(dialogue);
            }
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "读取文件时发生错误: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "读取文件时发生未知错误: " + e.getMessage(), e);
        }
        return messages;
    }
    public static String getKiroCharacter(String fileName) {
        try {
            Path path1 = Path.of(System.getProperty("user.dir") + "\\data\\"+fileName+"_character.json");
            String str1 = Files.readString(path1, StandardCharsets.UTF_8);
            logger.info("对话数据加载成功");
            return str1;
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "读取文件时发生错误: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "读取文件时发生未知错误: " + e.getMessage(), e);
            return null;
        }
    }
    public static String getMessage(String UserMessage,int choice,String character,Boolean dialogue) {
        String filePath = character+"_dialogues.json";
        JSONArray messages = new JSONArray();
        if (dialogue) {
            messages = loadMessagesFromFile(filePath);
        }
        //家入dialogues
        System.out.println(messages);
        messages.put(new JSONObject()
                .put("role", "user")
                .put("content", UserMessage));
        //用户输入信息
        switch (choice)
            {
                case 1:
                    GPTKiroChat.messages(messages,character);
                    break;
                case 2:
                    System.out.println(messages);
                    DeepSeekKiroChat.messages(messages,character);
                    break;
                default:
                    break;
            }
        if (dialogue) {
            Same.saveMessagesToFile(messages, filePath);
        }
        return messages.getJSONObject(messages.length() - 1).getString("content");
    }
    public static String loadApi(Path path){
        try {
            String content = Files.readString(path, StandardCharsets.UTF_8);
            return content;
        } catch (IOException | NullPointerException | IllegalArgumentException e) {
            logger.log(Level.SEVERE, "读取文件时发生错误: " + e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "读取文件时发生未知错误: " + e.getMessage(), e);
            return null;
        }
    }
}
