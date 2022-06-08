import {footerResizer, removeItemFromCart} from './util.js';

const mobileNavBtn = document.querySelector('#mobile-nav-button > span');

const currentTarget = window;
const footerElement = document.getElementsByTagName('footer')[0];
const bodyElement = document.getElementsByTagName('body')[0];
footerResizer(currentTarget, footerElement, bodyElement, [mobileNavBtn]);

const ulElement = document.getElementById('list-group');

const removeBtns = [...document.getElementsByClassName('remove-btn')];

removeBtns.forEach(btn => {
    btn.addEventListener('click', e => {
        e.preventDefault();
        const productId = Number(e.currentTarget.getAttribute('data-product-id'));
        removeItemFromCart(productId);
        const quantity = Number(e.currentTarget.parentElement.previousElementSibling.previousElementSibling.textContent.split(' ')[1]);
        const price = Number(e.currentTarget.parentElement.previousElementSibling.textContent.split(' ')[0]);
        const totalPriceElement = document.getElementsByTagName('h5')[0];
        const totalPrice = Number(totalPriceElement.textContent.split(' ')[1]);
        totalPriceElement.textContent = `Total: ${(totalPrice - (price * quantity)).toFixed(2)} BGN`;
        e.currentTarget.parentElement.parentElement.parentElement.remove();
        footerResizer(currentTarget, footerElement, bodyElement, [mobileNavBtn]);
        if (ulElement.childElementCount === 0) {
            totalPriceElement.remove();

            const mainElement = ulElement.parentElement.parentElement;
            ulElement.parentElement.remove();

            const h3Element = document.createElement('h3');
            h3Element.textContent = 'No Products';

            const divElement = document.createElement('div');
            divElement.classList.add('mt-5');
            divElement.appendChild(h3Element);

            mainElement.appendChild(divElement);
        }
    });
});