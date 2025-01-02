function calculateTotalPrice() {
    for (const order of document.querySelectorAll(".order")) {
        const products = order.querySelectorAll(".product");

        const totalPrice = Array.from(products).reduce((prev, curr) => {
            const price = parseFloat(curr.querySelector(".price").innerText);
            const quantity = parseInt(curr.querySelector(".quantity").innerText, 10);
            return prev + (price * quantity);
        }, 0);

        const totalPriceElement = order.querySelector(".totalPrice");
        if (totalPriceElement) totalPriceElement.innerText = totalPrice.toFixed(2);
    }
}

document.addEventListener("DOMContentLoaded", calculateTotalPrice);