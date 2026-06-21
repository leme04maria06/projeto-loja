function abrirCarrinho(){

    document
        .getElementById(
            "miniCarrinho"
        )
        .style.right = "0";
}

function fecharCarrinho(){

    document
        .getElementById(
            "miniCarrinho"
        )
        .style.right = "-400px";
}

// =========================================
// CARRINHO LATERAL
// =========================================

function abrirCarrinho(){

    document
        .getElementById("miniCarrinho")
        .style.right = "0";
}

function fecharCarrinho(){

    document
        .getElementById("miniCarrinho")
        .style.right = "-400px";
}


// =========================================
// CARROSSEL
// =========================================

const banners = [

    "/img/banners/banner1.png",
    "/img/banners/banner2.png",
    "/img/banners/banner3.jpg",
    "/img/banners/banner4.jpg"

];

let bannerAtual = 0;

function atualizarBanner(){

    const imagem =
        document.getElementById("banner-img");

    if(imagem){

        imagem.src =
            banners[bannerAtual];
    }
}

function proximoBanner(){

    bannerAtual++;

    if(bannerAtual >= banners.length){

        bannerAtual = 0;
    }

    atualizarBanner();
}

function bannerAnterior(){

    bannerAtual--;

    if(bannerAtual < 0){

        bannerAtual =
            banners.length - 1;
    }

    atualizarBanner();
}


// =========================================
// INICIAR CARROSSEL AUTOMÁTICO
// =========================================

document.addEventListener(
    "DOMContentLoaded",
    function(){

        setInterval(
            proximoBanner,
            5000
        );

    }
);