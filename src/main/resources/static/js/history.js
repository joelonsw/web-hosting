let popup = document.querySelector(".popup");
let pages = document.querySelector(".pages");
let pagesDeployed = document.querySelector(".pages-deployed");
let pagesList = document.querySelector(".pages-list");
let pagesCloseBtn = document.querySelector(".pages-close-btn");

popup.addEventListener('click', function () {
    appear(popup);
    setTimeout(function () { popup.style.display = "none" }, 200);
    disappear(pages);
    setTimeout(function () { pages.style.display = "block"; }, 200);
    getPages();
});

pagesCloseBtn.addEventListener('click', function () {
    appear(pages);
    setTimeout(function () { pages.style.display = "none"; }, 200);
    disappear(popup);
    setTimeout(function () { popup.style.display = "block" }, 200);
});

function appear(element) {
    element.classList.remove('appear-history');
    element.classList.add('disappear-history');
}

function disappear(element) {
    element.classList.remove('disappear-history');
    element.classList.add('appear-history');
}

function getPages() {
    var requestOptions = {
        method: 'GET'
    };

    fetch("/all-pages", requestOptions)
        .then(response => response.json())
        .then(result => addPages(result))
        .catch(error => console.log(error));
}

function addPages(pages) {
    pagesDeployed.innerText = "총 " + pages.length + "개의 페이지가 배포됐어요!";
    pagesList.innerHTML = "";
    pages.forEach((page) => addSinglePage(page));
}

function addSinglePage(page) {
    pagesList.innerHTML += '<a class="single-page" href="https://easy-deploy.kr/pages/' + page  + '" target="_blank">' + page + '</a>';
}
