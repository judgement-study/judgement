from fastapi import FastAPI, HTTPException, APIRouter
from pydantic import BaseModel
import subprocess
import tempfile
from typing import List, Dict, Union

app = FastAPI()

class CodeExecutionRequest(BaseModel):
    code: str
    inputData: str

router = APIRouter()

def execute_code(compile_cmd: List[str], run_cmd: List[str], code: str, input_data: str, code_ext: str) -> Dict[str, str]:
    """
    주어진 코드를 컴파일하고 실행하여 결과를 반환합니다.

    Args:
        compile_cmd: 컴파일 명령어 리스트.
        run_cmd: 실행 명령어 리스트.
        code: 실행할 소스 코드 문자열.
        input_data: 프로그램에 전달할 입력 데이터 문자열.
        code_ext: 코드 파일의 확장자 (예: '.py', '.java').

    Returns:
        dict: 실행 상태와 프로그램 출력 결과를 포함한 사전.
            'status': 'success' 또는 'error'.
            'output': 프로그램의 표준 출력 또는 표준 오류 내용.
    """
    # 임시 파일에 코드를 작성
    with tempfile.NamedTemporaryFile(delete=False, suffix=code_ext) as code_file:
        code_file.write(code.encode())
        code_file_path: str = code_file.name

    # 컴파일 명령어 실행
    compile_process: subprocess.Popen = subprocess.Popen(
        compile_cmd + [code_file_path],
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )
    compile_stdout, compile_stderr = compile_process.communicate()

    # 컴파일 에러 처리
    if compile_process.returncode != 0:
        return {'status': 'error', 'output': compile_stderr.decode()}

    # 실행 명령어 실행
    run_process: subprocess.Popen = subprocess.Popen(
        run_cmd,
        stdin=subprocess.PIPE,
        stdout=subprocess.PIPE,
        stderr=subprocess.PIPE
    )
    run_stdout, run_stderr = run_process.communicate(input=input_data.encode())

    # 실행 에러 처리
    if run_process.returncode != 0:
        return {'status': 'error', 'output': run_stderr.decode()}

    # 성공 시 출력 반환
    return {'status': 'success', 'output': run_stdout.decode()}

@router.post("/execute/python")
async def execute_python(request: CodeExecutionRequest) -> Dict[str, str]:
    return execute_code(['python3'], ['python3', '-'], request.code, request.inputData, ".py")

@router.post("/execute/java")
async def execute_java(request: CodeExecutionRequest) -> Dict[str, str]:
    with tempfile.NamedTemporaryFile(delete=False, suffix=".java") as code_file:
        code_file.write(request.code.encode())
        code_file_path: str = code_file.name
        class_name: str = code_file.name.split('/')[-1].replace('.java', '')

    compile_cmd: List[str] = ['javac', code_file_path]
    run_cmd: List[str] = ['java', '-cp', tempfile.gettempdir(), class_name]
    return execute_code(compile_cmd, run_cmd, request.code, request.inputData, ".java")

@router.post("/execute/cpp")
async def execute_cpp(request: CodeExecutionRequest) -> Dict[str, str]:
    compile_cmd: List[str] = ['g++', '-o', tempfile.mktemp(), '']
    run_cmd: List[str] = [compile_cmd[2]]
    return execute_code(compile_cmd, run_cmd, request.code, request.inputData, ".cpp")

@router.post("/execute/c")
async def execute_c(request: CodeExecutionRequest) -> Dict[str, str]:
    compile_cmd: List[str] = ['gcc', '-o', tempfile.mktemp(), '']
    run_cmd: List[str] = [compile_cmd[2]]
    return execute_code(compile_cmd, run_cmd, request.code, request.inputData, ".c")
