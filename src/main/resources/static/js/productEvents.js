const filterBtn = document.getElementById('filterBtn');

function buildSpinner(parent) {
    const spinner = document.createElement('span');
    spinner.classList.add('visually-hidden');
    spinner.textContent = 'Loading...';

    const spinnerContainer = document.createElement('div');
    spinnerContainer.classList.add('spinner-border', 'text-primary');
    spinnerContainer.setAttribute('role', 'status');
    spinnerContainer.appendChild(spinner);

    const container = document.createElement('div');
    container.classList.add('mt-5');
    container.appendChild(spinnerContainer);

    parent.appendChild(container);
}

function clearPagination() {
    document.getElementById('pagination').remove();
}

function toggleFooter() {
    const classList = ['position-fixed', 'bottom-0', 'w-100'];
    const footer = document.getElementsByTagName('footer')[0];
    if (footer.classList.contains('position-fixed')) {
        footer.classList.remove(...classList)
        return;
    }
    footer.classList.add(...classList);
}

async function loadContent() {
    let url = 'http://localhost:8080/products/fitness/data';
    const inputQueryData = [...document.querySelectorAll('.list-group input:checked')];
    const queryObj = {};

    inputQueryData.forEach((e) => {
        const queryAttributeName = e.parentElement.parentElement.firstElementChild.firstElementChild.textContent.toLowerCase();
        const item = e.getAttribute('id');
        if (queryAttributeName === 'price') {
            const values = item.split('Cost')[1]
                .split('[')[1]
                .split(']')[0]
                .split('-');

            const lowestPrice = Number(values[0]);

            if (!queryObj.hasOwnProperty('lowestPrice')) {
                queryObj.lowestPrice = lowestPrice;
            } else {
                queryObj.lowestPrice =
                    Math.min(Number(values[0]), queryObj.lowestPrice);
            }

            const highestPrice = Number(values[1]);

            if (!queryObj.hasOwnProperty('highestPrice')) {
                queryObj.highestPrice = highestPrice;
            } else {
                queryObj.highestPrice = Math.max(queryObj.highestPrice, highestPrice);
            }

            if (isNaN(highestPrice)) {
                delete queryObj.highestPrice;
            }
        } else {
            if (!queryObj.hasOwnProperty(queryAttributeName + 's')) {
                if (queryAttributeName === 'type') {
                    queryObj[queryAttributeName + 's'] = [item.split('-')[0].toUpperCase()];
                } else {
                    queryObj[queryAttributeName + 's'] = [item.split('-')[0]];
                }
            } else {
                if (queryAttributeName === 'type') {
                    queryObj[queryAttributeName + 's'].push(item.split('-')[0].toUpperCase());
                } else {
                    queryObj[queryAttributeName + 's'].push(item.split('-')[0]);
                }
            }
        }
    });

    const keys = Object.keys(queryObj);
    if (keys.length !== 0) {
        let searchString = '?';
        for (let i = 0; i < keys.length; i++) {
            const currentValue = queryObj[keys[i]];
            let currentParam = null;
            if (Array.isArray(currentValue)) {
                currentParam = keys[i] + '=' + currentValue.join(',');
            } else {
                currentParam = keys[i] + '=' + currentValue;
            }
            if (i < keys.length - 1) {
                currentParam += '&';
            }

            searchString += currentParam;
        }

        const urlObj = new URL(url);
        urlObj.search = searchString;
        url = urlObj.toString();
    }
    console.log(url);
}

filterBtn.addEventListener('click', () => {
    const dataContainer = document.querySelector('#content-container-wrapper > .container');
    const clickEvent = new MouseEvent("click", {
        bubbles: true,
        cancelable: true,
        view: window
    });
    document.querySelector('#filterHeading > button').dispatchEvent(clickEvent);
    dataContainer.innerHTML = '';
    buildSpinner(dataContainer);
    clearPagination();
    toggleFooter();
    loadContent();
});