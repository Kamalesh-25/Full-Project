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
        totalPriceEl.innerText = 0;
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
    }
  
    window.removeFromCart = function (index) {
      cart.splice(index, 1);
      localStorage.setItem("cart", JSON.stringify(cart));
      displayCart();
    };
  
    checkoutBtn.addEventListener("click", async () => {
        const userId = localStorage.getItem("userId");
        if (!userId) return alert("Please login to checkout.");
      
        try {
          const promises = cart.map(book => {
            return fetch("http://localhost:8080/orders", {
              method: "POST",
              headers: { "Content-Type": "application/x-www-form-urlencoded" },
              body: new URLSearchParams({
                userId,
                booktitle: book.title,
                total: book.price
              })
            });
          });
      
          await Promise.all(promises);
      
          alert("Order placed successfully!");
          localStorage.removeItem("cart");
          location.reload();
        } catch (error) {
          alert("Error placing order. Please try again.");
          console.error(error);
        }
      });
    });