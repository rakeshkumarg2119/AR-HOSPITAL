A lightweight and user-friendly Hospital Management System built using Java (JDK 21), Swing UI, and an embedded H2 database.
Designed to simplify hospital operations such as ambulance allocation, staff monitoring, and emergency management.

🚀 Features                                                        
        •🚑 Real-time ambulance allocation           
        •🏥 Manage doctors, staff, and departments             
        •🗂 Embedded H2 database (no external setup needed)        
        •🖥️ Simple and clean Swing interface     
        •⚡ Fast, portable, and offline         
        •👨‍💻 Beginner-friendly architecture         

🛠️ Tech Stack        
| Component    | Technology           |
| ------------ | -------------------- |
| Language     | Java (JDK 21)        |
| UI Framework | Java Swing           |
| Database     | H2 Embedded Database |
| IDE          | Eclipse              |
| Build System | Simple Java project  |


📁Project Structure               

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
    •Java JDK 21      
    •Eclipse IDE or any Java-compatible IDE     
    •(Optional) Git    

📥 Setup & Installation             
1. Clone the repository         
   git clone https://github.com/rakeshkumarg2119/AR-HOSPITAL.git    
   cd AR-HOSPITAL    
   Or download the ZIP.     
   
▶️ Running the Application          
Option 1 — Run in Eclipse (Recommended)       
    1. Open Eclipse       
    2. Go to File → Import → Existing Projects into Workspace      
    3. Select the cloned project    
    4. Ensure H2 JAR is included in the build path      
    5. Run Main.java     

Option 2 — Run from Terminal          
    If all dependencies are added:        
    java -cp "bin;lib/h2.jar" Main (Use : instead of ; on macOS/Linux.)       

🗄️ Database Information             
    •Uses H2 embedded database      
    •No setup / installation required       
    •Data stored automatically in the user’s home directory        

⚙️ Database Configuration            
    Your project dynamically creates a secure storage folder inside the user’s home directory:        
    private static final String USER = "sa";         
private static final String PASSWORD = "";           

private static String getDatabaseUrl() {                  
    String userHome = System.getProperty("user.home");            
    java.nio.file.Path dbPath = java.nio.file.Paths.get(userHome, ".data", "test");             
    dbPath.getParent().toFile().mkdirs();                     
    return "jdbc:h2:file:" + dbPath.toString() + ";AUTO_SERVER=TRUE";                
}         

✔️ Explanation          
    •Stores DB under: C:/Users/<username>/.data/test        
    •Automatically creates directory if missing           
    •Uses AUTO_SERVER=TRUE to prevent locking issues       
    •No external DB server required             
    
🧰 Log File                   
    •app_log.txt stores runtime logs          
    •Can be cleared anytime         

🌟 Future Enhancements                     
    •User authentication (admin/staff login)                  
    •Patient management module                   
    •Modern UI theme (dark mode)                    
    •Database backup & export                       
    •Analytics dashboard                        
    
👥 Contributors                           
    This project was created by:                      
        •Rakesh Kumar G       
        •Amutha Rohith P        
💡 Contributions are always welcome!        
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
