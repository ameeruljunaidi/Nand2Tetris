// function SimpleFunction.test 2
(SimpleFunction.test)
   @0
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   @0
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push local 0
   @0
   D=A
   @LCL
   D=D+M
   @R13
   M=D
   @R13
   A=M
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push local 1
   @1
   D=A
   @LCL
   D=D+M
   @R13
   M=D
   @R13
   A=M
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// add
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @SP
   M=M-1
   @SP
   A=M
   M=D+M
   @SP
   M=M+1
   
// not
   @SP
   M=M-1
   @SP
   A=M
   M=!M
   @SP
   M=M+1
   
// push argument 0
   @0
   D=A
   @ARG
   D=D+M
   @R13
   M=D
   @R13
   A=M
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// add
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @SP
   M=M-1
   @SP
   A=M
   M=D+M
   @SP
   M=M+1
   
// push argument 1
   @1
   D=A
   @ARG
   D=D+M
   @R13
   M=D
   @R13
   A=M
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// sub
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @SP
   M=M-1
   @SP
   A=M
   M=M-D
   @SP
   M=M+1
   
// return
   // endFrame (R13) = LCL
   @LCL
   D=M
   @R13
   M=D
   // retAddr (R14) = *(endFrame - 5)
   @5
   D=A
   @R13
   D=M-D
   @R14
   M=D
   // ARG = pop
   @SP
   M=M-1
   A=M
   D=M
   @ARG
   A=M
   M=D
   @ARG
   D=M
   @SP
   M=D+1
   // restore THAT
   @1
   D=A
   @R13
   D=M-D
   A=D
   D=M
   @THAT
   M=D
   // restore THIS
   @2
   D=A
   @R13
   D=M-D
   A=D
   D=M
   @THIS
   M=D
   // restore ARG
   @3
   D=A
   @R13
   D=M-D
   A=D
   D=M
   @ARG
   M=D
   // restore LCL
   @4
   D=A
   @R13
   D=M-D
   A=D
   D=M
   @LCL
   M=D
   // goto retAddr
   @R14
   A=M
   0;JMP
   
