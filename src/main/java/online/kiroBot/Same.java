package online.kiroBot;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.LinkedList;
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
    public static List<JSONObject> loadMessagesFromFile(String fileName) {
        List<JSONObject> messages = new ArrayList<>();
        try {
            // 使用类加载器获取资源文件
            Path path = Path.of(System.getProperty("user.dir") +  "\\data\\"+ fileName);
            String content = Files.readString(path, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject dialogue = jsonArray.getJSONObject(i);
                messages.add(dialogue);
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
    public static String getMessage(String UserMessage,int choice,String character) {
        String filePath = character+"_dialogues.json";
        List<JSONObject> messages = loadMessagesFromFile(filePath);
        //家入dialogues
//        System.out.println(messages);
        LinkedList<JSONObject> messageQueue = new LinkedList<>();
        int maxQueueSize = 10;
        for (JSONObject message : messages) {
            if (messageQueue.size() >= maxQueueSize) {
                messageQueue.removeFirst(); // 移除最旧的消息对象
            }
            messageQueue.addLast(message); // 添加新的消息对象
        }
        messages.add(new JSONObject()
                .put("role", "user")
                .put("content", UserMessage+"（不需要加任何表情以及动作描写）"));
        //用户输入信息
        switch (choice)
            {
                case 1:
                    GPTKiroChat.messages(messages,character);
                    break;
                case 2:
                    DeepSeekKiroChat.messages(messages,character);
                    break;
                default:
                    break;
            }


        Same.saveMessagesToFile(messages, filePath);
        return messages.get(messages.size() - 1).getString("content");
    }

}
