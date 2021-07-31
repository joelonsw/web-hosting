let loginButton = document.getElementById("user-login");
let userInfo = document.getElementById("user-info");
let userInfoImage = document.getElementById("user-info-image");
let userInfoName = document.getElementById("user-info-name");
let mypageNickname = document.getElementById("mypage-nickname");
let mypageWebsite = document.getElementById("mypage-website");

let baseUserPageUrl = "https://joel-web-hosting.o-r.kr/pages/";

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
    mypageNickname.innerText = userInfoResponse.name;
    userInfoImage.setAttribute("src", userInfoResponse.imageUrl);
    loginButton.style.display = "none";
    checkDeployed(userInfoResponse);
}

function checkDeployed(userInfoResponse) {
    if (userInfoResponse.deployed) {
        let userPageUrl = baseUserPageUrl + userInfoResponse.name;
        mypageWebsite.innerText = userPageUrl;
        mypageWebsite.href = userPageUrl;
    } else {
        mypageWebsite.innerText = "No Web sites have been deployed yet!";
    }
}
