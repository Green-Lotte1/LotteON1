$(function() {

            const swiper = new Swiper('.swiper', {
                // Optional parameters
                direction: 'vertical',
                loop: false,
                speed: 1000,

                // If we need pagination
                pagination: {
                    el: '.swiper-pagination',
                },

                // Navigation arrows
                navigation: {
                    nextEl: '.swiper-button-next',
                    prevEl: '.swiper-button-prev',
                },

                // And if we need scrollbar
                scrollbar: {
                    el: '.swiper-scrollbar',
                    draggable: true
                },
                mousewheel: {
                    invert: false,
                },
            });

            swiper.on('slideChange', function () {
                const slideIndex = swiper.realIndex;

                if(slideIndex == 0){
                    $('header').css('background', 'transparent');
                }else if(slideIndex == 3){
                    $('footer').slideDown(1000);
                }else{
                    $('header').css('background', 'white');
                    $('footer').slideUp(1000);
                }
            })

        });