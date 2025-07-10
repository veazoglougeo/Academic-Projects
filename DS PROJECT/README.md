# Distributed Room Booking App 🏨📱

This project is a **distributed room reservation system** consisting of:
- A **Java-based backend server**
- An **Android mobile application** for clients and managers


## 🔧 Features

### 📱 Android Client App
- Register and log in as **Client** or **Manager**
- **Clients** can:
  - Search for rooms using filters (area, price, rating)
  - View room details and availability
  - Book rooms for selected dates
  - Rate their stays
- **Managers** can:
  - Add rooms and availability
  - View all bookings
  - Monitor average ratings per room

### 🖥️ Java Backend (Distributed)
- Uses a **master–reducer architecture**:
  - `MasterServer` receives requests
  - `Reducer` nodes process booking and filtering
- **Parser** handles request interpretation
- **Threaded processing** for concurrent user handling