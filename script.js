let currentUser = null;

async function login() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const res = await fetch("http://localhost:8080/api/auth/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  });
  const data = await res.json();

  if (data && data.id) {
    currentUser = data;
    document.querySelector(".login-container").style.display = "none";
    document.getElementById("dashboard").style.display = "block";
  } else {
    alert("Invalid login!");
  }
}

function showLeaveForm() {
  document.getElementById("leaveForm").style.display = "block";
}

async function applyLeave() {
  const reason = document.getElementById("reason").value;
  const startDate = document.getElementById("start").value;
  const endDate = document.getElementById("end").value;

  const res = await fetch("http://localhost:8080/api/leaves/apply", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ reason, startDate, endDate, employee: currentUser })
  });

  if (res.ok) {
    alert("Leave applied!");
    loadLeaves();
  }
}

async function loadLeaves() {
  const res = await fetch("http://localhost:8080/api/leaves/all");
  const leaves = await res.json();
  let html = "<h3>All Leave Requests</h3><ul>";
  leaves.forEach(l => {
    html += `<li>${l.reason} | ${l.status} | ${l.startDate} to ${l.endDate}</li>`;
  });
  html += "</ul>";
  document.getElementById("leavesList").innerHTML = html;
}
