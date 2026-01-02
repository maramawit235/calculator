This is a Java-based RMI (Remote Method Invocation) Calculator project.
It demonstrates a client-server architecture where the server performs arithmetic operations and the client interacts via a GUI.

The project supports:

Addition (+)

Subtraction (−)

Multiplication (×)

Division (÷)

The GUI allows user input for two numbers and displays results dynamically.

Features

Remote calculation using Java RMI

Simple Swing-based GUI client

Real-time result display

Keyboard support (+, -, *, /, Enter)

Clean project structure suitable for GitHub

Project Structure
calculator/
 ├─ Calculator.java        # Remote interface
 ├─ CalculatorImpl.java    # Server implementation
 ├─ CalculatorServer.java  # RMI server
 ├─ CalculatorClient.java  # Swing GUI client
 ├─ images/                # Screenshots or images
 └─ README.md

How to Run
1. Compile

Open a terminal in the project folder and run:

javac *.java

2. Start the Server
java CalculatorServer

3. Start the Client

Open a second terminal in the same folder:

java CalculatorClient


The GUI window will appear. Enter two numbers, select an operation, and see the result.

Screenshot
![Calculator GUI](images/screenshot.png)

Notes

Make sure the server is running before starting the client.

Division by zero is handled by the server and will show an error dialog.

All RMI logic is contained in CalculatorServer and CalculatorImpl. The client is purely for user interaction.

Author
Maramawit Esualegn
