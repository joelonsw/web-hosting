## Web Hosting

### 🎯 목표
- 클라이언트의 정적 파일 (HTML, CSS, JS)를 업로드 시켜준다
- 이를 하나의 페이지로 볼 수 있도록 웹 호스팅을 지원해준다

### 🎁 배포 URL
- URL: http://joel-web-hosting.o-r.kr/

### 🛠 사용 방법
- 클라이언트는 **[유저 ID]**, **[1개의 HTML 파일]**, 해당 HTML 파일을 꾸며줄 __[여러 개의 CSS/JS 파일]__ 을 업로드한다. 
- 클라이언트는 `http://joel-web-hosting.o-r.kr/pages/유저ID`를 통해 자신의 배포 URL을 얻을 수 있다

### 🤞 구현할 기능 목록 
- [x] 정적 파일 업로드
    - [x] HTML 파일은 사용자당 하나 업로드
    - [x] CSS 파일은 여러개 업로드 가능
    - [x] JS 파일은 여러개 업로드 가능
- [x] 페이지 만들어주기
    - [x] GET: /page/userId 요청에 사용자의 HTML 페이지 반환하도록 구현
        - [x] 해당 HTML 페이지에 userId에 알맞는 CSS, JS 파일 추가해줌
    - [x] GET: /page/userId/resource 요청에 사용자의 CSS, JS를 반환하도록 구현
    - [x] 유저 아이디에 따라 페이지 저장 폴더를 만들어주기
    - [x] 다 만들어지면 페이지 링크를 알려주기

### ✂ 리팩토링 중점 사안
- [ ] HTML만 필수! CSS와 JS 코드는 Optional로 저장토록 수정
- [x] 파일 확장자
    - [x] 프론트 단에서 넘길 때 파일 확장자를 확정짓기
    - [x] 유저 ID 혹은 HTML 파일이 없다면 제출을 못하도록 막기

### 🙌 Contributors
- 우테코 3기 **서니**
- 우테코 3기 **웨지**
