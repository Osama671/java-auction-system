document.addEventListener('DOMContentLoaded', (event) => {
    let auctions = [];
    document.querySelectorAll('.countdown').forEach(function(element) {
        var endTime = element.getAttribute('data-end-time');
        auctions.push({
            element: element,
            endTime: new Date(endTime)
        });

    });

    function updateCountdown(auction) {
        const now = new Date().getTime();
        const distance = auction.endTime - now;

        if (distance < 0) {
            auction.element.innerHTML = "Auction Ended";
            return;
        }

        const days = Math.floor(distance / (1000 * 60 * 60 * 24));
        const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((distance % (1000 * 60)) / 1000);

        auction.element.innerHTML = `${days}d ${hours}h ${minutes}m ${seconds}s`;
    }

    function initializeCountdowns() {
        auctions.forEach(function(auction) {
            updateCountdown(auction);
            setInterval(() => updateCountdown(auction), 1000);
        });
    }
    initializeCountdowns();
});