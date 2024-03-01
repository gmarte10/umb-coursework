	.data
byte: .byte 0x0
esc: .byte 0x0

	.text
	.globl echo
echo:
	# stack frame
	pushl %ebp
	movl %esp, %ebp
	# get escape char
	movl 8(%ebp), %eax
	movb (%eax), esc
	# step 1a: turn on DTR and RTS
	jmp dtr_rts
	# step 1b: wait for DSR, CTS, & DCD
	movw $0x2fe, %dx		# modem status
	jmp wait
	# step 2: read data ready
	movw $0x2fd, %dx		# line status
	inb (%dx), %al 			# get data ready
	# step 3: if ready, read a byte from recieve data
	cmpb $0, %al
	je return
	jmp read_byte
	# step 3b: check if escape
	movb $esc, %cl 
	cmpb $byte, %cl
	je return
	# step 4: read transmit holding register empty
	inb (%dx), %al 			# get thre
	# step 5: if empty, write a byte to transmit data
	cmpb $0, %al
	je xmit
dtr_rts:
	movw $0x2fc, %dx		# modem control
	inb (%dx), %al			# get current
	orb $0x03, %al			# or on 2 lsbs
	outb %al, (%dx)			# set control
wait:
	inb (%dx), %al			# get current
	andb $0xb0, %al 		# get 3 signals
	xorb $0xb0, %al			# check all 3 signals
	jnz loop1				# if some missing
	# all 3 signals are on now
read_byte:
	movw $0x2fd, %dx		# line status
	inb (%dx), %al 			# get data ready
	andb $0x01, %al 		# look at dr
	jz xmit					# if data recieved
	movw $0x2f8, %dx		# i/o data addr
	inb (%dx), %al 			# move rx to %al
	movb %al, byte 			# store byte
	movw $0x2fd, %dx		# line status
xmit:
	inb (%dx), %al 			# get thre
	andb $0x20, %al 		# look at thre
	jz read_byte			# if tx hr empty
	movb $byte, %al 		# get data to send
	movw $0x2f8, %dx 		# i/o data addr
	outb %al, (%dx)			# send it
	jmp read_byte			# loop to get another byte
return:
	# remove stack frame
	movl %ebp, %esp
	popl %ebp
	ret
	.end