const form = document.getElementsByTagName('form')[1];
const imgInput = document.querySelector('input[type="file"]');
imgInput.addEventListener('change', (e) => {
    e.currentTarget.setCustomValidity('');
    e.currentTarget.classList.remove('is-invalid');
});
form.addEventListener('submit', (e) => {
    console.log(e.defaultPrevented);
    const formObj = Object.fromEntries(new FormData(e.currentTarget));
    const imgType = formObj.image.type;

    if (!imgType.includes('image')) {
        e.preventDefault();
        imgInput.classList.add('is-invalid');
        imgInput.setCustomValidity('invalid image');
        e.currentTarget.reportValidity();
        return;
    }

    if (!formObj.hasOwnProperty('productSizes')) {
        e.preventDefault();
        document.getElementsByTagName('h5')[0].style.color = '#ff2626';
        document.querySelectorAll('input[type="checkbox"]').forEach(e => {
            e.classList.add('is-invalid');
        });
    }
});

