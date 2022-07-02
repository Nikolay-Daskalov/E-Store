import {buildSpinner} from './util.js';

const orderBtn = document.getElementById('orders-btn');
orderBtn.addEventListener('click', (e) => {
    e.preventDefault();
    const sectionContainer = document.getElementById('contentSection');
    buildSpinner(sectionContainer);
    fetch('/users/orders')
        .then(res => {
            if (res.status === 204) {
                return new Promise((resolve, reject) => {
                    resolve('No content');
                });
            }
            return res.json();
        })
        .then(data => {
            if (typeof data === 'string') {
                const noOrdersH2 = document.createElement('h2');
                noOrdersH2.textContent = 'No orders';
                noOrdersH2.classList.add('mb-0');
                sectionContainer.innerHTML = '';
                sectionContainer.appendChild(noOrdersH2);
                return;
            }

            const parentUL = document.createElement('ul');
            parentUL.classList.add('list-group', 'orders', 'ms-auto', 'me-auto', 'mb-5');

            data.forEach((order, index) => {
                const currentOrderBtn = document.createElement('button');
                currentOrderBtn.classList.add('accordion-button', 'collapsed');
                currentOrderBtn.setAttribute('type', 'button');
                currentOrderBtn.setAttribute('data-bs-toggle', 'collapse');
                currentOrderBtn.setAttribute('data-bs-target', `#collapse${index + 1}`);
                currentOrderBtn.setAttribute('aria-expanded', 'false');
                currentOrderBtn.setAttribute('aria-controls', `collapse${index + 1}`);
                currentOrderBtn.textContent = `Order #${order.id}, purchased on: ${order.created}`;

                const accordionHeaderH2 = document.createElement('h2');
                accordionHeaderH2.classList.add('accordion-header');
                accordionHeaderH2.setAttribute('id', `heading${index + 1}`);
                accordionHeaderH2.appendChild(currentOrderBtn);

                const listGroupUl = document.createElement('ul');
                listGroupUl.classList.add('list-group');

                order.orderDetails.forEach((orderDetail) => {
                    const orderLiItem = document.createElement('li');
                    orderLiItem.classList.add('list-group-item');
                    const titleSpan = document.createElement('span');
                    titleSpan.classList.add('d-block');
                    titleSpan.textContent = `${orderDetail.product.brand} ${orderDetail.product.model},`;

                    const orderInfoSpan = document.createElement('span');
                    orderInfoSpan.classList.add('d-block');
                    orderInfoSpan.textContent = `Quantity: ${orderDetail.quantity} X ${orderDetail.product.price} BGN`;

                    orderLiItem.append(titleSpan, orderInfoSpan);

                    listGroupUl.appendChild(orderLiItem);
                });

                const totalPriceP = document.createElement('p');
                totalPriceP.classList.add('mb-0', 'mt-1');
                totalPriceP.textContent = `Total price: ${order.totalPrice} BGN`;

                listGroupUl.appendChild(totalPriceP);

                const listGroupContainer = document.createElement('div');
                listGroupContainer.classList.add('accordion-body');
                listGroupContainer.appendChild(listGroupUl);

                const parentContainer = document.createElement('div');
                parentContainer.classList.add('accordion-collapse', 'collapse');
                parentContainer.setAttribute('id', `collapse${index + 1}`);
                parentContainer.setAttribute('aria-labelledby', `heading${index + 1}`);
                parentContainer.setAttribute('data-bs-parent', `#accordionParent${index + 1}`);
                parentContainer.appendChild(listGroupContainer);

                const accordionItemDiv = document.createElement('div');
                accordionItemDiv.classList.add('accordion-item');
                accordionItemDiv.appendChild(accordionHeaderH2);
                accordionItemDiv.appendChild(parentContainer);

                const accordionParent = document.createElement('div');
                accordionParent.classList.add('accordion');
                accordionParent.setAttribute('id', `accordionParent${index + 1}`);
                accordionParent.appendChild(accordionItemDiv);

                const parentItemLI = document.createElement('li');
                parentItemLI.classList.add('list-group-item', 'list-group-item-primary', 'mb-1');
                parentItemLI.appendChild(accordionParent);

                parentUL.appendChild(parentItemLI);
            });

            sectionContainer.innerHTML = '';
            sectionContainer.appendChild(parentUL);
        })
        .catch(err => {
            console.error(err);
        });
});