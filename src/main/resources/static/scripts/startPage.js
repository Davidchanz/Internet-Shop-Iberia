const carouselWrapper = document.querySelector(".carousel-wrapper");
        const carouselCards = document.querySelectorAll(".carousel-card");
        var size = carouselCards.length;
        carouselWrapper.style.width = `${size * 100}%`;
        const cardWidth = carouselCards[0].offsetWidth + 40;

        let index = 0;
        var forward = document.querySelector(".slide-forward");
        forward.addEventListener("click", () => {
            if(index < carouselCards.length-1){
                index = index+1;
                carouselWrapper.style.transform = `translateX(-${index * cardWidth}px)`;
            }
        })

        var backward = document.querySelector(".slide-backward");
        backward.addEventListener("click", () => {
            if(index > 0){
                index = index-1;
                carouselWrapper.style.transform = `translateX(-${index * cardWidth}px)`;
            }
        })