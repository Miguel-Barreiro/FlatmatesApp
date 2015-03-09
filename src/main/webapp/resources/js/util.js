var funFactPollCounter = 0;

function scrollPage(location) {
    window.location.hash = location;
}

function checkFunFactPoll() {
    if (funFactPollCounter === 3) {
        PF('funFactPollWar').stop();
    }
    funFactPollCounter++;
}

