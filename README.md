## Web Hosting Mockup

#### 🎯 목표
- 클라이언트의 정적 파일 (HTML, CSS, JS)를 업로드 시켜준다
- 이를 바탕으로 웹 호스팅을 지원해준다

#### 🤞 구현할 기능 목록 
- [x] 정적 파일 업로드
    - [x] HTML 파일은 사용자당 하나 업로드
    - [x] CSS 파일은 여러개 업로드 가능
    - [x] JS 파일은 여러개 업로드 가능
- [x] 페이지 만들어주기
    - [x] GET: /page/userId 요청에 사용자의 HTML 페이지 반환하도록 구현
        - [x] 해당 HTML 페이지에 userId에 알맞는 CSS, JS 파일 추가해줌
    - [x] GET: /page/userId/resource 요청에 사용자의 CSS, JS를 반환하도록 구현
