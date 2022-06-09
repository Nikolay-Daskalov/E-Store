const orderBtn = document.getElementById('orders-btn');
orderBtn.addEventListener('click', (e) => {
    fetch('/users/profile/orders')
        .then(res => {
            if (res.status === 204){
                console.log('No orders');
            } else {
                return res.json();
            }
        })
        .then(data => {

        })
        .catch(err => console.error(err));
});