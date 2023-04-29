let index = 0;
function reportWindowSize() {
    const carouselCards = document.querySelectorAll(".carousel-card");
    var size = carouselCards.length;
    carouselWrapper.style.width = `${size * 100}%`;
    const cardWidth = carouselCards[0].offsetWidth + 40;
    carouselWrapper.style.transform = `translateX(-${index * cardWidth}px)`;
    return carouselCards[0].offsetWidth + 40;
}

const carouselWrapper = document.querySelector(".carousel-wrapper");
const carouselCards = document.querySelectorAll(".carousel-card");
carouselWrapper.onresize = reportWindowSize;
document.addEventListener("fullscreenchange", reportWindowSize);
window.addEventListener("resize", reportWindowSize);
reportWindowSize();
var forward = document.querySelector(".slide-forward");
forward.addEventListener("click", () => {
    const cardWidth = reportWindowSize();
    if(index < carouselCards.length-1){
        index = index+1;
        carouselWrapper.style.transform = `translateX(-${index * cardWidth}px)`;
    }
})

var backward = document.querySelector(".slide-backward");
backward.addEventListener("click", () => {
    const cardWidth = reportWindowSize();
    if(index > 0){
        index = index-1;
        carouselWrapper.style.transform = `translateX(-${index * cardWidth}px)`;
    }
})