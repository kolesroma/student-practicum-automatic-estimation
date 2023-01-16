// https://codepen.io/Johnny-Dragovic/pen/JjvaojP

let seconds = document.querySelector(".seconds");
let minutes = document.querySelector(".minutes");
let indicator = document.querySelector(".indicator");

let play = document.querySelector(".fa-play");
let pause = document.querySelector(".fa-pause");
let stop = document.querySelector(".fa-stop");
let counter = -1;
let pomoValue = 25 * 60;
let counterValue = pomoValue;

function playButtonClick() {
    if (counter == -1 && counterValue > 0) {
        counter = setInterval(() => {
            let rest = --counterValue;
            let min = Math.floor(rest / 60);
            let sec = Math.floor(rest % 60);
            seconds.textContent = sec.toString().padStart(2, "0");
            minutes.textContent = min.toString().padStart(2, "0");
            indicator.style.strokeDashoffset = 600 - (rest / pomoValue) * 600;

            if (rest == 0) {
                clearInterval(counter);
            }
        }, 1000);
    }
}
window.addEventListener("load", (event) => {
    playButtonClick();
});