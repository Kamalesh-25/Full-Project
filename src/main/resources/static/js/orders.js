document.addEventListener("DOMContentLoaded", function () {
    const cartContainer = document.getElementById("cart-container");
    const totalPriceEl = document.getElementById("totalPrice");
    const checkoutBtn = document.getElementById("checkoutBtn");
  
    let cart = JSON.parse(localStorage.getItem("cart")) || [];
  
    function displayCart() {
      cartContainer.innerHTML = "";
      let total = 0;
  
      if (cart.length === 0) {
        cartContainer.innerHTML = "<p>Your cart is empty.</p>";
        checkoutBtn.disabled = true;
        return;
      }
  
      cart.forEach((book, index) => {
        const div = document.createElement("div");
        div.className = "book-card";
        div.innerHTML = `
          <h4>${book.title}</h4>
          <p>Price: ₹${book.price}</p>
          <button onclick="removeFromCart(${index})">Remove</button>
        `;
        cartContainer.appendChild(div);
        total += book.price;
      });
  
      totalPriceEl.innerText = total;
      checkoutBtn.disabled = false;
    }
  
    window.removeFromCart = function (index) {
      cart.splice(index, 1);
      localStorage.setItem("cart", JSON.stringify(cart));
      displayCart();
    };
  
    checkoutBtn.addEventListener("click", () => {
      const userId = localStorage.getItem("userId");
      if (!userId) return alert("Please login to checkout.");
  
      Promise.all(cart.map(book =>
        fetch("http://localhost:8080/orders", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({
            userId,
            booktitle: book.title,
            total: book.price
          })
        })
      )).then(() => {
        alert("Order placed successfully!");
        localStorage.removeItem("cart");
        location.reload();
      });
    });
  
    displayCart();
  });
  