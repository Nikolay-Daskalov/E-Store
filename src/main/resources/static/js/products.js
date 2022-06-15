function changeRangeLabelText(currentTarget, pes, text) {
    pes.textContent = text + currentTarget.value;
}

const lowestCostRangeInputElement = document.getElementById('lowestCostRange');
lowestCostRangeInputElement.addEventListener('input', (e) => {
    changeRangeLabelText(e.currentTarget, e.currentTarget.previousElementSibling,
        'Lowest price: ');
});

const highestCostRangeInputElement = document.getElementById('highestCostRange');
highestCostRangeInputElement.addEventListener('input', (e) => {
    changeRangeLabelText(e.currentTarget, e.currentTarget.previousElementSibling,
        'Highest price: ');
});

const pagination = document.querySelector('#pagination > *');
const prevPage = pagination.children[0];
let pages = [...pagination.children];
pages = pages.slice(1, pages.length - 1);
const nextPage = pagination.children[pagination.childElementCount - 1];

const disableClass = 'disabled';
if (pages.length === 1) {
    prevPage.classList.add(disableClass);
    nextPage.classList.add(disableClass);
} else {
    for (let i = 0; i < pages.length; i++) {
        const currentPage = pages[i];

        if (currentPage.classList.contains('active') && i > 0 && i < pages.length) {
            prevPage.firstElementChild.href = pages[i - 1].firstElementChild.href;
        }

        if (currentPage.classList.contains('active') && i >= 0 && i < pages.length - 1) {
            nextPage.firstElementChild.href = pages[i + 1].firstElementChild.href;
        }

        if (currentPage.classList.contains('active') && i === 0) {
            prevPage.classList.add(disableClass);
            break;
        }

        if (currentPage.classList.contains('active') && i === pages.length - 1) {
            nextPage.classList.add(disableClass);
            break;
        }
    }
}