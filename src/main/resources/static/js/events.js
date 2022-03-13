// function changeTipContentTextSizeHandler(e) {
//     const elements = document.querySelectorAll('#second-section-div > *:nth-child(n+2)');
//     console.log(elements);
//     elements.forEach((element) => {
//         if (e.currentTarget.innerWidth < 521) {
//             if (element.childElementCount != 0) {
//                 element.firstChild.classList.remove('fs-5');
//                 element.firstChild.classList.add('fs-6');
//             } else {
//                 element.classList.remove('fs-5');
//                 element.classList.add('fs-6');
//             }
//
//         } else {
//             if (element.childElementCount != 0) {
//                 element.firstChild.classList.remove('fs-6');
//                 element.firstChild.classList.add('fs-5');
//             } else {
//                 element.classList.remove('fs-6');
//                 element.classList.add('fs-5');
//             }
//         }
//     });
// }
//
// window.addEventListener('load', changeTipContentTextSizeHandler);