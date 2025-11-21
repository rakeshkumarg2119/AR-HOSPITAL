A lightweight and user-friendly Hospital Management System built using Java (JDK 21), Swing UI, and an embedded H2 database.
Designed to simplify hospital operations such as ambulance allocation, staff monitoring, and emergency management.

🚀 Features
🚑 Real-time ambulance allocation
🏥 Manage doctors, staff, and departments
🗂 Embedded H2 database (no external setup needed)
🖥️ Simple and clean Swing interface
⚡ Fast, portable, and offline
👨‍💻 Beginner-friendly architecture

🛠️ Tech Stack
Component  	  Technology
Language	    Java (JDK 21)
UI Framework	Java Swing
Database	    H2 Embedded Database
IDE	          Eclipse
Build System	Simple Java project

📁 Project Structure
AR-HOSPITAL/
├── src/                     # Java source code
├── bin/                     # Compiled class files
├── resources/
│    └── icons/              # UI graphics
├── app_log.txt              # Log file
├── .classpath               # Eclipse classpath config
├── .project                 # Eclipse project config
└── README.md

✅ Prerequisites
Java JDK 21
Eclipse IDE or any Java-compatible IDE
(Optional) Git

📥 Setup & Installation
1. Clone the repository
git clone https://github.com/rakeshkumarg2119/AR-HOSPITAL.git
cd AR-HOSPITAL
Or download the ZIP.

▶️ Running the Application
Option 1 — Run in Eclipse (Recommended)
Open Eclipse

Go to File → Import → Existing Projects into Workspace

Select the cloned project

Ensure H2 JAR is included in the build path

Run Main.java

Option 2 — Run from Terminal

If all dependencies are in classpath:

java -cp "bin;lib/h2.jar" Main


(Use : instead of ; on macOS/Linux.)

🗄️ Database Information

Uses H2 embedded database

No installation required

Data is stored locally inside the project directory

Optional: open database using H2 console

java -jar h2.jar


Use JDBC URL:

jdbc:h2:./h2-db/hospital

🧰 Log File

app_log.txt stores runtime logs

Can be cleared anytime

🌟 Future Enhancements
  User authentication (admin, staff login)
  Patient management module
  UI improvements (dark mode, modern layout)
  Database backup and export
  Analytics dashboard
👥 Contributors

This project was created by:

  Rakesh Kumar G
  Amutha Rohith P

Contributions are always welcome!
Feel free to submit issues or pull requests.

📜 License — MIT License
MIT License

Copyright (c) 2025 Rakesh Kumar G, Amutha Rohith P

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

