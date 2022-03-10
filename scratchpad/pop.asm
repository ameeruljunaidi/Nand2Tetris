
@0
D=A
@LCL
D=M+D
@R13
M=D

// Get the value from stack
@SP
M=M-1
A=M
D=M

@R13
A=M
M=D

