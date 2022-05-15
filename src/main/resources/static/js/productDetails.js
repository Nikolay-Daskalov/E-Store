import {footerResizer, addItemToCart, buildAlert} from './util.js';

const currentTarget = window;
const footerElement = document.getElementsByTagName('footer')[0];
const bodyElement = document.getElementsByTagName('body')[0];
footerResizer(currentTarget, footerElement, bodyElement);

const addToCardBtn = document.getElementById('addToCard');
addToCardBtn.addEventListener('click', (e) => {
    const quantityInput = document.getElementById('products-count');

    if (quantityInput.value.trim().length === 0) {
        buildAlert('Invalid quantity must be between 1 and 20', 'danger');
        return;
    }

    const quantityInputValue = Number(quantityInput.value);

    if (quantityInputValue < 1 || quantityInputValue > 20) {
        buildAlert('Invalid quantity must be between 1 and 20', 'danger');
        return;
    }

    const productId = e.currentTarget.getAttribute('data-product-id');
    addItemToCart(Number(productId), quantityInputValue, document.getElementById('size-select'));
});