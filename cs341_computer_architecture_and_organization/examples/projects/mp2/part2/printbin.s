# Turn hex into binary string
# Giancarlos Marte
# 4/3

	.data
# binary form
string:
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0
	.byte 0x0

# to make binary string
one: .byte 0x31
zero: .byte 0x30
space: .byte 0x20

# backup of input
copy: .long 0x0

# possible cases
c0: .byte 0x0
c1: .byte 0x1
c2: .byte 0x2
c3: .byte 0x3
c4: .byte 0x4
c5: .byte 0x5
c6: .byte 0x6
c7: .byte 0x7
c8: .byte 0x8
c9: .byte 0x9
c10: .byte 0xa
c11: .byte 0xb
c12: .byte 0xc
c13: .byte 0xd
c14: .byte 0xe
c15: .byte 0xf

	.text
	.globl printbin
printbin:
	pushl %ebp					# stack frame
	movl %esp, %ebp
	movl 8(%ebp), %eax			# user input
	movl %eax, copy				# backup copy
	movl string, %ecx			# ptr to string (%ecx)
	call donibble				# get upper
	movb space, %al				# add space
	movb %al, (%ecx)
	incl %ecx
	call donibble
	jmp return

donibble:
	cmpb $0, %dl				# if (dl not used yet)
	je upper
	cmpb $0, %al				# if (if al not used yet)
	je lower

upper:
	movb (%eax), %dl  			# get byte
	shrb $4, %dl 				# shift 4 to the right

	cmpb c0, %dl 				# if (%dl == c0)
	je case0
	cmpb c1, %dl 				# if (%dl == 0001)
	je case1
	cmpb c2, %dl 				# if (%dl == 0010)
	je case2
	cmpb c3, %dl 				# if (%dl == 0011)
	je case3
	cmpb c4, %dl 				# if (%dl == 0100)
	je case4
	cmpb c5, %dl 				# if (%dl == 0101)
	je case5
	cmpb c6, %dl 				# if (%dl == 0110)
	je case6
	cmpb c7, %dl 				# if (%dl == 0111)
	je case7
	cmpb c8, %dl 				# if (%dl == 1000)
	je case8
	cmpb c9, %dl 				# if (%dl == 1001)
	je case9
	cmpb c10, %dl 				# if (%dl == 1010)
	je case10
	cmpb c11, %dl 				# if (%dl == 1011)
	je case11
	cmpb c12, %dl 				# if (%dl == 1100)
	je case12
	cmpb c13, %dl 				# if (%dl == 1101)
	je case13
	cmpb c14, %dl 				# if (%dl == 1110)
	je case14
	cmpb c15, %dl 				# if (%dl == 1111)
	je case15

lower:
	movl copy, %eax				# restore char input
	movb (%eax), %al			# mask with 0xf
	andb $0xf, %al

	cmpb c0, %dl 				# if (%dl == c0)
	je case0
	cmpb c1, %dl 				# if (%dl == 0001)
	je case1
	cmpb c2, %dl 				# if (%dl == 0010)
	je case2
	cmpb c3, %dl 				# if (%dl == 0011)
	je case3
	cmpb c4, %dl 				# if (%dl == 0100)
	je case4
	cmpb c5, %dl 				# if (%dl == 0101)
	je case5
	cmpb c6, %dl 				# if (%dl == 0110)
	je case6
	cmpb c7, %dl 				# if (%dl == 0111)
	je case7
	cmpb c8, %dl 				# if (%dl == 1000)
	je case8
	cmpb c9, %dl 				# if (%dl == 1001)
	je case9
	cmpb c10, %dl 				# if (%dl == 1010)
	je case10
	cmpb c11, %dl 				# if (%dl == 1011)
	je case11
	cmpb c12, %dl 				# if (%dl == 1100)
	je case12
	cmpb c13, %dl 				# if (%dl == 1101)
	je case13
	cmpb c14, %dl 				# if (%dl == 1110)
	je case14
	cmpb c15, %dl 				# if (%dl == 1111)
	je case15

case0:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 00
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 000
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 0000
	movb %al, (%ecx)
	incl %ecx

case1:	
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 00
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 000
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 0001
	movb %al, (%ecx)
	incl %ecx

case2:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 00
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 001
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 0010
	movb %al, (%ecx)
	incl %ecx

case3:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 00
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 001
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 0011
	movb %al, (%ecx)
	incl %ecx

case4:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 01
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 010
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 0100
	movb %al, (%ecx)
	incl %ecx

case5:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 01
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 010
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 0101
	movb %al, (%ecx)
	incl %ecx

case6:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 01
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 011
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 0110
	movb %al, (%ecx)
	incl %ecx

case7:
	movb zero, %al		# 0
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 01
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 011
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 0111
	movb %al, (%ecx)
	incl %ecx

case8:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 10
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 100
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 1000
	movb %al, (%ecx)
	incl %ecx

case9:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 10
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 100
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 10001
	movb %al, (%ecx)
	incl %ecx

case10:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 10
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 101
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 1010
	movb %al, (%ecx)
	incl %ecx

case11:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 10
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 101
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 1011
	movb %al, (%ecx)
	incl %ecx

case12:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 11
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 110
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 1100
	movb %al, (%ecx)
	incl %ecx

case13:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 11
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 110
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 1101
	movb %al, (%ecx)
	incl %ecx

case14:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 11
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 111
	movb %al, (%ecx)
	incl %ecx
	movb zero, %al		# 1110
	movb %al, (%ecx)
	incl %ecx

case15:
	movb one, %al		# 1
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 11
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 111
	movb %al, (%ecx)
	incl %ecx
	movb one, %al		# 1111
	movb %al, (%ecx)
	incl %ecx

return:
	movl %ecx, %eax			# put string into eax
	movl %ebp, %esp			# remove stack frame
	popl %ebp
	ret
	.end
