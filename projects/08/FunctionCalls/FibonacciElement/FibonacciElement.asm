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
   
// function Main.fibonacci 0
(Main.fibonacci)
   
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
   
// push constant 2
   @2
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// lt                     
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
   
   @LT1
   D;JLT
   
   @JEQ1
   D;JEQ
   
   @NLT1
   0;JEQ
   
(LT1)
   @SP
   A=M
   M=0
   @CONTINUE1
   0;JEQ
   
(JEQ1)
   @SP
   A=M
   M=0
   @CONTINUE1
   0;JEQ
   
(NLT1)
   @SP
   A=M
   M=-1
   @CONTINUE1
   0;JEQ
   
(CONTINUE1)
   @SP
   M=M+1
   
// if-goto IF_TRUE
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @IF_TRUE
   D;JGT
   D;JLT
   
// goto IF_FALSE
   @IF_FALSE
   0;JMP
   
// label IF_TRUE          
(IF_TRUE)
   
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
   
// label IF_FALSE         
(IF_FALSE)
   
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
   
// push constant 2
   @2
   D=A
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
   
// call Main.fibonacci 1  
   // push returnAddress
   @Main.fibonacci$ret.2
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
   @Main.fibonacci
   0;JMP
   // label for return address
(Main.fibonacci$ret.2)
   
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
   
// push constant 1
   @1
   D=A
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
   
// call Main.fibonacci 1  
   // push returnAddress
   @Main.fibonacci$ret.3
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
   @Main.fibonacci
   0;JMP
   // label for return address
(Main.fibonacci$ret.3)
   
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
   
// function Sys.init 0
(Sys.init)
   
// push constant 4
   @4
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// call Main.fibonacci 1   
   // push returnAddress
   @Main.fibonacci$ret.4
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
   @Main.fibonacci
   0;JMP
   // label for return address
(Main.fibonacci$ret.4)
   
// label WHILE
(WHILE)
   
// goto WHILE              
   @WHILE
   0;JMP
   
