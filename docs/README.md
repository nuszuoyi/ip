# Amadeus User Guide

Welcome to **Amadeus**, your personal task management chatbot!

## Getting Started

Amadeus helps you manage your tasks directly from the command line or GUI. You can add, list, mark, unmark, delete, and search for tasks. All your tasks are automatically saved and loaded the next time you start Amadeus.

## Features

### 1. List all tasks

**Command:**  
```
list
```
**Description:**  
Shows all tasks in your list, with their type and completion status.

---

### 2. Mark a task as done

**Command:**  
```
mark <task_number>
```
**Example:**  
`mark 2`

**Description:**  
Marks the specified task as completed.

---

### 3. Mark a task as not done

**Command:**  
```
unmark <task_number>
```
**Example:**  
`unmark 2`

**Description:**  
Marks the specified task as not completed.

---

### 4. Add a ToDo task

**Command:**  
```
todo <description>
```
**Example:**  
`todo borrow book`

**Description:**  
Adds a simple ToDo task.

---

### 5. Add a Deadline task

**Command:**  
```
deadline <description> /by <yyyy-mm-dd>
```
**Example:**  
`deadline return book /by 2023-12-01`

**Description:**  
Adds a task with a deadline. The date must be in `yyyy-mm-dd` format.

---

### 6. Add an Event task

**Command:**  
```
event <description> /from <yyyy-mm-dd> /to <yyyy-mm-dd>
```
**Example:**  
`event project meeting /from 2023-12-01 /to 2023-12-02`

**Description:**  
Adds an event with a start and end date. Dates must be in `yyyy-mm-dd` format.

---

### 7. Delete a task

**Command:**  
```
delete <task_number>
```
**Example:**  
`delete 3`

**Description:**  
Deletes the specified task from your list.

---

### 8. Search for tasks

**Command:**  
```
find <keyword>
```
**Example:**  
`find book`

**Description:**  
Shows all tasks containing the given keyword.

---

### 9. Exit Amadeus

**Command:**  
```
bye
```
**Description:**  
Exits the chatbot and saves your tasks.

---

### 10. Show help

**Command:**  
```
help
```
**Description:**  
Shows a summary of all available commands.

---

## Notes

- Dates must be entered in `yyyy-mm-dd` format (e.g., `2023-12-01`).
- All tasks are saved automatically.
- If you enter an invalid command, Amadeus will prompt you to try again.

---

Enjoy using **Amadeus**!