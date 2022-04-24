const lowestCostRangeInputElement = document.getElementById('lowestCostRange');

function changeRangeLabelText(currentTarget, pes, text) {
    pes.textContent = text + currentTarget.value;
}

lowestCostRangeInputElement.addEventListener('input', (e) => {
    changeRangeLabelText(e.currentTarget, e.currentTarget.previousElementSibling,
        'Lowest price: ');
});

const highestCostRangeInputElement = document.getElementById('highestCostRange');
highestCostRangeInputElement.addEventListener('input', (e) => {
    changeRangeLabelText(e.currentTarget, e.currentTarget.previousElementSibling,
        'Highest price: ');
});

