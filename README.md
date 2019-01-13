# Checkers
Checkers, completed in May 2017, was the final assignment of my Object Oriented Programming with Java course. The aim of the assignment was to get to grips with Java's graphics library facilities, to demonstrate the design and programming skills acquired during the course, and to show off some creativity.

## Assignment
For the assignment I decided to design and develop a checkers application. For simplicity, the Russian, Brazilian and Czech version of checkers was be implemented and the majority of the effort was spent on implementing a clean and usable GUI for two players with JavaFX.

Due to time constraints, not all of the features were implemented fully (for example, the overall and individual timers and the ability to move pieces when the board is rotated). However, I was quite pleased with the overall aesthetics and usability of the GUI and some of the functionality that had been implemented (for example, the ability to rotate the board).

## Requirements

In order to compile and run the application, a version of the JDK and JavaFX is required.

JavaFX is not included in the OpenJDK and was removed from Oracle's JDK from JDK 11 onwards. If you are using OpenJDK or JDK 11 and higher then you will need to download JavaFX separately.

#### Workaround

If you do not wish to download and configure JavaFX manually, one quick and dirty workaround is to install JDK 8 (which can be found [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)), as JavaFX is is supported in JDK 8 until at least 2022. Once you have JDK 8 installed, you can use this to compile and run the program with JDK 8.

##### Mac Example

1. Check which JDKs are installed using ```/usr/libexec/java_home -V```. The output should look something like:
```
Matching Java Virtual Machines (2):
    11, x86_64:	"AdoptOpenJDK 11"	/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
    1.8.0_191, x86_64:	"Java SE 8"	/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home

/Library/Java/JavaVirtualMachines/adoptopenjdk-11.jdk/Contents/Home
```
2. Install JDK 8 if it isn't already.
3. Specify that you want to use JDK 8 using ```export JAVA_HOME=`/usr/libexec/java_home -v 1.8` ```.
4. Confirm that you are using the correct JDK using ```java -version```. The output should look something like:
```
java version "1.8.0_191"
Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
```

## Usage
The program can be compiled using the *makefile*:
```
make
```

The program can then be run using the *makefile*:
```
make run
```


## Disclaimer
I have have confirmed with the University that I am able to publish my solution to this assignment. However, as there is the possibility that a similar assignment may still be used by the University, I have been informed that I should state that *this work cannot be used without my permission* and if it is submitted by as current student as their own, they will be investigated for plagiarism.

## Acknowledgments
The song used for the theme music is called ‘Funky Jazzy Loop’ and can be downloaded for free [here](https://www.dl-sounds.com/royalty-free/funky-jazzy-loop/).

The applause played when somebody wins (*People_Applause_short.wav*) was recorded by my friend Alex Cummings.

The crown used for the king pieces was taken was taken from [here](http://www.clipartpanda.com/clipart_images/ princess-crown-clipart-free-31355335).
