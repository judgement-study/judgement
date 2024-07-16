import uvicorn
from fastapi import FastAPI
import subprocess
from fastapi.middleware.cors import CORSMiddleware

from routers.root import router as root_router
from routers.prob_generate import router as generate_router
from routers.execute import router as execute_code_router
from routers.tier import router as tier_calc_router

from config.settings import IP_NUM, PORT_NUM


app = FastAPI()
# CORS 설정 추가
app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

app.include_router(root_router)
app.include_router(generate_router)
app.include_router(execute_code_router)
app.include_router(tier_calc_router)

if __name__ == '__main__':
    command = f"uvicorn main:app --host {IP_NUM} --port {PORT_NUM} --workers 1"
    subprocess.run(command, shell=True)