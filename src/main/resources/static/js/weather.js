import {footerResizer} from "./util.js";

const mobileNavBtn = document.querySelector('#mobile-nav-button > span');

const currentTarget = window;
const footerElement = document.getElementsByTagName('footer')[0];
const bodyElement = document.getElementsByTagName('body')[0];
footerResizer(currentTarget, footerElement, bodyElement, [mobileNavBtn]);