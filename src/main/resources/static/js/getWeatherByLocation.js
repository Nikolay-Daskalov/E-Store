import {buildSpinner, footerResizer} from './util.js';

const weatherCoordinatesApi = 'https://api.openweathermap.org/geo/1.0/direct?appid=8f7ad215dda0fce71704f079657852b6&q=';

const locationButton = document.getElementsByTagName('button')[1];
locationButton.addEventListener('click', (e) => {
    const weatherSectionElement = document.getElementById('weather-section');
    buildSpinner(weatherSectionElement);

    const buildDangerAlert = (element, text) => {
        element.innerHTML = '';
        const container = document.createElement('div');
        container.style.width = '350px';
        container.classList.add('ms-auto', 'me-auto', 'alert', 'alert-danger', 'mb-0');
        container.setAttribute('role', 'alert');
        container.textContent = text;

        element.appendChild(container);
    }

    const locationInput = e.currentTarget.previousElementSibling.lastElementChild;
    if (locationInput.value.trim() === '') {
        buildDangerAlert(weatherSectionElement, 'Cannot be empty!');
        return;
    }

    fetch(weatherCoordinatesApi + locationInput.value)
        .then(res => res.json())
        .then(data => {
            if (data.length === 0) {
                buildDangerAlert(weatherSectionElement, 'Location not found!');
                return;
            }
            const lat = data[0].lat;
            const lon = data[0].lon;
            const city = data[0].name;
            const country = data[0].country;

            const currentWeatherApi = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lon}&units=metric&appid=8f7ad215dda0fce71704f079657852b6`;

            fetch(currentWeatherApi)
                .then(res => res.json())
                .then(data => {
                    const locationSpan = document.createElement('span');
                    locationSpan.textContent = `${city}, ${country}`;
                    locationSpan.id = 'location';

                    const weatherConditionSpan = document.createElement('span');
                    weatherConditionSpan.id = 'weather-condition';
                    weatherConditionSpan.textContent =
                        data.weather[0].description.split(' ').map((value, index) => {
                            if (index === 0) {
                                return value.charAt(0).toUpperCase() + value.slice(1);
                            }

                            return value;
                        }).join(' ');

                    const fontIcon = document.createElement('i');
                    fontIcon.classList.add('fas', 'fa-temperature-high', 'ms-1');

                    const tempSpan = document.createElement('span');
                    tempSpan.textContent = data.main.temp.toFixed(2);
                    tempSpan.id = 'temp';
                    tempSpan.appendChild(fontIcon);

                    const weatherDataContainer = document.createElement('div');
                    weatherDataContainer.classList.add('d-flex', 'justify-content-around', 'fs-5');
                    weatherDataContainer.appendChild(locationSpan);
                    weatherDataContainer.appendChild(weatherConditionSpan);
                    weatherDataContainer.appendChild(tempSpan);

                    const weatherImg = document.createElement('img');
                    weatherImg.src = `https://openweathermap.org/img/wn/${data.weather[0].icon}@4x.png`;
                    weatherImg.alt = 'weather image';
                    weatherImg.setAttribute('width', '100px');
                    weatherImg.classList.add('d-block', 'ms-auto', 'me-auto');

                    const container = document.createElement('div');
                    container.classList.add('ms-auto', 'me-auto', 'alert', 'alert-success', 'mb-0', 'shadow');
                    container.setAttribute('role', 'alert');
                    container.appendChild(weatherDataContainer);
                    container.appendChild(weatherImg);

                    weatherSectionElement.innerHTML = '';
                    weatherSectionElement.appendChild(container);
                });
        })
        .catch(err => console.error(err));
});