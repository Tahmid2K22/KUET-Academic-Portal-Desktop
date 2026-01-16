# KUET Academic Portal Desktop

## Introduction
KUET Academic Portal Desktop is a comprehensive desktop application designed to streamline academic management for Khulna University of Engineering & Technology (KUET). Built with JavaFX and MySQL, this portal serves as a central hub for both administrators and students. It allows administrators to manage academic records, schedules, and announcements effectively, while providing students with easy access to their academic data, including routines, results, and assignments.

## Features

### Admin Features
*   **Dashboard:** specialized dashboard for administrative tasks.
*   **Student Management:** Add new student profiles and update existing student information.
*   **Routine Management:** Create and modify class routines for different departments and terms.
*   **Result Management:** Publish and manage student results.
*   **Assignments:** Create and distribute assignments to students.
*   **Attendance:** Record and track student attendance.
*   **Notices:** Publish important notices and announcements for students.

### Student Features
*   **Student Dashboard:** Personalized view showing student details (Name, Roll, Department, CGPA).
*   **Class Routine:** View up-to-date class schedules.
*   **Assignments:** View pending and completed assignments.
*   **Results:** Access academic results and grades.
*   **Attendance:** Monitor attendance records.
*   **Notices:** Browse university notices and announcements.
*   **Contacts:** Access contact information for faculty and staff.

## Technology Stack
*   **Language:** Java 21
*   **UI Framework:** JavaFX 21
*   **Database:** MySQL
*   **Build System:** Maven

## Prerequisites
Before running the application, ensure you have the following installed:
*   **Java Development Kit (JDK) 21** or higher
*   **MySQL Server**
*   **Maven**

## Installation and Setup

1.  **Clone the Repository**
    ```bash
    git clone https://github.com/your-username/kuet-academic-portal-desktop.git
    cd kuet-academic-portal-desktop
    ```

2.  **Database Configuration**
    *   The application uses a MySQL database named `StudentDB`.
    *   By default, it connects using `root` as the username and `123456` as the password.
    *   If your MySQL configuration differs, update the connection details in:
        `src/main/java/com/example/kuet_academic_portal_desktop/Controller/databaseConnect.java`

3.  **Build the Project**
    Open the project in your IDE (IntelliJ IDEA recommended) or build using Maven:
    ```bash
    mvn clean install
    ```

4.  **Run the Application**
    *   Run the main class: `com.example.kuet_academic_portal_desktop.Launcher`
    *   **Note:** On the first run, the application will automatically create the `StudentDB` database and all required tables if they do not exist.

## Usage Guide

### Initial Setup (Important)
Since the database is created empty, you must manually create an Admin account to log in for the first time.
1.  Open your MySQL client or workbench.
2.  Execute the following SQL command:
    ```sql
    USE StudentDB;
    INSERT INTO users (email, password, role) VALUES ('admin@admin.com', 'admin123', 'Admin');
    ```
3.  Launch the application and log in with the credentials created above.

### Navigation
*   **Admin:** Log in to access the admin dashboard. Use the sidebar to navigate between Add Student, Routine, Result, Attendance, and Notice sections.
*   **Student:** Students can log in using their credentials (created by the Admin). They will be directed to the Student Dashboard to view their specific data.





