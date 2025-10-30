fetch("http://localhost:8080/api/usuarios/login", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ email: "nora@mail.com", clave: "1234" })
})
.then(res => res.json())
.then(data => console.log("Login exitoso", data))
.catch(err => console.error("Error:", err));
