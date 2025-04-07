from openai import OpenAI
from pathlib import Path

speech_file_path = Path(__file__).parent / "siliconcloud-generated-speech.mp3"

client = OpenAI(
    api_key="sk-pdddeuyrtpuwqrzwkadvbzxiivasjbpvkzkkmruouugatowc", # 从 https://cloud.siliconflow.cn/account/ak 获取
    base_url="https://api.siliconflow.cn/v1"
)

with client.audio.speech.with_streaming_response.create(
        model="FunAudioLLM/CosyVoice2-0.5B", # 支持 fishaudio / GPT-SoVITS / CosyVoice2-0.5B 系列模型
        voice="speech:Elysia:sbdn8crxf9:mststxysjyefjprcoino", # 系统预置音色
        # 用户输入信息
        input="哼，月月这个懒虫，整天就知道偷懒！你还有什么脸活在世上啊？快去干活！不过要是你敢哭的话，我就饶不了你！",
        response_format="mp3" # 支持 mp3, wav, pcm, opus 格式
) as response:
    response.stream_to_file(speech_file_path)