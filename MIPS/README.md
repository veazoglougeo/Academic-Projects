# MIPS Linked List Project 🧠

This project implements a **dynamic singly linked list** using **MIPS Assembly Language**.  
The user can interact with the list through a menu-driven interface using system calls (syscall) and keyboard input.


## 📋 Features

- Create a linked list (manually allocating memory with syscall 9)
- Add new nodes (with integer `ID` and `Value`)
- Maintain sorted insertion based on node value
- Delete a node by `ID`
- Display the full list contents
- User interaction via ASCII choices (A, B, C, D, X)

---

## 🧠 Data Structures

Each node contains:
- ID (integer)
- Value (integer)
- Pointer to the next node (address)

