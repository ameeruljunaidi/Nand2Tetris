#!/bin/bash
cd /Users/ajjunaidi/Dev/Nand2Tetris/tools/VMTranslator/src
javac VMTranslator.java
java VMTranslator $1
rm *.class