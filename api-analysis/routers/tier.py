from fastapi import FastAPI, HTTPException, APIRouter
from pydantic import BaseModel
from typing import List, Tuple, Dict

app = FastAPI()

class tierRequest(BaseModel):
    solvedProblem: str  # bronze2, bronze3, .... 형식

# 티어 우선순위 설정
tiers: Dict[str, int] = {
    "diamond": 1, "emerald": 2, "platinum": 3,
    "gold": 4, "silver": 5, "bronze": 6, "iron": 7
}

prob_tier_scores: Dict[str, Tuple[int, int]] = {
    "diamond": (5000, 4000), "emerald": (4000, 3000), "platinum": (3000, 2000),
    "gold": (2000, 1000), "silver": (1000, 500), "bronze": (500, 100), "iron": (100, 0)
}

def calculate_score(tier_name: str, tier_rank: int) -> int:
    """
    등급 이름과 등급 순위에 따라 점수를 계산합니다.

    Args:
        tier_name: 등급의 이름 (예: 'Gold', 'Silver').
        tier_rank: 등급 내의 순위 (예: 1이 가장 높은 순위, 5가 가장 낮은 순위).

    Returns:
        int: 등급 이름과 순위에 따라 계산된 점수.
    """
    max_score, min_score = prob_tier_scores[tier_name]
    # 등급이 1일 때 max_score, 5일 때 min_score
    score: int = max_score - ((tier_rank - 1) * (max_score - min_score) // 4)
    return score

def parse_and_score_tier(tier_str: str) -> int:
    """
    등급 문자열을 파싱하여 등급 이름과 순위를 추출한 후, 점수를 계산합니다.

    Args:
        tier_str: 등급과 순위를 나타내는 문자열 (예: 'Gold1', 'Silver3').

    Returns:
        int: 파싱된 등급 이름과 순위에 따라 계산된 점수.
    """
    tier_name: str = ''.join(filter(str.isalpha, tier_str))
    tier_rank: int = int(''.join(filter(str.isdigit, tier_str)))
    score: int = calculate_score(tier_name, tier_rank)
    return score

router = APIRouter()

@router.post("/tier")
async def execute_python(request: tierRequest) -> Dict[str, Union[str, int]]:
    solved_prob: str = request.solvedProblem
    solved_prob_list: List[str] = solved_prob.split()

    prob_tier_scores_list: List[Tuple[str, int]] = [(tier, parse_and_score_tier(tier)) for tier in solved_prob_list]
    top_100_prob_tier_scores: List[Tuple[str, int]] = sorted(prob_tier_scores_list, key=lambda x: x[1], reverse=True)[:100]
    top_100_scores: int = sum(score for _, score in top_100_prob_tier_scores)

    return {'status': 'success', 'score': top_100_scores}
