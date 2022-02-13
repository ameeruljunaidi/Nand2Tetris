// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.

// LOOP:
// if i > n goto END
// RAM[addr] = -1
// addr = add + 32
// i = i + 1
// goto LOOP

// END:
// goto END

// Initializing the variables
(INIT)
    @SCREEN
    D=A
    @addr
    M=D
    @8191
    D=A
    @n
    M=D
    @i
    M=0

(LOOP)
    @i
    D=M
    @n
    D=D-M
    @END
    D;JGT

    @KBD
    D=M
    @WHITE
    D;JEQ
    @BLACK
    D;JGT

(WHITE)
    @addr
    A=M
    M=0
    @CONTINUE
    0;JMP

(BLACK)
    @addr
    A=M
    M=-1
    @CONTINUE
    0;JMP

(CONTINUE)
    @i
    M=M+1
    @addr
    M=M+1
    @LOOP
    0;JMP

(END)
    @INIT
    0;JMP
    @LOOP
    0;JMP

