	.data
# string to iterate through
string:
	.long 0
# character to find in string
char:
	.long 0
# total number of times char appears in string
total:
	.long 0
# null character
null:
	.long 0
# current character
current:
	.long 0
# store ecx
ecx:
	.long 0
# number one
one:
	.long 1

	.global count
	.text
count: 
	# create stack frame
	pushl %ebp
	movl %esp, %ebp
	# store exc
	movl %ecx, ecx
	# add space for 5 constant variables
	subl $20, %esp
	# get string input
	movl 8(%ebp), %eax
	movl %eax, string
	# get char input
	movl 12(%ebp), %eax
	movl %eax, char
	# get the first char in string
	movl (string), %eax
	movl %eax, current

while:
	# if char == null -> end the program
	jmp checkNull
	# compare current char with input char
	movl current, %eax
	movl char, %ecx
	cmpl %eax, %ecx
	# if equal -> increase total
	je	increase
	# get the next char
	jmp nextChar
	# loop back		
	jmp while						

checkNull:
	# check if current == null
	movl current, %eax
	cmpl null, %eax
	# if equal -> end program
 	je end								

increase:
	# add 1 to total
	movl total, %eax
	addl one, %eax
	movl %eax, total					

nextChar:
	# point to next char
	movl string, %eax
	incl %eax
	# store next char into current
	movl %eax, current

end:
	# put total into eax
	movl total, %eax
	# restore ecx
	movl ecx, %ecx
	# restore esp	
	movl %ebp, %esp
	# remove stack frame
	popl %ebp
	# return back to C		
	ret					
	.end
