export function footerResizer(currentTarget, footerElement, bodyElement) {
    if (currentTarget.innerHeight > bodyElement.clientHeight) {
        footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
    }
    currentTarget.addEventListener('resize', (e) => {
        if (currentTarget.innerHeight > bodyElement.clientHeight) {
            footerElement.classList.add('position-fixed', 'w-100', 'bottom-0');
        } else {
            footerElement.classList.remove('position-fixed', 'w-100', 'bottom-0');
        }
    })
}

window.addEventListener("load", (e) => {
    const currentTarget = e.currentTarget;
    const footerElement = document.getElementsByTagName('footer')[0];
    const bodyElement = document.getElementsByTagName('body')[0];
    footerResizer(currentTarget, footerElement, bodyElement);
});