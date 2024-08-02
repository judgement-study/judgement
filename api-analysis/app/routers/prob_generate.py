import os
import requests
from bs4 import BeautifulSoup
from pydantic import BaseModel
from fastapi import APIRouter, Depends, HTTPException, Request
from fastapi.responses import JSONResponse
from typing import List, Dict, Any

class probRequest(BaseModel):
    problemNumber: int

router = APIRouter()

@router.post("/prob_generate")
async def prob_generate(request: probRequest) -> Dict[str, Any]:
    prob_num: int = request.problemNumber
    prob_url: str = f"https://www.acmicpc.net/problem/{prob_num}"
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36"
    }
    
    response = requests.get(prob_url, headers=headers)
    if response.status_code != 200:
        return JSONResponse({"error": f"Failed to fetch problem details: {response.status_code}"}, status_code=response.status_code)
    
    soup = BeautifulSoup(response.text, 'html.parser')

    # 제목과 설명
    title: str = soup.find('title').text.strip()
    problem_description: str = soup.find('div', {'id': 'problem_description'}).text.strip()
    problem_input: str = soup.find('div', {'id': 'problem_input'}).text.strip()
    problem_output: str = soup.find('div', {'id': 'problem_output'}).text.strip()

    # 입력 및 출력 예시
    sample_inputs: List[str] = []
    sample_outputs: List[str] = []
    example_pairs = soup.find_all('pre', {'class': 'sampledata'})

    for i in range(0, len(example_pairs), 2):
        sample_inputs.append(example_pairs[i].text.strip())
        if i + 1 < len(example_pairs):
            sample_outputs.append(example_pairs[i + 1].text.strip())

    # 추가 정보
    info_table = soup.find('table', {'id': 'problem-info'})
    info_labels: List[str] = ["time_limit", "memory_limit", "submissions", "correct", "user_correct", "accuracy"]
    info: Dict[str, str] = {}

    if info_table:
        info_values = info_table.find_all('td')
        for i, label in enumerate(info_labels):
            if i < len(info_values):
                info[label] = info_values[i].text.strip()
            else:
                info[label] = "N/A"

    json_data: Dict[str, Any] = {
        "title": title,
        "time_limit": info.get("time_limit", "N/A"),
        "memory_limit": info.get("memory_limit", "N/A"),
        "accuracy": info.get("accuracy", "N/A"),
        "prob_description": problem_description,
        "input_description": problem_input,
        "output_description": problem_output,
        "sample_inputs": sample_inputs,
        "sample_outputs": sample_outputs
    }

    return {"status": "success", "output": json_data}
