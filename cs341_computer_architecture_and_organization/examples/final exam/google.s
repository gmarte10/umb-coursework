#GOOG Stock price analysis assembly language program
# google.s
#

# Giancarlos Marte


          .globl google 
          .data
price:    .long 1260    #11/1/19 price
          .long 1289    #12/1/19 price
          .long 1337    #1/1/20 price
          .long 1434    #2/1/20 price
          .long 1389    #3/1/20 price
          .long 1105    #4/1/20 price
          .long 1320    #5/1/20 price
          .long 1431    #6/1/20 price
          .long 1438    #7/1/20 price
          .long 1482    #8/1/20 price
          .long 1634    #9/1/20 price
          .long 1490    #10/1/20 price
          .long 0       #stop 

input:
          .long 0


          .text
google:
     # stack frame
     pushl %ebp
     movl %esp, %ebp

     # get stock price input
     movl 8(%ebp), %edx
     movl %edx, input

     # get 3rd price (skips 2019)
     movl $price, %eax
     addl $8, %eax

     # set count = 0
     movl $0, %ecx

loop:
     # if (price == 0) {end program}
     cmpl $0, (%eax)
     je end

     # else if (price >= input) {increase count}
     movl (%eax), %edx
     cmpl input, %edx
     jge increase

     # else {get next price}
     addl $4, %eax

     jmp loop

increase:
     # increase count
     addl $1, %ecx

     # get next price
     addl $4, %eax

     jmp loop

end:
     # return data
     movl %ecx, %eax

     # remove stack frame
     popl %ebp
     ret
     .end 

