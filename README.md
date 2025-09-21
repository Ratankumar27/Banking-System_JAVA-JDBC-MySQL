<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

  <h1>🏦 Banking Management System (Java + JDBC + MySQL)</h1>
  <p>
    A console-based Banking Application built in <b>Java</b>, using <b>JDBC</b> to connect with a <b>MySQL database</b>.
    It provides core banking functionalities such as <b>account creation, secure login, debit/credit transactions,
    money transfer, and balance inquiry</b>, with transaction safety and user privacy in mind.
  </p>

  <h2>🚀 Features</h2>

  <h3>User Management</h3>
  <ul>
    <li>Register new users with name, email, and password.</li>
    <li>Secure login with email and password authentication.</li>
    <li>Prevents duplicate user registration.</li>
  </ul>

  <h3>Bank Account Management</h3>
  <ul>
    <li>Open new bank accounts with unique account numbers.</li>
    <li>Initial deposit and security PIN setup.</li>
    <li>One user can only hold one active account.</li>
  </ul>

  <h3>Transactions (ACID Compliant)</h3>
  <ul>
    <li><b>Debit Money</b> – Withdraw funds with PIN verification.</li>
    <li><b>Credit Money</b> – Deposit securely into account.</li>
    <li><b>Transfer Money</b> – Atomic money transfer between accounts using transactions (<code>commit</code> / <code>rollback</code>).</li>
    <li><b>Check Balance</b> – PIN-protected balance inquiry.</li>
  </ul>

  <h3>Security & Privacy</h3>
  <ul>
    <li>Uses prepared statements to prevent SQL Injection.</li>
    <li>Security PIN verification required for every transaction.</li>
    <li>Passwords stored in DB (can be upgraded to hashing for production use).</li>
    <li>Proper session-like flow: users must log in before performing actions.</li>
  </ul>

  <h3>Error Handling</h3>
  <ul>
    <li>Handles invalid inputs gracefully.</li>
    <li>Prevents overdrafts (withdrawals &gt; balance).</li>
    <li>Ensures only valid accounts can transact.</li>
  </ul>

  <h2>⚙️ How It Works</h2>
  <ol>
    <li><b>Run the application</b> → Console menu appears.</li>
    <li><b>Register or Login</b>:
      <ul>
        <li>Registration saves user data in the <code>user</code> table.</li>
        <li>Login validates email &amp; password.</li>
      </ul>
    </li>
    <li><b>Open an Account</b> (if not already created):
      <ul>
        <li>Provides unique account number.</li>
        <li>Saves initial deposit and PIN in the <code>accounts</code> table.</li>
      </ul>
    </li>
    <li><b>Perform Transactions</b>:
      <ul>
        <li>Debit, Credit, Transfer, and Balance Check.</li>
        <li>Uses <code>connection.setAutoCommit(false)</code> + <code>commit()</code> / <code>rollback()</code> to maintain transaction integrity.</li>
        <li>Example: In transfer, if debit succeeds but credit fails → rollback ensures no half-completed transaction.</li>
      </ul>
    </li>
    <li><b>Log Out / Exit</b> safely.</li>
  </ol>

  <h2>🗄️ Database Schema</h2>

  <h3><code>user</code> Table</h3>
  <table>
    <tr><th>Column</th><th>Type</th></tr>
    <tr><td>full_name</td><td>VARCHAR</td></tr>
    <tr><td>email</td><td>VARCHAR (PK)</td></tr>
    <tr><td>password</td><td>VARCHAR</td></tr>
  </table>

  <h3><code>accounts</code> Table</h3>
  <table>
    <tr><th>Column</th><th>Type</th></tr>
    <tr><td>account_num</td><td>BIGINT (PK)</td></tr>
    <tr><td>full_name</td><td>VARCHAR</td></tr>
    <tr><td>email</td><td>VARCHAR (FK)</td></tr>
    <tr><td>balance</td><td>DOUBLE</td></tr>
    <tr><td>security_pin</td><td>VARCHAR</td></tr>
  </table>

  <h2>📌 Key Highlights</h2>
  <div class="highlight">
    ✅ <b>ACID Transactions</b> → Transfer, Debit, Credit operations ensure atomicity &amp; rollback safety.<br>
    ✅ <b>Security First</b> → PIN-based verification &amp; SQL injection prevention using PreparedStatement.<br>
    ✅ <b>Modular Design</b> → Separated into <code>BankingApp</code>, <code>User</code>, <code>Accounts</code>, <code>AccountsManager</code> classes for clean OOP structure.<br>
    ✅ <b>Scalability</b> → Can be extended to support multiple accounts per user, interest calculation, statements, etc.<br>
    ✅ <b>Industry Practices</b> → JDBC, MySQL, exception handling, transaction management, data integrity enforcement.<br>
    ✅ <b>Privacy-Oriented</b> → User credentials &amp; PIN verified before every sensitive operation.
  </div>

  <h2>🌟 What Makes This Project Unique</h2>
<div class="highlight">
  <p>
    This project is not just a CRUD-based database app — it demonstrates real-world banking transaction handling
    with <strong>atomic operations</strong>, <strong>rollback safety</strong>, security measures, and a modular design.
    It reflects both practical coding skills and system-design thinking.
  </p>

  <ul>
    <li>Database-driven applications</li>
    <li>Security &amp; privacy concerns in finance apps</li>
    <li>Transaction management (a key skill in enterprise software)</li>
    <li>Clean coding with OOP principles</li>
  </ul>
</div>

  <h2>🛠️ Tech Stack</h2>
  <ul>
    <li><b>Language:</b> Java</li>
    <li><b>Database:</b> MySQL</li>
    <li><b>Connectivity:</b> JDBC (MySQL Connector/J)</li>
    <li><b>Tools:</b> IntelliJ IDEA / Eclipse</li>
  </ul>

  <h2>📂 Project Structure</h2>
<pre>
Banking System/
 ├── src/
 │   ├── Accounts.java
 │   ├── AccountsManager.java
 │   ├── BankingApp.java
 │   ├── User.java
 ├── .classpath
 ├── .gitignore
 ├── .project
 └── README.md
</pre>

<h2>▶️ How to Run</h2>
<ol>
  <li>Clone the repository and open it in your IDE (IntelliJ IDEA / Eclipse).</li>
  <li>Ensure MySQL server is running and update the database credentials in the code if required.</li>
  <li>Add the MySQL JDBC Driver (<code>mysql-connector-j</code>) to the project classpath.</li>
  <li>Compile and run the main class:
    <pre>src/BankingApp.java</pre>
    This class contains the <code>main()</code> method and starts the console-based Banking Application.
  </li>
</ol>



</body>
</html>
