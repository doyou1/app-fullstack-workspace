import openai
import os

from dotenv import load_dotenv, find_dotenv
_ = load_dotenv(find_dotenv()) # read local .env file

openai.api_key = os.getenv("OPENAI_API_KEY")

# helper function
# Throughout this course, we will use OpenAI's gpt-3.5-turbo model and the chat completions endpoint.
# This helper function will make it easier to use prompts and look at the generated outputs.
def get_completion(prompt, model="gpt-3.5-turbo"):
    messages = [{"role": "user", "content": prompt}]
    response = openai.ChatCompletion.create(
        model=model,
        messages=messages,
        temperature=0, # this is the degree of randomness of the model's output
    )
    return response.choices[0].message["content"]


### Promopting Principles
    # Principle 1: Write clear and specific instructions
    # Principle 2: Give the model time to "think"

## Tactics
# Tactic 1: Use delimiters to clearly indicate distinct parts of the input
    # - Delimiters can be anything like ```, ''', <>, <tag> </tag>, :

# (translation) 
# 가능한 한 명확하고 구체적인 지침을 제공하여 모델이 수행할 작업을 표현해야 합니다. 이 의지 
# 원하는 출력으로 모델을 안내하고 관련이 없거나 잘못된 응답을 받을 가능성을 줄입니다. 명확하게 쓰기를 혼동하지 마십시오 
# 프롬프트에서 짧은 프롬프트를 표시합니다. 대부분의 경우 긴 프롬프트는 모델에서 더 명확하고 맥락을 제공하므로 더 상세하고 관련성 있는 출력으로 이어질 수 있습니다.
text = f"""
You should express what you want a model to do by \
providing instructions that are as clear and \
specific as you can possibly make them. \
This will guide the model towards the desired output, \
and reduce the chances of receiving irrelevant \
or incorrect responses. Don't confuse writing a \
clear prompt with writing a short prompt. \
In many cases, longer prompts provide more clarity \
and context from the model, which can lead to \
more detailed and relevant outputs.
"""

# (translation)
# 삼중 백택으로 구분된 텍스트를 하나의 문장으로 요약합니다. ```{text}```
prompt = f"""
Summarize the text delimited by triple backticks \
into a single sentence. In Korean
```{text}```
"""

# response = get_completion(prompt)
# (translation) 모델을 원하는 출력으로 안내하기 위해 명확하고 구체적인 지침을 제공해야 하며, 긴 프롬프트는 보다 상세하고 관련성이 높은 출력에 대해 보다 명확한 설명과 맥락을 제공할 수 있습니다.
# (In Korean) 모델이 원하는 출력물을 만들기 위해서는 가능한 한 명확하고 구체적인 지시사항을 제공하여 모델이 원하는 방향으로 이끌어야 하며, 길이가 짧은 프롬프트와 명확한 프롬프트를 혼동하지 말아야 하며, 많은 경우 더 긴 프롬프트가 모델로부터 더 많은 명확성과 문맥을 제공하여 더 자세하고 관련성 높은 출력물 을 얻을 수 있다.
# Clear and specific instructions should be provided to guide a model towards the desired output, and longer prompts can provide more clarity and context for more detailed and relevant outputs.
# print(response) 

# Tactic 2: Ask for a structured output
 # JSON, HTML
prompt = f"""
Generate a list of three made-up book titles along \
with their authors and genres.
Provide them in JSON format with the following keys:
book_id, title, author, genre.
"""
# response = get_completion(prompt)
# print(response)
# [
#   {
#     "book_id": 1,
#     "title": "The Lost City of Zorath",
#     "author": "Aria Blackwood",
#     "genre": "Fantasy"
#   },
#   {
#     "book_id": 2,
#     "title": "The Last Survivors",
#     "author": "Ethan Stone",
#     "genre": "Science Fiction"
#   },
#   {
#     "book_id": 3,
#     "title": "The Secret Life of Bees",
#     "author": "Lila Rose",
#     "genre": "Romance"
#   }
# ]

# Tactic 3: Ask the model to check whether conditions are satisfied

# (translation)
# 차 한 잔을 만드는 것은 쉽습니다! 
# 일단 물을 끓여야 하는데 그럴 때 컵을 들고 티백을 넣어주세요. 
# 물이 충분히 뜨거우면 티백 위에 붓기만 하면 됩니다. 
# 차가 끓을 수 있도록 잠시 놔두세요. 
# 몇 분 후에 티백을 꺼냅니다. 
# 만약 여러분이 원한다면, 맛을 내기 위해 설탕이나 우유를 첨가할 수 있습니다. 
# 그게 다야! 당신은 맛있는 차 한 잔을 즐길 수 있습니다.
text_1 = f"""
Making a cup of tea is easy! First, you need to get some \
water boiling, While that's happening, \
grab a cup and put a tea bag in it. Once the water is \
hot enough, just pour it over the tea bag. \
Let it sit for a bit so the tea can steep. After a \
few minutes, take out the tea bag. If you \
like, you can add some sugar or milk to taste. \
And that's it! You've got yourself a delicious \
cup of tea to enjoy.
"""

# (translation)
# 세 개의 따옴표로 구분된 텍스트가 제공됩니다.
# 일련의 지시가 포함되어 있다면, 
# 다음 형식으로 해당 지침을 다시 작성합니다:
# 1단계 - ...
# 2단계 - …
# …
# N단계 - …
# 텍스트에 일련의 지침이 포함되어 있지 않으면, 
# 그런 다음 "단계가 제공되지 않음"이라고 간단히 적습니다
prompt = f"""
You will be provided with text delimited by triple quotes.
If it contains a sequence of instructions, \
re-write those instructions in the following format:

Step 1 - ...
Step 2 - …
…
Step N - …

If the text does not contain a sequnce of instructions, \
then simply write \"No steps provided.\"

\"\"\"{text_1}\"\"\"
"""

# response = get_completion(prompt)
# print("Completion for Text 1: ")
# print(response)
# Completion for Text 1: 
# Step 1 - Get some water boiling.
# Step 2 - Grab a cup and put a tea bag in it.
# Step 3 - Once the water is hot enough, pour it over the tea bag.
# Step 4 - Let it sit for a bit so the tea can steep.
# Step 5 - After a few minutes, take out the tea bag.
# Step 6 - Add some sugar or milk to taste (optional).
# Step 7 - Enjoy your delicious cup of tea!

# (translation)
# 오늘은 태양이 밝게 빛나고, 새들이 지저귀고 있습니다.
# 공원으로 산책을 가기에 좋은 날씨입니다. 
# 꽃이 피고, 나무들이 산들바람에 살랑살랑 흔들리고 있습니다. 
# 사람들은 밖에서 돌아다니며 멋진 날씨를 즐기고 있습니다. 
# 어떤 사람들은 소풍을 가고 있는 반면, 다른 사람들은 게임을 하거나 잔디에서 휴식을 취하고 있습니다. 
# 야외에서 시간을 보내고 자연의 아름다움을 감상하기에 완벽한 날입니다.
text_2 = f"""
The sun is shining brightly today, and the birds are \
singing. It's a beautiful day to go for a \
walk in the park. The flowers are blooming, and the \
trees are swaying gently in the breeze. People \
are out and about, enjoying the lovely weather. \
Some are having picnics, while others are playing \
games or simply relaxing on the grass. It's a \
perfect day to spend time outdoors and appreciate the \
beauty of nature.
"""

prompt = f"""
You will be provided with text delimited by triple quotes.
If it contains a sequence of instructions, \
re-write those instructions in the following format:

Step 1 - ...
Step 2 - …
…
Step N - …

If the text does not contain a sequnce of instructions, \
then simply write \"No steps provided.\"

\"\"\"{text_2}\"\"\"
"""

# response = get_completion(prompt)
# print("Completion for Text 2: ")
# print(response)
# Completion for Text 2: 
# No steps provided.

# Tactic 4: "Few-shot" prompting
    # what is "Few-shot" ?  
    # : 자연어 처리에서 상황 내 학습, 퓨샷 학습 또는 퓨샷 프롬팅은 모델이 작업을 시도하기 전에 예를 처리할 수 있도록 하는 프롬팅 기술입니다. 이 방법은 GPT-3의 출현 이후 대중화되었으며 대규모 언어 모델의 창발적 속성으로 간주됩니다


# (translation)
# 당신의 임무는 일관된 스타일로 대답하는 것입니다.

# <아이>: 인내심에 대해 가르쳐 주세요.
# <조부모>: 가장 깊은 계곡을 깎는 강은 수수한 샘에서 흐릅니다. 가장 위대한 교향곡은 하나의 음표에서 비롯됩니다. 가장 복잡한 태피스트리는 단독 실에서 시작합니다.
# <아이>: 회복력에 대해 가르쳐 주세요.
prompt = f"""
Your task is to answer in a consistent style.

<child>: Teach me about patience.

<grandparent>: The river that carves the deepest \
valley flows from a modest spring; the \
grandest symphony originates from a single note; \
the most intricate tapestry begins with a solitary thread.

<child>: Teach me about resilience.
"""

response = get_completion(prompt)
print(response)
# <grandparent>: Resilience is like a tree that bends with the wind but never breaks. It is the ability to bounce back from adversity and keep moving forward, even when things get tough. Just like a tree needs strong roots to weather a storm, we need to cultivate inner strength and perseverance to overcome life's challenges.
# (translation) <조부모>: 회복력이란 바람과 함께 휘어지되 결코 꺾이지 않는 나무와 같은 것입니다. 그것은 역경에서 다시 일어설 수 있는 능력이고, 상황이 어려워질 때에도 계속해서 앞으로 나아갈 수 있는 능력입니다. 나무가 폭풍을 이겨내기 위해 튼튼한 뿌리가 필요한 것처럼, 우리는 삶의 도전을 극복하기 위해 내면의 힘과 끈기를 길러야 합니다.