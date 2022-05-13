export function footerResizer(currentTarget, footerElement, bodyElement) {
    if (currentTarget.innerHeight > bodyElement.clientHeight) {
        footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
    }
    currentTarget.addEventListener('resize', () => {
        if (currentTarget.innerHeight > bodyElement.clientHeight) {
            footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
        } else {
            footerElement.classList.remove('position-fixed', 'w-100', 'bottom-0');
        }
    })
}

const cartKeyCookie = 'cartItems';

function setCartItemsCookie(cartItems) {
    if (Array.isArray(cartItems)) {
        document.cookie = `${cartKeyCookie}=` + JSON.stringify(cartItems) + '; path=/';
    }
}

function addItemToCart(item) {

}

function getCartCookieValue(cookieData) {
    for (const cookie of cookieData) {
        const key = cookie.split('=')[0];
        const value = cookie.split('=')[1];

        if (key === cartKeyCookie) {
            return JSON.parse(value);
        }
    }

    return null;
}

function getCartItemsCookie() {
    const cookies = document.cookie;
    const cookieData = cookies.split('; ');
    return getCartCookieValue(cookieData);
}

function renderCartItems(cart, numberOfItems) {
    const numberOfItemsSpanElement = document.createElement('span');
    numberOfItemsSpanElement.classList.add('position-absolute', 'top-0', 'start-100', 'translate-middle', 'badge', 'rounded-pill', 'bg-primary');
    numberOfItemsSpanElement.textContent = numberOfItems;

    cart.innerHTML = '';
    cart.textContent = 'Cart';
    cart.appendChild(numberOfItemsSpanElement);
}

export function loadCartItems(){
    const cartItems = getCartItemsCookie();
    const cart = document.getElementById('cartLink');
    if (cartItems === null) {
        setCartItemsCookie([]);
        renderCartItems(cart, 0);
    } else {
        const numberOfItems = cartItems.length;
        renderCartItems(cart, numberOfItems);
    }
}