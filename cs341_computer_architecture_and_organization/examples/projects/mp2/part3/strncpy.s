# strncpy.s: copies characters from one array to another
# Giancarlos Marte
# 03/28/21

	.data
num:
	.long 0

	.text
	.globl mystrncpy
mystrncpy:
	# stack frame
	pushl %ebp
	movl %esp, %ebp

	# get the empty array
	movl 8(%ebp), %eax

	# get the input string
	movl 12(%ebp), %ecx

	# get the number of characters to copy
	movl 16(%ebp), %edx
	movl %edx, num

	# count
	movl $0, %edx

loop:
	# check if count is equal to num
	cmpl %edx, num
	
	# exit if equal
	jz exit

	# get character from string
	movb (%ecx), %dl

	# put the char from string into empty array
	movb %dl, (%eax)

	# decrease the count
	addl $1, %edx

	# increase the empty array pointer
	incl %eax

	# increase the string pointer
	incl %ecx

	# loop back
	jmp loop

exit:
	# remove stack frame
	movl %ebp, %esp
	popl %ebp
	ret
	.end
