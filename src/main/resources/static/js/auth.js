document.addEventListener("DOMContentLoaded", () => {
    const regForm = document.getElementById("registerForm");
    const loginForm = document.getElementById("loginForm");
  
    if (regForm) {
      regForm.addEventListener("submit", e => {
        e.preventDefault();
        const username = document.getElementById("username").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const role = document.getElementById("role").value;
    
        fetch("http://localhost:8080/users/register", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({ username, email, password, role })
        })
          .then(res => res.text())
          .then(msg => {
            alert(msg);
    
            localStorage.setItem("role", role);
    
            window.location.href = "login.html";
          });
      });
    }
    
  
    if (loginForm) {
      loginForm.addEventListener("submit", e => {
        e.preventDefault();
        const username = document.getElementById("username").value;
        const password = document.getElementById("password").value;
  
        fetch("http://localhost:8080/users/login", {
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: new URLSearchParams({ username, password })
        })
          .then(res => res.json())
          .then(user => {
            localStorage.setItem("userId", user.id);
            localStorage.setItem("username", user.username);
            alert("Login successful");
            window.location.href = "index.html";
          })
          .catch(() => alert("Invalid credentials"));
      });
    }
  });
  