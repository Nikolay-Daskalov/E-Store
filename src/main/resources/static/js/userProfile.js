import {footerResizer} from "./util.js";

const mobileNavBtn = document.querySelector('#mobile-nav-button > span');

const currentTarget = window;
const footerElement = document.getElementsByTagName('footer')[0];
const bodyElement = document.getElementsByTagName('body')[0];
footerResizer(currentTarget, footerElement, bodyElement, [mobileNavBtn]);

// const orderBtn = document.getElementById('orders-btn');
// orderBtn.addEventListener('click', async (e) => {
//     const res = await fetch('/users/profile/orders');
//     if (res.status === 204) {
//         return;
//     }
//
//     const data = await res.json();
//
//
// });