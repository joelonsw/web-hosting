let HTMLInput = document.getElementById("html-input-tag");
let CSSInput = document.getElementById("css-input-tag");
let JSInput = document.getElementById("js-input-tag");

function HTMLSelected(sender)
{
    let fileName = sender.value

    if (fileName.slice(-4) !== "html") {
        alert("HTML 파일이 아닙니다.");
        HTMLInput.value = '';
    }
}

function CSSSelected(sender) {
    let fileName = sender.value

    if (fileName.slice(-3) !== "css") {
        alert("CSS 파일이 아닙니다.");
        CSSInput.value = '';
    }
}

function JSSelected(sender) {
    let fileName = sender.value

    if (fileName.slice(-2) !== "js") {
        alert("JS 파일이 아닙니다.");
        JSInput.value = '';
    }
}

function checkUpload() {
    if (UserId.value === '' || HTMLInput.value === '') {
        alert("유저 ID와 HTML 파일은 필수입니다.");
        return false;
    }
    return true;
}
