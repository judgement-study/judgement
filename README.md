# 🏆 온라인 저지 플랫폼

온라인 저지 플랫폼에 오신 것을 환영합니다! 이 프로젝트는 사용자가 다양한 문제를 통해 알고리즘 실력을 테스트하고 향상시킬 수 있는 종합 시스템입니다. 백준과 같은 플랫폼을 목표로 하고 있습니다.

![License](https://img.shields.io/github/license/judgement-study/judgement) ![Stars](https://img.shields.io/github/stars/judgement-study/judgement) ![Issues](https://img.shields.io/github/issues/judgement-study/judgement)

## 🛠️ 주요 기능

- **알고리즘 실력 검증**: 데이터 분석을 통해 사용자의 알고리즘별 실력을 검증합니다.
- **FastAPI 분석 서버**: 빠르고 효율적인 분석 서버를 FastAPI로 구현하였습니다.
- **백엔드**: Spring 기반으로 구축되었으며, PostgreSQL을 사용합니다.
- **프론트엔드**: React를 사용하여 사용자 친화적인 인터페이스를 제공합니다.

## 📂 프로젝트 구조

```plaintext
root
├── backend
├── frontend
├── analysis_server
└── README.md
```markdown
## 🚀 시작하기

### 필수 조건

- Node.js
- PostgreSQL
- Java (Spring Boot)
- Python (FastAPI)

### 설치 및 실행

1. **백엔드 서버**
    ```bash
    cd backend
    ./mvnw spring-boot:run
    ```

2. **프론트엔드 서버**
    ```bash
    cd frontend
    npm install
    npm start
    ```

3. **분석 서버**
    ```bash
    cd analysis_server
    pip install -r requirements.txt
    uvicorn app.main:app --reload
    ```

## 📊 데이터 분석

- 다양한 알고리즘 문제를 풀고, 분석 서버를 통해 실력을 검증합니다.
- 사용자 별, 알고리즘 별 성능을 분석하여 피드백을 제공합니다.

## 🤝 기여하기

기여를 환영합니다! Pull Request를 보내주시거나, 이슈를 등록해 주세요. 자세한 내용은 `CONTRIBUTING.md` 파일을 참고해 주세요.

## 📄 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다. 자세한 내용은 `LICENSE` 파일을 참조하세요.

## 📧 연락처

궁금한 점이나 문의 사항이 있으시면 [이메일 주소]로 연락해 주세요.
