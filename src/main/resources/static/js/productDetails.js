import {addItemToCart, buildAlert} from './util.js';

const addToCardBtn = document.getElementById('addToCard');
addToCardBtn.addEventListener('click', (e) => {
    const quantityInput = document.getElementById('products-count');

    const maxQuantity = Number(quantityInput.getAttribute('max'));

    if (quantityInput.value.trim().length === 0) {
        buildAlert(`Invalid quantity must be between 1 and ${maxQuantity}`, 'danger');
        return;
    }

    const quantityInputValue = Number(quantityInput.value);

    if (quantityInputValue < 1 || quantityInputValue > maxQuantity) {
        buildAlert(`Invalid quantity must be between 1 and ${maxQuantity}`, 'danger');
        return;
    }

    const productId = e.currentTarget.getAttribute('data-product-id');
    addItemToCart(Number(productId), quantityInputValue, document.getElementById('size-select'));
});