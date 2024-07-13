from fastapi import FastAPI, HTTPException, APIRouter
from pydantic import BaseModel
import subprocess
import tempfile

class CodeExecutionRequest(BaseModel):
    code: str
    inputData: str

router = APIRouter()

@router.post("/execute/python")
async def execute_python(request: CodeExecutionRequest):
    code: str = request.code
    input_data: str = request.inputData
    with tempfile.NamedTemporaryFile(delete=False, suffix=".py") as code_file:
        code_file.write(code.encode())
        code_file_path = code_file.name

    process = subprocess.Popen(
        ['python3', code_file_path],
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )
    stdout, stderr = process.communicate(input=input_data.encode())
    
    if process.returncode != 0:
        return {'status': 'error', 'output': stderr.decode()}
    
    return {'status': 'success', 'output': stdout.decode()}