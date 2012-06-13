PrintSim
========

To build (assuming you have a JUnit JAR somewhere in your $HOME:

javac -sourcepath src/ -cp $HOME/junit/junit-4.10.jar:src/:PrintSim/bin/ -d PrintSim/bin/ *.java

To run simulation:

java -cp ../bin/ "printsim.SimDriver"

To run tests:

java -cp ../bin/ "testing.<name_of_test_suite>"
