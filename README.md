# BieganBrickGame

## Description

BieganBrickGame is a 2D game inspired by the classic "Brick Games: Race". The game is built using the Model-View-Controller (MVC) pattern, which ensures a clear separation between the game logic, user interface, and game state management.

In the game, you control a brick that moves across the screen, aiming to avoid obstacles and score points. The game features simple controls that make it easy to pick up and play.

## Controls

- **Up Arrow**: Start the game
- **Left Arrow**: Move left
- **Right Arrow**: Move right
- **Spacebar**: Speed up the game

## How to Run

1. **Clone the Repository**

   ```bash
   git clone https://github.com/YourUsername/BieganBrickGame.git
   ```

2. **Build the Project**

   If you are using Gradle to build the project, navigate to the project directory and run:
   ```bash
   ./gradlew build
   ```
   On Windows, you might need to use:
   ```bash
   gradlew.bat build
   ```
3. **Run the Game**

   After building the project, you can run the game using the appropriate command. If the project is packaged as a JAR file, execute:   
   ```bash
   java -jar build/libs/BieganBrickGame-1.0-SNAPSHOT.jar
   ```

##INFO##

The BieganBrickGame is built using the Model-View-Controller (MVC) pattern:

    Model: Handles the game logic, including the game state and gameplay mechanics.
    View: Manages rendering the game interface and displaying information to the player.
    Controller: Interprets user input and updates the game model in response.

Requirements

    Java 17+
    Gradle (if using Gradle for building the project)

Contact

If you have any questions about the game, you can reach out to the author through email or open an issue on GitHub.
