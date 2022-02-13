// LOOP:
// if i > n goto END
// RAM[addr] = -1
// addr = add + 32
// i = i + 1
// goto LOOP

// END:
// goto END

// Initializing the variables
    @SCREEN
    D=A
    @addr
    M=D
    @R0
    D=M
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

    @addr
    A=M
    M=-1
    @i
    M=M+1
    @addr
    M=M+1
    @LOOP
    0;JMP

(END)
    @END
    0;JMP

