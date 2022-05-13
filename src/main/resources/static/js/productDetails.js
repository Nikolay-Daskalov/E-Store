import {footerResizer, loadCartItems} from './util.js';

const currentTarget = window;
const footerElement = document.getElementsByTagName('footer')[0];
const bodyElement = document.getElementsByTagName('body')[0];
footerResizer(currentTarget, footerElement, bodyElement);

const addToCardBtn = document.getElementById('addToCard');
addToCardBtn.addEventListener('click', (e) => {
    const productId = e.currentTarget.getAttribute('data-productId');
    const cartItems = getCartItemsCookie();
    cartItems.push(productId);
});