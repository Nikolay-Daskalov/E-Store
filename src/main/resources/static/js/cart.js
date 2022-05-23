import {footerResizer, removeItemFromCart} from './util.js';

const currentTarget = window;
const footerElement = document.getElementsByTagName('footer')[0];
const bodyElement = document.getElementsByTagName('body')[0];
footerResizer(currentTarget, footerElement, bodyElement);

const ulElement = document.getElementById('list-group');

const removeBtns = [...document.getElementsByClassName('remove-btn')];

removeBtns.forEach(btn => {
    btn.addEventListener('click', e => {
        e.preventDefault();
        const productId = Number(e.currentTarget.getAttribute('data-product-id'));
        removeItemFromCart(productId);
        e.currentTarget.parentElement.parentElement.parentElement.remove();
        footerResizer(currentTarget, footerElement, bodyElement);
        if (ulElement.childElementCount === 0){
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