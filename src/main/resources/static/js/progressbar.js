let progressBar = document.querySelector(".progress-bar");
let slidePage = document.querySelector(".slide-page");

let firstList = document.getElementById("first_list");
let secondList = document.getElementById("second_list");
let thirdList = document.getElementById("third_list");
let fourthList = document.getElementById("fourth_list");
let fifthList = document.getElementById("fifth_list");

function firstNextBtn() {
    secondList.classList.add('active');
    slidePage.style.marginLeft = "-20%";
}

function secondNextBtn() {
    thirdList.classList.add('active');
    slidePage.style.marginLeft = "-40%";
}

function thirdNextBtn() {
    fourthList.classList.add('active');
    slidePage.style.marginLeft = "-60%";
}

function fourthNextBtn() {
    fifthList.classList.add('active');
    slidePage.style.marginLeft = "-80%";
}

function secondPrevBtn() {
    secondList.classList.remove('active');
    slidePage.style.marginLeft = "0%";
}

function thirdPrevBtn() {
    thirdList.classList.remove('active');
    slidePage.style.marginLeft = "-20%";
}

function fourthPrevBtn() {
    fourthList.classList.remove('active');
    slidePage.style.marginLeft = "-40%";
}

function fifthPrevBtn() {
    fifthList.classList.remove('active');
    slidePage.style.marginLeft = "-60%";
}

function deleteAllListItem() {
    firstList.classList.remove('active');
    secondList.classList.remove('active');
    thirdList.classList.remove('active');
    fourthList.classList.remove('active');
    fifthList.classList.remove('active');
}

function firstListItemBtn() {
    deleteAllListItem();
    firstList.classList.add('active');
    slidePage.style.marginLeft = "0%";
}

function secondListItemBtn() {
    deleteAllListItem();
    firstList.classList.add('active');
    secondList.classList.add('active');
    slidePage.style.marginLeft = "-20%";

}

function thirdListItemBtn() {
    deleteAllListItem();
    firstList.classList.add('active');
    secondList.classList.add('active');
    thirdList.classList.add('active');
    slidePage.style.marginLeft = "-40%";

}

function fourthListItemBtn() {
    deleteAllListItem();
    firstList.classList.add('active');
    secondList.classList.add('active');
    thirdList.classList.add('active');
    fourthList.classList.add('active');
    slidePage.style.marginLeft = "-60%";
}

function fifthListItemBtn() {
    deleteAllListItem();
    firstList.classList.add('active');
    secondList.classList.add('active');
    thirdList.classList.add('active');
    fourthList.classList.add('active');
    fifthList.classList.add('active');
    slidePage.style.marginLeft = "-80%";
}