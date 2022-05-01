window.addEventListener("load", (e) => {
    const currenTarget = e.currentTarget;
    const footerElement = document.getElementsByTagName('footer')[0];
    const bodyElement = document.getElementsByTagName('body')[0];
    if (currenTarget.innerHeight > bodyElement.clientHeight) {
        footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
    }
    currenTarget.addEventListener('resize', (e) => {
        if (currenTarget.innerHeight > bodyElement.clientHeight) {
            footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
        } else {
            footerElement.classList.remove('position-fixed', 'w-100', 'bottom-0');
        }
    })

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

    const accordionButton = document.getElementsByClassName('accordion-button')[0];
    accordionButton.addEventListener('click', (e) => {
        setTimeout(() => {
            if (currenTarget.innerHeight > bodyElement.clientHeight) {
                footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
            } else {
                footerElement.classList.remove('position-fixed', 'w-100', 'bottom-0');
            }
        }, 280);
    });
});