// initializing pointers
   @256
   D=A
   @SP
   M=D
   
// call Sys.init 0
   // push returnAddress
   @Sys.init$ret.0
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push LCL
   @LCL
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push ARG
   @ARG
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THIS
   @THIS
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THAT
   @THAT
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // reposition ARG
   @SP
   D=M
   @0
   D=D-A
   @5
   D=D-A
   @ARG
   M=D
   // reposition LCL
   @SP
   D=M
   @LCL
   M=D
   // goto functionName
   @Sys.init
   0;JMP
   // label for return address
(Sys.init$ret.0)
   
// function Class1.set 0
(Class1.set)
   
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
   
// pop static 0
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @Class1.0
   M=D
   
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
   
// pop static 1
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @Class1.1
   M=D
   
// push constant 0
   @0
   D=A
   @SP
   A=M
   M=D
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
   A=M-D
   D=M
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
   D=M+1
   @SP
   M=D
   // restore THAT
   @1
   D=A
   @R13
   A=M-D
   D=M
   @THAT
   M=D
   // restore THIS
   @2
   D=A
   @R13
   A=M-D
   D=M
   @THIS
   M=D
   // restore ARG
   @3
   D=A
   @R13
   A=M-D
   D=M
   @ARG
   M=D
   // restore LCL
   @4
   D=A
   @R13
   A=M-D
   D=M
   @LCL
   M=D
   // goto retAddr
   @R14
   A=M
   0;JMP
   
// function Class1.get 0
(Class1.get)
   
// push static 0
   @Class1.0
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push static 1
   @Class1.1
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
   A=M-D
   D=M
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
   D=M+1
   @SP
   M=D
   // restore THAT
   @1
   D=A
   @R13
   A=M-D
   D=M
   @THAT
   M=D
   // restore THIS
   @2
   D=A
   @R13
   A=M-D
   D=M
   @THIS
   M=D
   // restore ARG
   @3
   D=A
   @R13
   A=M-D
   D=M
   @ARG
   M=D
   // restore LCL
   @4
   D=A
   @R13
   A=M-D
   D=M
   @LCL
   M=D
   // goto retAddr
   @R14
   A=M
   0;JMP
   
// function Sys.init 0
(Sys.init)
   
// push constant 6
   @6
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push constant 8
   @8
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// call Class1.set 2
   // push returnAddress
   @Class1.set$ret.1
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push LCL
   @LCL
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push ARG
   @ARG
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THIS
   @THIS
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THAT
   @THAT
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // reposition ARG
   @SP
   D=M
   @2
   D=D-A
   @5
   D=D-A
   @ARG
   M=D
   // reposition LCL
   @SP
   D=M
   @LCL
   M=D
   // goto functionName
   @Class1.set
   0;JMP
   // label for return address
(Class1.set$ret.1)
   
// pop temp 0 
   @0
   D=A
   @5
   D=D+A
   @R13
   M=D
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @R13
   A=M
   M=D
   
// push constant 23
   @23
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push constant 15
   @15
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// call Class2.set 2
   // push returnAddress
   @Class2.set$ret.2
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push LCL
   @LCL
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push ARG
   @ARG
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THIS
   @THIS
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THAT
   @THAT
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // reposition ARG
   @SP
   D=M
   @2
   D=D-A
   @5
   D=D-A
   @ARG
   M=D
   // reposition LCL
   @SP
   D=M
   @LCL
   M=D
   // goto functionName
   @Class2.set
   0;JMP
   // label for return address
(Class2.set$ret.2)
   
// pop temp 0 
   @0
   D=A
   @5
   D=D+A
   @R13
   M=D
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @R13
   A=M
   M=D
   
// call Class1.get 0
   // push returnAddress
   @Class1.get$ret.3
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push LCL
   @LCL
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push ARG
   @ARG
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THIS
   @THIS
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THAT
   @THAT
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // reposition ARG
   @SP
   D=M
   @0
   D=D-A
   @5
   D=D-A
   @ARG
   M=D
   // reposition LCL
   @SP
   D=M
   @LCL
   M=D
   // goto functionName
   @Class1.get
   0;JMP
   // label for return address
(Class1.get$ret.3)
   
// call Class2.get 0
   // push returnAddress
   @Class2.get$ret.4
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push LCL
   @LCL
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push ARG
   @ARG
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THIS
   @THIS
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // push THAT
   @THAT
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   // reposition ARG
   @SP
   D=M
   @0
   D=D-A
   @5
   D=D-A
   @ARG
   M=D
   // reposition LCL
   @SP
   D=M
   @LCL
   M=D
   // goto functionName
   @Class2.get
   0;JMP
   // label for return address
(Class2.get$ret.4)
   
// label WHILE
(WHILE)
   
// goto WHILE
   @WHILE
   0;JMP
   
// function Class2.set 0
(Class2.set)
   
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
   
// pop static 0
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @Class2.0
   M=D
   
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
   
// pop static 1
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @Class2.1
   M=D
   
// push constant 0
   @0
   D=A
   @SP
   A=M
   M=D
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
   A=M-D
   D=M
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
   D=M+1
   @SP
   M=D
   // restore THAT
   @1
   D=A
   @R13
   A=M-D
   D=M
   @THAT
   M=D
   // restore THIS
   @2
   D=A
   @R13
   A=M-D
   D=M
   @THIS
   M=D
   // restore ARG
   @3
   D=A
   @R13
   A=M-D
   D=M
   @ARG
   M=D
   // restore LCL
   @4
   D=A
   @R13
   A=M-D
   D=M
   @LCL
   M=D
   // goto retAddr
   @R14
   A=M
   0;JMP
   
// function Class2.get 0
(Class2.get)
   
// push static 0
   @Class2.0
   D=M
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push static 1
   @Class2.1
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
   A=M-D
   D=M
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
   D=M+1
   @SP
   M=D
   // restore THAT
   @1
   D=A
   @R13
   A=M-D
   D=M
   @THAT
   M=D
   // restore THIS
   @2
   D=A
   @R13
   A=M-D
   D=M
   @THIS
   M=D
   // restore ARG
   @3
   D=A
   @R13
   A=M-D
   D=M
   @ARG
   M=D
   // restore LCL
   @4
   D=A
   @R13
   A=M-D
   D=M
   @LCL
   M=D
   // goto retAddr
   @R14
   A=M
   0;JMP
   
