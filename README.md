# 🐍 Snake Game in Java (Console Version)

A simple **console-based Snake-style game** implemented in Java without external libraries.  
The project was created as an educational exercise to practice **OOP, game loops, and basic input handling**.

---

## 🎯 Game Objective

- The **Player (P)** moves on a rectangular field.
- The player must **collect the Gold (G)** first.
- After collecting the gold, the player must **reach the Door (D)** to win.
- The **Snake (S)** chases the player. If it catches the player → **Game Over**.

---

## 🕹️ Game Rules

- The game runs entirely in the **console (ASCII output)**.
- **Controls:**
    - `W` → up
    - `A` → left
    - `S` → down
    - `D` → right
    - `Q` → quit the game
- **Player movement:** one step per turn (no diagonal).
- **Snake movement:** moves toward the player; can move diagonally (1 step per axis per turn).
- **Win condition:** collect the gold and reach the door.
- **Lose condition:** the snake reaches the player’s position.

---

## 🖼️ Example Game Board

```text
###########
#P....G..D#
#.........#
#.........#
#.........#
#.........#
#.....S...#
#.........#
#.........#
###########
```

Symbols:  
- `P` → Player  
- `S` → Snake  
- `G` → Gold  
- `D` → Door  
- `.` → Empty field  
- `#` → Wall (border)  

---

## ⚙️ Technical Details

- **Language:** Java (SE 21+ recommended)
- **Libraries:** Only standard Java (`java.awt.Point`, `java.util.Scanner`)
- **Entry Point:** `Main.java`
- **Main Classes:**
  - `Board` – Handles the game field and rendering
  - `Game` – Main loop and logic
  - `Player` – Player entity
  - `Snake` – Snake enemy logic
  - `Gold` – Collectible item
  - `Door` – Exit to win
  - `Input` – Reads user commands

---

## 🚀 How to Run

### 1. Compile
```bash
javac -d out src/game/*.java src/Main.java
```
### 1. Run
```bash
java -cp out Main
```
## ✅ Features Implemented


- ASCII board rendering
- Player movement (WASD)
- Snake chasing logic (with diagonal steps)
- Gold collection and removal
- Door interaction (win condition)
- Collision detection with the snake (lose condition)
- Game loop with step-by-step input

## 🧪 Test Scenarios

- Player collects gold and reaches door → Win
- Snake reaches player → Lose
- Player tries invalid/diagonal input → Ignored
- Movement at borders → Correctly limited


## 📦 Project Structure
```text
Snake/
│── .idea/                # IntelliJ project settings
│── out/                  # Compiled class files
│── src/                  # Source code
│   └── game/
│       ├── Board.java
│       ├── Door.java
│       ├── Game.java
│       ├── Gold.java
│       ├── Input.java
│       ├── Main.java
│       ├── Player.java
│       └── Snake.java
│── .gitignore
│── Snake.iml
│── README.md             # <── YOU ARE HERE
```
## 👨‍💻 Author

Created as part of a Java learning project to practice OOP, algorithms, and console-based game design.
