    @R0
    D=M
    @n
    M=D
    @i
    M=1
    @sum
    M=0

(LOOP)
    @i       // Access i
    D=M      // Take D = i
    @n
    D=D-M    // Take D = D - n, in this case, n is the number of iteration we want
    @STOP
    D;JGT    // if i > n goto STOP 

    @sum     // Access the variable sum
    D=M      // D=sum
    @i       // Access the variable i, which is the counter
    D=D+M    // D=D+M, D will take the previous sum and add i to it
    @sum
    M=D      // The variable sum will now have the new value
    @i
    M=M+1    // i = i + 1
    @LOOP
    0;JMP    // Unconditial jump back to LOOP

(STOP)
    @sum     // Access the variable sum 
    D=M
    @R1
    M=D      // RAM[1] = sum

(END)
    @END
    0;JMP