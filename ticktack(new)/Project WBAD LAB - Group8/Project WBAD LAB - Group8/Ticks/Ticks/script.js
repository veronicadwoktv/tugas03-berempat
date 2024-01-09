let slideIndex = 0;
showSlides();

function showSlides() {
  let slides = $(".mySlides");
  let dots = $(".dot");
  
  slides.hide();
  
  slideIndex++;
  if (slideIndex > slides.length) {
    slideIndex = 1;
  }
  
  dots.removeClass("active");
  
  slides.eq(slideIndex - 1).show();
  dots.eq(slideIndex - 1).addClass("active");
  
  setTimeout(showSlides, 2000); // Ganti gambar setiap 2 detik
}

