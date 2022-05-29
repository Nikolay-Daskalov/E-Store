import {buildSpinner} from './util.js';

const locationSearchInput = document.getElementById('search-input');
locationSearchInput.addEventListener('input', e => {
    const weatherSectionElement = document.getElementById('weather-section');
    buildSpinner(weatherSectionElement)

    setTimeout(() => {
        console.log("timeout working")
    }, 300);
});