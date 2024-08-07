import json
import logging
import atexit
from locust import HttpUser, task, between, events

# JSON 데이터 로드
with open('data.json', mode='r') as file:
    request_data = json.load(file)

# 로거 설정
logger = logging.getLogger('locust_log')
logger.setLevel(logging.INFO)
file_handler = logging.FileHandler('locust_requests.log', mode='w')
formatter = logging.Formatter('%(asctime)s %(message)s')
file_handler.setFormatter(formatter)
logger.addHandler(file_handler)

# 로그 파일에 테스트 메시지 기록
logger.info("Logger is configured correctly")

# 전역 변수로 사용자 인덱스를 관리
current_user_index = 0
current_finish = 0

class TimedUser(HttpUser):
    wait_time = between(1, 1)  # 대기 시간을 1초로 고정

    def on_start(self):
        global current_user_index
        self.index = current_user_index
        current_user_index += 1
        logger.info(f"User {self.index} started")  # 사용자 시작 시 로그 기록
    
    @task
    def timed_request(self):
        if self.index < len(request_data):
            data = request_data[self.index]
            problem_num = data.get("problem_num")
            logger.info(f"User {self.index} sending request to /problem/{problem_num}")  # 요청 전 로그 기록
            try:
                response = self.client.post(f"/problem/{problem_num}", json=data)
                if response.status_code == 200:
                    logger.info(f"Problem {problem_num}: Success")
                else:
                    logger.error(f"Problem {problem_num}: Failure, Status Code: {response.status_code}")
            except Exception as e:
                logger.error(f"Problem {problem_num}: Exception, Exception: {e}")
        self.stop()

@events.init.add_listener
def on_locust_init(environment, **kwargs):
    logger.info("Locust test is starting")  # 테스트 시작 시 로그 기록
    if not hasattr(environment, "runner") or environment.runner is None: return
    environment.runner.user_classes[0].wait_time = lambda self: self.index  # 각 사용자의 대기 시간을 고유 인덱스로 설정

# 이벤트 리스너 설정
def log_request(request_type, name, response_time, response_length, response, exception, **kwargs):
    global current_finish
    current_finish += 1
    if exception:
        logger.error(f"Request failed: {request_type} {name} Exception: {exception}, finish : {current_finish}")
    else:
        logger.info(f"Request succeeded: {request_type} {name}, finish : {current_finish}")

# 이벤트 리스너 등록
events.request.add_listener(log_request)

# 종료 시 로그 플러시 및 닫기
def shutdown_logger():
    logger.info("Shutting down logger")
    for handler in logger.handlers:
        handler.flush()
        handler.close()

atexit.register(shutdown_logger)

# 로거 테스트 메시지
logger.info("Events are set up correctly")
