import {buildSpinner} from './util.js';

const weatherApi = 'https://api.openweathermap.org/geo/1.0/direct?appid=8f7ad215dda0fce71704f079657852b6&q=';

const locationButton = document.getElementsByTagName('button')[1];
locationButton.addEventListener('click', (e) => {
    const weatherSectionElement = document.getElementById('weather-section');
    buildSpinner(weatherSectionElement);

    fetch(weatherApi + e.currentTarget.previousElementSibling.value)
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                //TODO: add error box for city not found
            }

            const cityName = data[0].name;
            const country = data[0].country;

            console.log(cityName, country);

        })
        .catch(err => console.error(err));
});