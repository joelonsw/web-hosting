///////// 랜딩 페이지 /////////

let main = document.querySelector(".main");
let circle = document.querySelector(".circle");
let deploy = document.querySelector(".deploy");

function deployBtn() {
    main.classList.add('disappear');
    circle.classList.add('disappear');
    setTimeout(function () { main.style.display = "none"; circle.style.display = "none" }, 500);
    deploy.classList.add('appear');
    setTimeout(function () { deploy.style.display = "block"; }, 500);
}

///////// 페이지 이름 중복 확인 /////////

let pageNameInput = document.querySelector(".urlInput");
let pageNameUsablity = document.querySelector(".pageNameUsablity");
let pageName = null;
let pageNameUsable;

pageNameInput.addEventListener('keypress', function(event) {
    if (event.key === 'Enter') {
        checkPageName();
    }
})

function checkPageName() {
    if (pageNameInput.value.length == 0 || pageNameInput.value.trim().length == 0) {
        alert("URL로 사용하려면 최소 한 글자는 필요합니다");
        pageNameInput.value = "";
        return;
    }

    var requestOptions = {
        method: 'GET'
    };

    fetch("/validation?pageName=" + pageNameInput.value, requestOptions)
        .then(response => response.text())
        .then(result => checkPageNameUsable(result, pageNameInput.value))
        .catch(error => showPageNameServerError());
}

function checkPageNameUsable(result, pageNameToUse) {
    if ("true" == result) {
        pageName = pageNameToUse;
        pageNameUsable = true;
        showPageNameUsableTrue();
        return;
    }
    pageName = null;
    pageNameUsable = false;
    showPageNameUsableFalse();
}

function showPageNameUsableTrue() {
    pageNameUsablity.innerHTML = '<p style = "color: green">✔ 사용 가능 합니다</p>'
}

function showPageNameUsableFalse() {
    pageNameUsablity.innerHTML = '<p style = "color: red">❌ 사용할 수 없습니다</p>'
}

function showPageNameServerError() {
    pageNameUsablity.innerHTML = '<p style = "color: gray">🛠 서버와의 통신에 실패했습니다. 잠시 후 다시 시도해주세요</p>'
}

///////// HTML 첨부 페이지 /////////

let htmlArea = document.querySelector(".htmlarea");
let htmlUpload = document.querySelector(".htmlUpload");
let htmlFileBtn = document.querySelector(".htmlFileBtn");
let htmlFileInput = document.querySelector(".htmlFileInput");
let htmlFile = null;

htmlArea.addEventListener("dragover", (event) => {
    event.preventDefault();
    htmlArea.classList.add("active");
})

htmlArea.addEventListener("dragleave", () => {
    htmlArea.classList.remove("active");
})

htmlArea.addEventListener("drop", (event) => {
    event.preventDefault();
    showInputHtmlFile(event.dataTransfer.files[0]);
})

htmlFileBtn.onclick = () => {
    htmlFileInput.click();
}

htmlFileInput.addEventListener("change", function() {
    showInputHtmlFile(this.files[0]);
})

function showInputHtmlFile(inputFile) {
    if (inputFile.type != "text/html") {
        alert("HTML 파일을 첨부해주세요");
        htmlArea.classList.remove("active");
        return;
    }

    htmlFile = inputFile;
    htmlArea.style.display = "none";
    htmlUpload.style.display = "block";
    let fileName = htmlFile.name;
    htmlUpload.innerHTML = "<div id=" + fileName + ">" + fileName + "<button onclick=deleteHtmlFile('" + fileName + "')>❌</button></div>";
}

function deleteHtmlFile(fileName) {
    if (htmlFile.name == fileName) {
        htmlFileInput.value = "";
        htmlFile = null;
        htmlUpload.style.display = "none";
        htmlArea.style.display = "block";
        htmlArea.classList.remove("active");
    }
}

///////// CSS 첨부 페이지 /////////

let cssArea = document.querySelector(".cssarea");
let cssUpload = document.querySelector(".cssUpload");
let cssFileBtn = document.querySelector(".cssFileBtn");
let cssFileInput = document.querySelector(".cssFileInput");
let cssFiles = null;

cssArea.addEventListener("dragover", (event) => {
    event.preventDefault();
    cssArea.classList.add("active");
})

cssArea.addEventListener("dragleave", () => {
    cssArea.classList.remove("active");
})

cssArea.addEventListener("drop", (event) => {
    event.preventDefault();
    showInputCssFiles(Array.from(event.dataTransfer.files));
})

cssFileBtn.onclick = () => {
    cssFileInput.click();
}

cssFileInput.addEventListener("change", function() {
    showInputCssFiles(Array.from(this.files));
})

function showInputCssFiles(inputFiles) {
    for(let i = 0; i < inputFiles.length; i++) {
        if (inputFiles[i].type != "text/css") {
            alert("CSS 파일을 첨부해주세요");
            cssArea.classList.remove("active");
            return;
        }
    }

    cssFiles = inputFiles;
    cssArea.style.display = "none";
    cssUpload.style.display = "block";
    let cssInnerHtml = "";
    for (let i = 0; i < cssFiles.length; i++) {
        let fileName = cssFiles[i].name;
        cssInnerHtml += "<div id=" + fileName + ">" + fileName + "<button onclick=deleteCssFile('" + fileName + "')>❌</button></div>";
    }
    cssUpload.innerHTML = cssInnerHtml;
}

function deleteCssFile(fileName) {
    for (let i = 0; i < cssFiles.length; i++) {
        if (cssFiles[i].name == fileName) {
            let cssDom = document.getElementById(cssFiles[i].name);
            cssDom.remove();
            cssFiles.splice(i, 1);
        }
    }

    if (cssFiles.length == 0) {
        cssFileInput.value = "";
        cssUpload.style.display = "none";
        cssArea.style.display = "block";
        cssArea.classList.remove("active");
    }
}

///////// JS 첨부 페이지 /////////

let jsArea = document.querySelector(".jsarea");
let jsUpload = document.querySelector(".jsUpload");
let jsFileBtn = document.querySelector(".jsFileBtn");
let jsFileInput = document.querySelector(".jsFileInput");
let jsFiles = null;

jsArea.addEventListener("dragover", (event) => {
    event.preventDefault();
    jsArea.classList.add("active");
})

jsArea.addEventListener("dragleave", () => {
    jsArea.classList.remove("active");
})

jsArea.addEventListener("drop", (event) => {
    event.preventDefault();
    showInputJsFiles(Array.from(event.dataTransfer.files));
})

jsFileBtn.onclick = () => {
    jsFileInput.click();
}

jsFileInput.addEventListener("change", function() {
    showInputJsFiles(Array.from(this.files));
})

function showInputJsFiles(inputFiles) {
    for (let i = 0; i < inputFiles.length; i++) {
        if (inputFiles[i].type != "text/javascript") {
            alert("JS 파일을 첨부해주세요");
            jsArea.classList.remove("active");
            return;
        }
    }

    jsFiles = inputFiles;
    jsArea.style.display = "none";
    jsUpload.style.display = "block";

    let jsInnerHtml = "";
    for (let i = 0; i < jsFiles.length; i++) {
        let fileName = jsFiles[i].name;
        jsInnerHtml += "<div id=" + fileName + ">" + fileName + "<button onclick=deleteJsFile('" + fileName + "')>❌</button></div>";
    }
    jsUpload.innerHTML = jsInnerHtml;
}

function deleteJsFile(fileName) {
    for (let i = 0; i < jsFiles.length; i++) {
        if (jsFiles[i].name == fileName) {
            let jsDom = document.getElementById(jsFiles[i].name);
            jsDom.remove();
            jsFiles.splice(i, 1);
        }
    }

    if (jsFiles.length == 0) {
        jsFileInput.value = "";
        jsUpload.style.display = "none";
        jsArea.style.display = "block";
        jsArea.classList.remove("active");
    }
}

///////// 배포 페이지 /////////

function deployToServer() {
    deployConditionCheck();

    var formdata = new FormData();
    formdata.append("pageName", pageName);
    formdata.append("htmlFile", htmlFile, htmlFile.name);
    if (cssFiles != null) {
        for (let i = 0; i < cssFiles.length; i++) {
            formdata.append("cssFile", cssFiles[i], cssFiles[i].name);
        }
    }
    if (jsFiles != null) {
        for (let i = 0; i < jsFiles.length; i++) {
            formdata.append("jsFile", jsFiles[i], jsFiles[i].name);
        }
    }

    var requestOptions = {
        method: 'POST',
        body: formdata
    };

    fetch("/deploy", requestOptions)
        .then(response => response.text())
        .then(result => showResultPage())
        .catch(error => console.log('error', error));
}

function deployConditionCheck() {
    if (pageNameUsable == null) {
        alert("URL 중복 확인이 필요합니다.");
        firstListItemBtn();
        return;
    }

    if (pageNameUsable == false) {
        alert("사용할 수 없는 URL 입니다.");
        firstListItemBtn();
        return;
    }

    if (pageName == null || htmlFile == null || htmlFile.type != "text/html") {
        alert("URL 설정과 HTML 첨부는 필수입니다");
        if (pageName == null) {
            firstListItemBtn();
            return;
        }
        if (htmlFile == null) {
            secondListItemBtn();
            return;
        }
    }
}

///////// 결과 페이지 /////////

let result = document.querySelector(".result");
let link = document.querySelector(".link");

function showResultPage() {
    deploy.classList.remove('appear');
    deploy.classList.add('disappear');
    setTimeout(function () { deploy.style.display = "none"; }, 500);
    link.innerText = "https://easy-deploy.kr/pages/" + pageName;
    link.href = "https://easy-deploy.kr/pages/" + pageName;
    circle.classList.remove('disappear');
    circle.classList.add('appear');
    result.classList.add('appear');
    setTimeout(function () { result.style.display = "flex"; circle.style.display = "block"; }, 500);

    setUpKakao();
}

function setUpKakao() {
    Kakao.init("f6417783bcfb18cf9b2ff4992c846b62");

    Kakao.Link.createDefaultButton({
        container: '#kakaotalk',
        objectType: 'feed',
        content: {
            title: 'Easy Deploy에서 제 페이지를 배포했어요!',
            link: {
                webUrl: link.innerText,
                mobileWebUrl: link.innerText,
            },
            imageUrl: 'https://easy-deploy.kr/image/logo.png',
        }
    });
}
