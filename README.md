# CrazySnake

CrazySnake is a simple snake game played in the Command Line Interface (CLI). In this game, players control a snake that eats fruit and grows longer with each fruit consumed.

## Technologies Used

- Java: For game logic and CLI display
- C++: For handling keyboard input
- Java Native Interface (JNI): To bridge Java and C++ code

## Features

- Keyboard-controlled snake movement
- Simple and easy-to-understand CLI display
- Snake grows longer with each fruit eaten
- Stage and speed increases with each fruit consumed, every new stage snake length will reset to 3

## Installation

1. Ensure you have JDK (Java Development Kit) and a C++ compiler installed on your system.
2. Clone this repository:
``` bash 
git clone https://github.com/Zachry2906/CrazySnake.git
```
3. Navigate to the project directory, open terminal then run command below to run the game
``` bash 
java -cp . -Djava.library.path=lib src/main/java/id/dojo/Run.java
```

**Notes : every and any changes inside C++ code must be compile with command below (because i'm using linux so i'm gonna be demonstrate it with linux commmand)** [source](https://www.baeldung.com/jni)

1. Create JAVA_HOME variable and assign it with JDK path in your local
``` bash
cd / (go to root folder)
ls -l /etc/alternatives/java (then path to JDK will appear)
export JAVA_HOME=/usr/lib/jvm/jdk-22-oracle-x64/bin/java (assign path to JAVA_HOME)
```
2. At this point, we have all parts we need in place and have a connection between them. We need to build our shared library from the C++ code and run it
``` bash
g++ -c -fPIC -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux src/main/native/id_dojo_Run.cpp -o src/main/native/id_dojo_Run.o
```

3. Create shared object library generated from compiling id_dojo_Run.cpp
``` bash
g++ -shared -fPIC -o lib/libnative.so src/main/native/id_dojo_Run.o -lc
```
4. Then run the program as usual
```bash
java -cp . -Djava.library.path=lib src/main/java/id/dojo/Run.java
```

## How to Play

- Use arrow keys to control the snake's direction
- Eat fruits to increase the snake's length and score
- Avoid hitting the walls or the snake's own tail

## Project Structure

- `Run.java`: Main file containing game logic and CLI display
- `id_dojo_run.cpp`: C++ file for handling keyboard input
- `id_dojo_run.h`: Header file for id_dojo_run.cpp
- `libnative.so`: Shared object library generated from compiling id_dojo_run.cpp

## Contributing

Contributions are always welcome. If you'd like to contribute, please fork the repository and create a pull request, or open an issue for discussion of new features or bug fixes.
