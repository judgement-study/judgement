from fastapi import APIRouter
from pydantic import BaseModel
from typing import List, Tuple, Dict, Union

class tierRequest(BaseModel):
    solvedProblem: str  # Bronze2 Bronze3 .... 형식

router = APIRouter()

tiers: Dict[str, int] = {
    "Master": 1, "Diamond": 2, "Platinum": 3,
    "Gold": 4, "Silver": 5, "Bronze": 6
}
grades = {"V": 5, "IV": 4, "III":3 , "II": 2, "I": 1}

prob_tier_scores = {
    "Master": (1530, 4850), "Diamond": (363, 1148), "Platinum": (85, 272),
    "Gold": (20, 64), "Silver": (5, 15), "Bronze": (0, 4)
}

tier_thresholds = {
    "Master": 5000,
    "Diamond": 3000,
    "Platinum": 2000,
    "Gold": 1000,
    "Silver": 500,
    "Bronze": 0
}

def parse_and_score_tier(tier_str: str) -> int:
    """
    등급 문자열을 파싱하여 등급 이름과 순위를 추출한 후, 점수를 계산합니다.

    Args:
        tier_str: 등급과 순위를 나타내는 문자열 (예: 'GoldI', 'SilverIII').

    Returns:
        int: 파싱된 등급 이름과 순위에 따라 계산된 점수.
    """
    for i in range(1, len(tier_str)):
        if tier_str[i].isupper():
            tier_name = tier_str[:i]
            grade_name = tier_str[i:]
            break
    max_score, min_score = prob_tier_scores[tier_name]
    tier_rank = grades[grade_name]
    # 등급이 1일 때 max_score, 5일 때 min_score
    score: int = max_score - ((tier_rank - 1) * (max_score - min_score) // 4)
    return score

def calculate_user_tier(total_score: int) -> str:
    """
    총 점수에 따라 유저의 티어와 세부 등급을 산정합니다.

    Args:
        total_score: 유저의 총 점수.

    Returns:
        str: 유저의 티어와 세부 등급.
    """
    for tier, threshold in tier_thresholds.items():
        if total_score >= threshold: tier_name = tier; break

    if tier_name == "Master":
        sub_tier_score = (total_score - 5000) / 4850 * 4
    else:
        next_tier_threshold = next((v for k, v in tier_thresholds.items() if v < threshold), 0)
        sub_tier_score = (total_score - next_tier_threshold) / (threshold - next_tier_threshold) * 4

    grade_index = int(sub_tier_score) + 1
    grade_index = min(grade_index, 5)

    return f"{tier_name}{list(grades.keys())[5 - grade_index]}"

@router.post("/tier")
async def execute_python(request: tierRequest) -> Dict[str, Union[str, int]]:
    solved_prob: str = request.solvedProblem
    solved_prob_list: List[str] = solved_prob.split()

    prob_tier_scores_list: List[Tuple[str, int]] = [(tier, parse_and_score_tier(tier)) for tier in solved_prob_list]
    top_100_prob_tier_scores: List[Tuple[str, int]] = sorted(prob_tier_scores_list, key=lambda x: x[1], reverse=True)[:100]
    top_100_scores: int = sum(score for _, score in top_100_prob_tier_scores)

    user_tier = calculate_user_tier(top_100_scores)

    return {'status': 'success', 'score': top_100_scores, 'tier': user_tier}