export function footerResizer(currentTarget, footerElement, bodyElement, onButtons) {
    if (currentTarget.innerHeight > bodyElement.clientHeight) {
        footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
    }
    currentTarget.addEventListener('resize', () => {
        if (currentTarget.innerHeight > bodyElement.clientHeight) {
            footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
        } else {
            footerElement.classList.remove('position-fixed', 'w-100', 'bottom-0');
        }
    });

    onButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            setTimeout(() => {
                if (currentTarget.innerHeight > bodyElement.clientHeight) {
                    footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
                } else {
                    footerElement.classList.remove('position-fixed', 'w-100', 'bottom-0');
                }
            }, 250);
        });
    });
}

const cartKeyCookie = 'cartProducts';
const cart = document.getElementById('cartLink');

function setCartItemsCookie(cartItems) {
    document.cookie = `${cartKeyCookie}=` + encodeURIComponent(JSON.stringify(cartItems)) + '; path=/; samesite=strict;';
}

export function addItemToCart(itemId, quantity, sizeSelect) {
    const cartItems = getCartItemsCookie();

    const doesItemExist = cartItems.products.some(cartItem => {
        if (cartItem.id === itemId) {
            return true;
        }
    });

    const cartItem = {
        id: itemId,
        quantity
    };

    if (sizeSelect !== null) {
        if (sizeSelect.selectedIndex === 0) {
            buildAlert('Select size', 'warning')
            return;
        }
        cartItem.size = sizeSelect.value;
    }

    if (!doesItemExist) {
        cartItems.products.push(cartItem);
        setCartItemsCookie(cartItems);
        renderCartItems(cart, cartItems.products.length);
        buildAlert('Product added to cart', 'success');
    } else {
        buildAlert('Product already in cart', 'warning');
    }
}

export function removeItemFromCart(id) {
    const cartItems = getCartItemsCookie();
    const itemIndex = cartItems.products.findIndex(item => item.id === id);
    cartItems.products.splice(itemIndex, 1);
    setCartItemsCookie(cartItems);
    renderCartItems(cart, cartItems.products.length);
}

export function buildAlert(text, type) {
    const closeBtnAlert = document.createElement('button');
    closeBtnAlert.setAttribute('type', 'button');
    closeBtnAlert.setAttribute('data-bs-dismiss', 'alert');
    closeBtnAlert.setAttribute('aria-label', 'Close');
    closeBtnAlert.classList.add('btn-close');

    const alertDiv = document.createElement('div');
    alertDiv.classList.add('alert', `alert-${type}`, 'alert-dismissible', 'fade', 'show', 'ms-auto', 'me-auto');
    alertDiv.setAttribute('role', 'alert');
    alertDiv.textContent = text;
    alertDiv.id = 'alert-div';
    alertDiv.appendChild(closeBtnAlert);

    const parentContainer = document.getElementById('parent-container');

    if (parentContainer.childElementCount === 2) {
        parentContainer.appendChild(alertDiv);
    } else {
        parentContainer.lastElementChild.remove();
        parentContainer.appendChild(alertDiv);
    }
}

function getCartCookieValue(cookieData) {
    for (const cookie of cookieData) {
        const key = cookie.split('=')[0];
        const value = cookie.split('=')[1];

        if (key === cartKeyCookie) {
            return JSON.parse(decodeURIComponent(value));
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

export function loadCartItems() {
    const cartItems = getCartItemsCookie();
    if (cartItems === null) {
        setCartItemsCookie({products: []});
        renderCartItems(cart, 0);
    } else {
        const numberOfItems = cartItems.products.length;
        renderCartItems(cart, numberOfItems);
    }
}

export function buildSpinner(HTMLElement) {
    const divElementId = 'spinnerElement';
    if (HTMLElement.firstElementChild !== null && HTMLElement.firstElementChild.id === divElementId) {
        return;
    }

    const spanElement = document.createElement('span');
    spanElement.classList.add('visually-hidden');
    spanElement.textContent = 'Loading...';

    const divElement = document.createElement('div');
    divElement.classList.add('text-primary', 'spinner-border');
    divElement.setAttribute('role', 'status');
    divElement.id = divElementId;
    divElement.appendChild(spanElement);

    HTMLElement.innerHTML = '';
    HTMLElement.appendChild(divElement);
}