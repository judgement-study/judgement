from fastapi import FastAPI, HTTPException, APIRouter
from pydantic import BaseModel

app = FastAPI()

class tierRequest(BaseModel):
    solvedProblem : str # bronze2, bronze3, ....  형식

# 티어 우선순위 설정
tiers = {
    "diamond": 1, "emerald": 2, "platinum": 3,
    "gold": 4, "silver": 5, "bronze": 6, "iron": 7
}

tier_scores = {
    "diamond": (5000, 4000), "emerald": (4000, 3000), "platinum": (3000, 2000),
    "gold": (2000, 1000), "silver": (1000, 500), "bronze": (500, 100), "iron": (100, 0)
}

def calculate_score(tier_name, tier_rank):
    max_score, min_score = tier_scores[tier_name]
    # 등급이 1일 때 max_score, 5일 때 min_score
    score = max_score - ((tier_rank - 1) * (max_score - min_score) // 4)
    return score

def parse_and_score_tier(tier_str):
    tier_name = ''.join(filter(str.isalpha, tier_str))
    tier_rank = int(''.join(filter(str.isdigit, tier_str)))
    score = calculate_score(tier_name, tier_rank)
    return score

router = APIRouter()

@router.post("/tier")
async def execute_python(request: tierRequest):
    solved_prob:str = request.solvedProblem
    solved_prob_list = solved_prob.split()


    tier_scores_list = [(tier, parse_and_score_tier(tier)) for tier in solved_prob_list]
    top_100_tier_scores = sorted(tier_scores_list, key=lambda x: x[1], reverse=True)[:100]
    top_100_scores = sum(score for _, score in top_100_tier_scores)

    # return {
    #     "tier score" : tier_scores_list,
    #     "top 100 score" : top_100_scores
    # }
    return {'status': 'success', 'score': top_100_scores}