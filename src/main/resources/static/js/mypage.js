let loginButton = document.getElementById("user-login");
let userInfo = document.getElementById("user-info");
let userInfoImage = document.getElementById("user-info-image");
let userInfoName = document.getElementById("user-info-name");
let mypageNickname = document.getElementById("mypage-nickname");

loginRequest();

async function loginRequest() {
    const userRequest = await fetch("login/userinfo");
    if (userRequest.ok) {
        const userInfoResponse = await userRequest.json();
        setProfile(userInfoResponse);
    }
}

function setProfile(userInfoResponse) {
    userInfo.style.display = "flex";
    userInfoName.innerText = userInfoResponse.name;
    mypageNickname.innerText = "nickname : " + userInfoResponse.name;
    userInfoImage.setAttribute("src", userInfoResponse.imageUrl);
    loginButton.style.display = "none";
}

async function checkLoggedInForDeployBtn() {
    const userRequest = await fetch("login/userinfo");
    if (userRequest.ok) {
        window.location.href="/deploy";
    } else {
        window.location.href="/login";
    }
}
