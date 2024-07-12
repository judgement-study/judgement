import os
import requests
from bs4 import BeautifulSoup
from pydantic import BaseModel
from fastapi import APIRouter, Depends, HTTPException, Request
import numpy as np

class MetaDataRequest(BaseModel):
    problemNumber: int
    
router = APIRouter()

def convert_int64(value: int) -> int:
    """
    numpy int64->int형으로 변경하여 반환
        Args :
            a ``numpy int64``
        Retunrs :
            int(a) ``int``
    """
    if isinstance(value, np.int64): return int(value)
    raise TypeError

@router.get("/prob_generate")
async def prob_generate(request: MetaDataRequest):
    prob_num: int = problemNumber.url
    prob_url: str = f"https://www.acmicpc.net/problem/{prob_num}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
    }
    
    response = requests.get(url, headers=headers)
    if response.status_code != 200:
        print(f"Failed to fetch problem details: {response.status_code}")
        return
    
    soup = BeautifulSoup(response.text, 'html.parser')

    # 제목과 설명
    title = soup.find('title').text.strip()
    problem_description = soup.find('div', {'id': 'problem_description'}).text.strip()
    problem_input = soup.find('div', {'id': 'problem_input'}).text.strip()
    problem_output = soup.find('div', {'id': 'problem_output'}).text.strip()

    # 입력 및 출력 예시
    sample_inputs = []
    sample_outputs = []
    example_pairs = soup.find_all('pre', {'class': 'sampledata'})

    for i in range(0, len(example_pairs), 2):
        sample_inputs.append(example_pairs[i].text.strip())
        if i + 1 < len(example_pairs):
            sample_outputs.append(example_pairs[i + 1].text.strip())

    # 추가 정보
    info_table = soup.find('table', {'id': 'problem-info'})
    info_labels = ["time_limit", "memory_limit", "submissions", "correct", "user_correct", "accuracy"]
    info = {label: info_table.find_all('td')[i].text.strip() for i, label in enumerate(info_labels)}

    json_data = {
        "title": title,
        "time_limit" : info[info_labels.index("time_limit")],
        "memory_limit" : info[info_labels.index("memory_limit")],
        "accuracy" : info[info_labels.index("accuracy")],
        "prob_descipt" : problem_description,
        "input_descript" : problem_input,
        "output_descript" : problem_output,
        "sample_inputs" : sample_inputs,
        "sample_outputs" : sample_outputs
    }

    converted_json_data = json.loads(json.dumps(json_data, default=convert_int64))
    return JSONResponse({"data":converted_json_data, "status": 200})