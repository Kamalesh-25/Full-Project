document.addEventListener("DOMContentLoaded", function () {
  const bookContainer = document.getElementById("book-container");
  const searchInput = document.getElementById("searchInput");
  const addBookBtn = document.getElementById("addBookBtn");

  let allBooks = [];

  // Fetch and display all books
  fetch("http://localhost:8080/books")
    .then(res => res.json())
    .then(data => {
      allBooks = data;
      displayBooks(data);
    });

  // Display books function
  function displayBooks(books) {
    bookContainer.innerHTML = "";
    books.forEach(book => {
      const div = document.createElement("div");
      div.className = "book-card";
      div.innerHTML = `
        <h3>${book.title}</h3>
        <p><strong>Author:</strong> ${book.author}</p>
        <p><strong>Category:</strong> ${book.category}</p>
        <p><strong>Price:</strong> ₹${book.price}</p>
        <button onclick="addToCart('${book.title}', ${book.price})">Add to Cart</button>
      `;
      div.onclick = () => showBookPopup(book);
      bookContainer.appendChild(div);
    });
  }

  // Search books
  searchInput.addEventListener("input", () => {
    const searchValue = searchInput.value.toLowerCase();
    const filtered = allBooks.filter(book =>
      book.title.toLowerCase().includes(searchValue) ||
      book.author.toLowerCase().includes(searchValue) ||
      book.category.toLowerCase().includes(searchValue) ||
      book.price.toLowerCase().includes(searchValue)
        );
    displayBooks(filtered);
  });

  // Add Book (only for admin)
  addBookBtn.addEventListener("click", () => {
    const role = localStorage.getItem("role");
    if (role.toLocaleLowerCase() !== "admin") {
      return alert("Only admin can access the Add Book page.");
    }
    window.location.href = "add-book.html";
  });
  
  // Book detail popup
  window.showBookPopup = function (book) {
    const popup = document.createElement("div");
    popup.className = "popup";
    popup.innerHTML = `
      <div class="popup-content">
        <h2>${book.title}</h2>
        <p><strong>Author:</strong> ${book.author}</p>
        <p><strong>Category:</strong> ${book.category}</p>
        <p><strong>Price:</strong> ₹${book.price}</p>
        <p><strong>Description:</strong> ${book.description || "No description available."}</p>
        <button onclick="addToCart('${book.title}', ${book.price})">Add to Cart</button>
        <button onclick="this.closest('.popup').remove()">X</button>
      </div>
    `;
    document.body.appendChild(popup);
  };

  // Order a book
  window.addToCart = function addToCart(title, price) {
    const userId = localStorage.getItem("userId");
    if (!userId) {
      alert("Please login to place orders.");
      return;
    }
  
    fetch("http://localhost:8080/orders", {
      method: "POST",
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      body: new URLSearchParams({
        userId: userId,
        booktitle: title,
        total: price
      })
    })
      .then(response => {
        if (!response.ok) {
          throw new Error("HTTP status " + response.status);
        }
        return response.text();
      })
      .then(message => alert(message))
      .catch(error => {
        console.error("Order failed:", error);
        alert("Order failed. Please check console for details.");
      });
  }  
  
});  
