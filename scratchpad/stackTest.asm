    @17
    D=A
    @SP
    A=M
    M=D
    @SP
    M=M+1

    @17
    D=A
    @SP
    A=M
    M=D
    @SP
    M=M+1

    @SP
    M=M-1
    @SP
    A=M
    D=M
    @SP
    M=M-1
    @SP
    A=M
    D=D-M
    @EQ
    D;JEG
    @NEQ
    0;JEQ

(EQ)
    @SP
    A=M
    M=-1
    @

(NEQ)
    @SP
    A=M
    M=0
    @
