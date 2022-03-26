// push constant 0    
   @0
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1
   
// pop local 0         
   @0
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
   
// label LOOP_START
(LOOP_START)
   
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
   
// pop local 0	        
   @0
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
   
// pop argument 0      
   @0
   D=A
   @ARG
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
   
// if-goto LOOP_START  
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @LOOP_START
   D;JGT
   D;JLT
   
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
   
