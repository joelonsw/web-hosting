let HTMLInput = document.getElementById("html-input-tag");
let CSSInput = document.getElementById("css-input-tag");
let JSInput = document.getElementById("js-input-tag");

function HTMLSelected(sender) {
    let fileName = sender.value

    if (fileName.slice(-4) !== "html") {
        alert("Only HTML file can be uploaded");
        HTMLInput.value = '';
    }
}

function CSSSelected(sender) {
    let fileName = sender.value

    if (fileName.slice(-3) !== "css") {
        alert("Only CSS files can be uploaded");
        CSSInput.value = '';
    }
}

function JSSelected(sender) {
    let fileName = sender.value

    if (fileName.slice(-2) !== "js") {
        alert("Only JS files can be uploaded");
        JSInput.value = '';
    }
}

function checkUpload() {
    if (HTMLInput.value === '') {
        alert("At least one HTML file is required");
        return false;
    }
    return true;
}