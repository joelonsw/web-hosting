let loginButton = document.getElementById("user-login");
let userInfo = document.getElementById("user-info");
let userInfoImage = document.getElementById("user-info-image");
let userInfoName = document.getElementById("user-info-name");

loginRequest();

async function loginRequest() {
    const userRequest = await fetch("login/userinfo");
    console.log(userRequest);
    if (userRequest.ok) {
        const userInfoResponse = await userRequest.json();
        setProfile(userInfoResponse);
    }
}

function setProfile(userInfoResponse) {
    userInfo.style.display = "flex";
    userInfoName.innerText = userInfoResponse.name;
    userInfoImage.setAttribute("src", userInfoResponse.imageUrl);
    loginButton.style.display = "none";
}