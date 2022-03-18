// initializing pointers

// push constant 17
   @17
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 17
   @17
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// eq
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

   @EQ0
   D;JEQ

   @NEQ0
   0;JEQ

(EQ0)
   @SP
   A=M
   M=-1
   @CONTINUE0
   0;JEQ

(NEQ0)
   @SP
   A=M
   M=0
   @CONTINUE0
   0;JEQ

(CONTINUE0)
   @SP
   M=M+1

// push constant 17
   @17
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 16
   @16
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// eq
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

   @EQ1
   D;JEQ

   @NEQ1
   0;JEQ

(EQ1)
   @SP
   A=M
   M=-1
   @CONTINUE1
   0;JEQ

(NEQ1)
   @SP
   A=M
   M=0
   @CONTINUE1
   0;JEQ

(CONTINUE1)
   @SP
   M=M+1

// push constant 16
   @16
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 17
   @17
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// eq
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

   @EQ2
   D;JEQ

   @NEQ2
   0;JEQ

(EQ2)
   @SP
   A=M
   M=-1
   @CONTINUE2
   0;JEQ

(NEQ2)
   @SP
   A=M
   M=0
   @CONTINUE2
   0;JEQ

(CONTINUE2)
   @SP
   M=M+1

// push constant 892
   @892
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 891
   @891
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

   @LT3
   D;JLT

   @JEQ3
   D;JEQ

   @NLT3
   0;JEQ

(LT3)
   @SP
   A=M
   M=0
   @CONTINUE3
   0;JEQ

(JEQ3)
   @SP
   A=M
   M=0
   @CONTINUE3
   0;JEQ

(NLT3)
   @SP
   A=M
   M=-1
   @CONTINUE3
   0;JEQ

(CONTINUE3)
   @SP
   M=M+1

// push constant 891
   @891
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 892
   @892
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

   @LT4
   D;JLT

   @JEQ4
   D;JEQ

   @NLT4
   0;JEQ

(LT4)
   @SP
   A=M
   M=0
   @CONTINUE4
   0;JEQ

(JEQ4)
   @SP
   A=M
   M=0
   @CONTINUE4
   0;JEQ

(NLT4)
   @SP
   A=M
   M=-1
   @CONTINUE4
   0;JEQ

(CONTINUE4)
   @SP
   M=M+1

// push constant 891
   @891
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 891
   @891
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

   @LT5
   D;JLT

   @JEQ5
   D;JEQ

   @NLT5
   0;JEQ

(LT5)
   @SP
   A=M
   M=0
   @CONTINUE5
   0;JEQ

(JEQ5)
   @SP
   A=M
   M=0
   @CONTINUE5
   0;JEQ

(NLT5)
   @SP
   A=M
   M=-1
   @CONTINUE5
   0;JEQ

(CONTINUE5)
   @SP
   M=M+1

// push constant 32767
   @32767
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 32766
   @32766
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// gt
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

   @GT6
   D;JGT

   @JEQ6
   D;JEQ

   @NGT6
   0;JEQ

(GT6)
   @SP
   A=M
   M=0
   @CONTINUE6
   0;JEQ

(JEQ6)
   @SP
   A=M
   M=0
   @CONTINUE6
   0;JEQ

(NGT6)
   @SP
   A=M
   M=-1
   @CONTINUE6
   0;JEQ

(CONTINUE6)
   @SP
   M=M+1

// push constant 32766
   @32766
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 32767
   @32767
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// gt
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

   @GT7
   D;JGT

   @JEQ7
   D;JEQ

   @NGT7
   0;JEQ

(GT7)
   @SP
   A=M
   M=0
   @CONTINUE7
   0;JEQ

(JEQ7)
   @SP
   A=M
   M=0
   @CONTINUE7
   0;JEQ

(NGT7)
   @SP
   A=M
   M=-1
   @CONTINUE7
   0;JEQ

(CONTINUE7)
   @SP
   M=M+1

// push constant 32766
   @32766
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 32766
   @32766
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// gt
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

   @GT8
   D;JGT

   @JEQ8
   D;JEQ

   @NGT8
   0;JEQ

(GT8)
   @SP
   A=M
   M=0
   @CONTINUE8
   0;JEQ

(JEQ8)
   @SP
   A=M
   M=0
   @CONTINUE8
   0;JEQ

(NGT8)
   @SP
   A=M
   M=-1
   @CONTINUE8
   0;JEQ

(CONTINUE8)
   @SP
   M=M+1

// push constant 57
   @57
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 31
   @31
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// push constant 53
   @53
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

// push constant 112
   @112
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

// neg
   @SP
   M=M-1
   @SP
   A=M
   M=-M
   @SP
   M=M+1

// and
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @SP
   M=M-1
   @SP
   A=M
   M=D&M
   @SP
   M=M+1

// push constant 82
   @82
   D=A
   @SP
   A=M
   M=D
   @SP
   M=M+1

// or
   @SP
   M=M-1
   @SP
   A=M
   D=M
   @SP
   M=M-1
   @SP
   A=M
   M=D|M
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

