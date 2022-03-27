#!/bin/bash
cd /Users/ajjunaidi/Dev/Nand2Tetris/tools/HackAssembler/src
javac HackAssembler.java
java HackAssembler $1
rm *.class