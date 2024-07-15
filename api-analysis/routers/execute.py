from fastapi import FastAPI, HTTPException, APIRouter
from pydantic import BaseModel
import subprocess
import tempfile

app = FastAPI()

class CodeExecutionRequest(BaseModel):
    code: str
    inputData: str

router = APIRouter()

def execute_code(compile_cmd, run_cmd, code, input_data, code_ext):
    with tempfile.NamedTemporaryFile(delete=False, suffix=code_ext) as code_file:
        code_file.write(code.encode())
        code_file_path = code_file.name

    compile_process = subprocess.Popen(
        compile_cmd + [code_file_path],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )
    compile_stdout, compile_stderr = compile_process.communicate()

    if compile_process.returncode != 0:
        return {'status': 'error', 'output': compile_stderr.decode()}

    run_process = subprocess.Popen(
        run_cmd,
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )
    run_stdout, run_stderr = run_process.communicate(input=input_data.encode())

    if run_process.returncode != 0:
        return {'status': 'error', 'output': run_stderr.decode()}

    return {'status': 'success', 'output': run_stdout.decode()}

@router.post("/execute/python")
async def execute_python(request: CodeExecutionRequest):
    return execute_code(['python3'], ['python3', '-'], request.code, request.inputData, ".py")

@router.post("/execute/java")
async def execute_java(request: CodeExecutionRequest):
    with tempfile.NamedTemporaryFile(delete=False, suffix=".java") as code_file:
        code_file.write(request.code.encode())
        code_file_path = code_file.name
        class_name = code_file.name.split('/')[-1].replace('.java', '')

    compile_cmd = ['javac', code_file_path]
    run_cmd = ['java', '-cp', tempfile.gettempdir(), class_name]
    return execute_code(compile_cmd, run_cmd, request.code, request.inputData, ".java")

@router.post("/execute/cpp")
async def execute_cpp(request: CodeExecutionRequest):
    compile_cmd = ['g++', '-o', tempfile.mktemp(), '']
    run_cmd = [compile_cmd[2]]
    return execute_code(compile_cmd, run_cmd, request.code, request.inputData, ".cpp")

@router.post("/execute/c")
async def execute_c(request: CodeExecutionRequest):
    compile_cmd = ['gcc', '-o', tempfile.mktemp(), '']
    run_cmd = [compile_cmd[2]]
    return execute_code(compile_cmd, run_cmd, request.code, request.inputData, ".c")