const removeBtns = [...document.getElementsByClassName('remove-btn')];

removeBtns.forEach(btn => {
    btn.addEventListener(e => {
        e.preventDefault();
        const productId = Number(e.currentTarget.getAttribute('data-product-id'));


    });
});