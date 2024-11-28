# **PJ Task**

**PJ Task** is an Android application designed for efficient task and project management. Built using **Jetpack Compose**, the app provides an intuitive and modern interface to help users organize their activities easily and productively.

---

## **Key Features**
- 📋 **Task Management**: Create, edit, and delete tasks effortlessly.
- 💡 **Modern Design**: Developed with Jetpack Compose for a smooth and visually appealing experience.
- ☁️ **Synchronization**: (Coming soon) Sync tasks across devices.
- 🌐 **API Integration**: Connects with the API at [Task API](https://taskapi.juanfrausto.com/swagger/index.html) for backend operations.

---

## **Technologies and Tools**
- **Kotlin**: Primary development language.
- **Jetpack Compose**: Modern declarative UI framework.
- **Hilt**: Dependency injection.
- **Coroutines**: For efficient multithreading and asynchronous tasks.
- **Flow**: For reactive data and state management.
- **Navigation Component**: Handles navigation between screens.
- **ViewModel**: Manages UI state and business logic.
- **Retrofit**: For network requests to the Task API.

---

## **Project Structure**
The project follows **Clean Architecture** principles for modularity and scalability:

### **1. Data Layer**
- Contains repositories, services, and classes for data sources.
- Examples:
    - `AuthRepositoryImpl`: Implementation of the authentication repository.
    - `AuthService`: Service to interact with the backend API.

### **2. Domain Layer**
- Contains use cases and repository interfaces.
- Examples:
    - `AuthRepository`: Defines authentication operations.
    - `ApiResponse`: Manages success, error, and loading states.

### **3. Presentation Layer**
- Contains UI logic, `ViewModels`, and screens built with Jetpack Compose.
- Examples:
    - `AuthViewModel`: Handles authentication logic.
    - `LoginScreen`: Login screen UI.

---

## **API Integration**
This app uses the **[Task API](https://taskapi.juanfrausto.com/swagger/index.html)** for backend operations. The API handles:
- User authentication and registration.
- Task creation, editing, and deletion.
- Fetching user-specific task data.

---

## **Installation and Setup**

### **1. Clone the Repository**
Clone this project to your local machine using the following command:
```bash
git clone git@github.com:Juanfr97/PJ-TaskApp.git
```

## **2. Set Up the Environment**

   •	Ensure you have Android Studio (version Dolphin or later) installed.
   •	Import the project into Android Studio.
   •	Sync the dependencies using Gradle.

### **3. Run the Application**

   •	Select an emulator or physical device.
   •	Click on the Run button in Android Studio.