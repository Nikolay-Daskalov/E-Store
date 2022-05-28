import {loadCartItems} from './util.js';

document.cookie = 'cartProducts=' + encodeURIComponent(JSON.stringify({products: []})) + '; path=/; samesite=strict; max-age=0;';

loadCartItems();