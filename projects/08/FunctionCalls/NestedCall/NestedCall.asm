// initializing pointers
   @256
   D=A
   @SP
   M=D
   
// function Sys.init 0
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
   
   
// function Sys.init 0
(Sys.init)
   
// push constant 4000	
   @4000
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop pointer 0
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @3
   M=D
   
// push constant 5000
   @5000
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop pointer 1
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @4
   M=D
   
// call Sys.main 0
   // push returnAddress
   @Sys.main$ret.1
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
   @Sys.main
   0;JMP
   // label for return address
(Sys.main$ret.1)
   
// pop temp 1
   @1
   D=A
   @5
   D=A+D
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
   
// label LOOP
(LOOP)
   
// goto LOOP
   @LOOP
   0;JMP
   
// function Sys.main 5
(Sys.main)
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
   @0
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// push constant 4001
   @4001
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop pointer 0
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @3
   M=D
   
// push constant 5001
   @5001
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop pointer 1
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @4
   M=D
   
// push constant 200
   @200
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop local 1
   @1
   D=A
   @LCL
   D=D+M
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
   
// push constant 40
   @40
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop local 2
   @2
   D=A
   @LCL
   D=D+M
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
   
// push constant 6
   @6
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop local 3
   @3
   D=A
   @LCL
   D=D+M
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
   
// push constant 123
   @123
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// call Sys.add12 1
   // push returnAddress
   @Sys.add12$ret.2
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
   @1
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
   @Sys.add12
   0;JMP
   // label for return address
(Sys.add12$ret.2)
   
// pop temp 0
   @0
   D=A
   @5
   D=A+D
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
   
// push local 2
   @2
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
   
// push local 3
   @3
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
   
// push local 4
   @4
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
   
// function Sys.add12 0
(Sys.add12)
   
// push constant 4002
   @4002
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop pointer 0
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @3
   M=D
   
// push constant 5002
   @5002
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop pointer 1
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @4
   M=D
   
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
   
// push constant 12
   @12
   D=A
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
   