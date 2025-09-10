# BilibiliAI
**Bilibili语音直播机器人**  
基于[SiliconCloud](https://www.siliconcloud.ai/)语音合成技术开发的Bilibili直播互动机器人，支持自动连麦、语音点歌、实时互动等功能。

---

## 📌 功能特性
1. **语音合成**  
   - 支持自定义音色、语速、情感等参数
   - 自动化生成直播开场白/结束语/互动语音

2. **直播互动**  
   - 自动响应观众弹幕指令
   - 实现简单问答、投票、抽奖等互动功能

3. **定时任务**  
   - 支持定时启动/关闭直播间
   - 定时播放背景音乐或公告

4. **多平台兼容**  
   - 支持Bilibili直播姬/ OBS/ 其他推流软件集成

---

## 🔧 快速入门

### 1. 环境准备
- **Java环境**：JDK 1.8+
- **构建工具**：Maven 3.x+
- **Bilibili账号**：需绑定B站直播姬或开放平台账号

### 2. 项目部署
bash

克隆项目
git clone https://github.com/Cindifind/BilibiliAI.git

构建项目
mvn clean package

启动机器人
java -jar target/BilibiliAI.jar

### 3. 配置说明
修改 `src/main/resources/application.yml` 配置文件：
yaml

bilibili:

room-id: 123456 # 直播间ID

auth-key: xxxxxxx # B站授权密钥

voice-model: siliconcloud-standard # 语音模型选择

llm:

api-key: ${LLM_API_KEY} # 替换为您的SiliconCloud API密钥

---

## 🎤 使用示例
bash

启动自动连麦模式
curl -X POST http://localhost:8080/api/live/connect

触发语音点歌功能
curl -X POST http://localhost:8080/api/song/request-d "song_name=孤勇者"

发送实时弹幕回复
curl -X POST http://localhost:8080/api/chat/reply-d "message=谢谢宝宝们的礼物！"

---

## ⚙️ 高级功能
1. **自定义词库**  
   在 `data/dictionary.txt` 中添加自定义词汇，支持方言/专业术语优化

2. **直播监控**  
   通过 `src/main/resources/rules.yml` 配置敏感词/违规词检测规则

3. **数据分析**  
   访问 `http://localhost:8080/dashboard` 查看实时直播数据面板

---


## 📜 许可证
本项目采用 MIT 开源协议，允许商业使用但需保留版权声明。

---

## 📢 注意事项
1. 请勿滥用语音合成服务，遵守Bilibili社区规范
2. 语音内容需符合国家法律法规
3. 敏感操作（如红包发放）需自行添加风控逻辑

如需技术支持，可联系仓库维护者：Cindifind#1234
需要注意的要点：
1.
请将 LLM_API_KEY替换为实际申请的SiliconCloud API密钥

2.
建议添加.env文件管理敏感信息

3.
生产环境建议使用数据库存储用户数据

4.
已经自行实现Bilibili直播姬的WebSocket通信协议

5.
推荐搭配前端界面实现完整直播控制台
