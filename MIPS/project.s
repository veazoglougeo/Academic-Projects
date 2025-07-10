# ===========================================================================
# |	Computer Systems Organization.											|		
# |	Winter Semester 2021-2022.												|
# |	Third Assignment.														|
# |	Mips Code by:															|
# |	VEAZOGLOU GEORGIOS, p3190347@aueb.gr, 3190347.							|
# |	DROSOU VASILIKI, p3200044@aueb.gr, 3200044.								|
# ===========================================================================

.text
main:
	li $s1, 0				            # Register $s1 is the head. Set it to null.
	
	# Name: loop.
	# Parameters: User input.
	# Returns: Menu.
	# Does: Gets user input, jumps to labels, loops in case of wrong input.
loop:
	jal PRINT_MENU				        # Prints menu and gets input from the user.
	jal USER_INPUT			   

	seq $t0, $v0, 'A'					# If $v0 is equal to the Src2, set $t0 to 1. Else set $t0 to 0.
	bne $t0, $zero, A					# If $t0 is not equal to 0, go to label.
	seq $t0, $v0, 'B'					
	bne $t0, $zero, B
	seq $t0, $v0, 'C'
	bne $t0, $zero, C
	seq $t0, $v0, 'D'
	bne $t0, $zero, D
	seq $t0, $v0, 'X'
	bne $t0, $zero, EXIT
	j loop
	
	A:	jal CREATE_LIST
		j loop
	B:	jal DISPLAY_LIST
		j loop
	C:	jal ADD_NODE
		j loop
	D:	jal DELETE_NODE
		j loop


# -- PRINTS THE MENU -- #

PRINT_MENU:

	la $a0, ACTION		 
	li $v0, 4			
	syscall			
	la $a0, CREATE_L			
	syscall					
	la $a0, DISPLAY			
	syscall
	la $a0, ADD
	syscall
	la $a0, DELETE
	syscall
	la $a0, TERMINATE
	syscall
	jr $ra

# -- TAKES INPUT FROM USER -- #

USER_INPUT:

	li $v0, 12				         
	syscall					            
	jr $ra					          

# Name: CREATE_LIST.
# Parameters: Head, space, node ID, node Value.
# Returns: Input and error message.
# Does: Creates a list, allocates memory, stores the first node, warns user for errors.

CREATE_LIST:

	bne $s1, $0, LIST_EXISTS		    # If head is not null, the list is created.Go to label.

	li $v0, 9				           	
	li $a0, 12				            # Allocate 12 bytes, needed for three integers.
	syscall					            	
	move $s1, $v0				        # Store that address as the head.

	la $a0, ASK_ID						# Asks for node ID.
	li $v0, 4				            
	syscall					            

	li $v0, 5				           
	syscall					
	sw $v0, 0($s1)				        # Store the ID in memory, 1st field

	la $a0, ASK_VALUE					#Ask for node Value.
	li $v0, 4				
	syscall					

	li $v0, 5				
	syscall					
	sw $v0, 4($s1)				        # Store the value in memory, 2nd field
	sw $0, 8($s1)				        # Next field is null for a new list.
	
	jr $ra								# Return to A.

	LIST_EXISTS:
	
	la $a0, L_EXISTS					# Print message to notify the user and return to loop.
	li $v0, 4				
	syscall					
	jr $ra								
	
# Name: DISPLAY_LIST.
# Parameters: Head, nodes, field.
# Returns: Linked list, Error message.
# Does: Displays the Linked list, warns user for errors.

DISPLAY_LIST:

	beq $s1, $0, DISPLAY_REQ_LIST		# If the head is null then the list is not created. Go to label.

	move $t0, $s1				

	LOOP_DISPLAY:
	beq $t0, $0, RETURN					# If the list is printed, register $t0 must be null. Go to label.
	la $a0, NEXT_NODE			        
	li $v0, 4				
	syscall					
	
	li $v0, 1				
	lw $a0, 0($t0)				        # Load the first field(first 4 bytes -> ID) into $a0 and print it.
	syscall					
	
	li $v0, 4				
	la $a0, TAB				
	syscall					

	li $v0, 1							# Load the second field (forth to eighth byte -> Value) into $a0 and print it
	lw $a0, 4($t0)				 
	syscall	

	lw $t0, 8($t0)			
	j LOOP_DISPLAY		

	RETURN:
	li $v0, 4				
	la $a0, NL
	syscall					
	jr $ra					

	DISPLAY_REQ_LIST:					# Print message to notify the user and return to loop.
	la $a0, DISPLAY_R_LIST		
	li $v0, 4			
	syscall	
	jr $ra				

# Name: ID_IS_UNIQUE.
# Parameters:Head, nodes, field.
# Returns: ID messages
# Does: Checks if the node ID is unique and prints messages.

ID_IS_UNIQUE:

	move $t0, $s1						# Current = head.

	CHECK_NODES:
	beq $t0, $0, UNIQUE_ID				# If current is null, ID is unique.
	lw $t1, 0($t0)						# Load ID.
	beq $a0, $t1, NOT_UNIQUE_ID			# Break.
	lw $t0, 8($t0)						
	j CHECK_NODES			   

	UNIQUE_ID:
	li $v0, 1				       
	jr $ra					     

	NOT_UNIQUE_ID:
	li $v0, 0				  
	jr $ra					       


# Name: ADD_NODE.
# Parameters: Head, frame, node ID, node Value.
# Returns: Input and error messages.
# Does: Checks if the head is null, checks the given ID, stores the new node's information, updates the head, warns user about errors.

ADD_NODE:

	beq $s1, $0, ADD_TO_NULL_HEAD		# If the head is null then the list is not created. Go to label.	

	move $t0, $s1				        # Current = head.
	
	la $a0, ASK_ID						
	li $v0, 4				          
	syscall					            

	li $v0, 5				            
	syscall					         
	move $t1, $v0				        

	addi $sp, $sp, -12					# Stack frame for three words, store current Node, user's ID input and return address.
	sw $t0, 0($sp)						
	sw $t1, 4($sp)				
	sw $ra, 8($sp)				        
	move $a0, $t1				        
	jal ID_IS_UNIQUE					# Check if ID is unique.            

	lw $t0, 0($sp)
	lw $t1, 4($sp)
	lw $ra, 8($sp)
	addi $sp, $sp, 12

	beq $v0, $0, DUPLICATE_NODE			# If register $v0 is equal to 0 then the node exists. Go to label.

	la $a0, ASK_VALUE			       
	li $v0, 4				            
	syscall					            

	li $v0, 5				            
	syscall				            	
	move $t2, $v0						# Store Value at register $t2 for future use.	        			

	li $t3, 0				            

	FIND_LOOP:

	beq $t0, $0, EXIT_ADD_LOOP			# If the head is null, go to label
	lw $t4, 4($t0)						# Register $t4 stores the Value.
	slt $t4, $t2, $t4	        		# Compare Values.
	bne $t4, $0, EXIT_ADD_LOOP			# If $t2 is less than $t4, break.

	
	move $t3, $t0				       
	lw $t0, 8($t0)				       
	j FIND_LOOP				          

	EXIT_ADD_LOOP:
	
	li $v0, 9				            
	li $a0, 12	            			
	syscall				            	
	
	sw $t1, 0($v0)	        	   		# Store node's ID.
	sw $t2, 4($v0)						# Store node's Value.		        		
	sw $t0, 8($v0)		        		# Next node.

	beq $t3, $0, NULL_PREVIOUS				

	sw $v0, 8($t3)		        		
	jr $ra				            	

	NULL_PREVIOUS:


	bne $t4, $0, UPDATE_HEAD			

	UPDATE_HEAD:

	move $s1, $v0			        	# Head is the new node.
	jr $ra				            	

	ADD_TO_NULL_HEAD:

	la $a0, ADD_TO_NH    		
	li $v0, 4			            	
	syscall			   	            
	jr $ra			            		

	DUPLICATE_NODE:
	
	la $a0, NODE_EXISTS      			
	li $v0, 4			              
	syscall				             	
	jr $ra				               	
	
# Name: DELETE_NODE.
# Parameters: Head, node ID, space.
# Returns: Input and error messages.
# Does: Checks if there are nodes to delete, checks the given ID, deletes specific nodes, updates the head, warns user about errors.

DELETE_NODE:
	
	beq $s1, $0, NO_NODES				# If head is null then the task can not be completed. Go to label

	move $t0, $s1			        	# Current = head.

	la $a0, ASK_ID		        		
	li $v0, 4				            
	syscall				            	

	li $v0, 5			            	
	syscall				            
	move $t1, $v0		        		
	
	li $t2, 0			            	

	NODE_DELETE:	

	beq $t0, $0, NODE_NOT_FOUND			# If head is null, go to label.
	lw $t3, 0($t0)			        
	beq $t1, $t3, NODE_FOUND			# If the ID exists, go to label.
	move $t2, $t0						#else previous = current head.
	lw $t0, 8($t0)			           	
	j NODE_DELETE				     

	NODE_FOUND:
		
	beq $t2, $0, DELETE_HEAD			
	

	lw $t3, 8($t0)						#   Current = next.
	sw $t3, 8($t2)				        
	jr $ra					        

	DELETE_HEAD:
	
	lw $t2, 8($t0)			        	# Next node is the head.
	move $s1, $t2			        	
	jr $ra

	NODE_NOT_FOUND:

	la $a0, NODE_N_F	  
	li $v0, 4			            	
	syscall				           
	jr $ra				            	

	NO_NODES:

	la $a0, NO_N		
	li $v0, 4						
	syscall				           		
	jr $ra				        

# --TERMINATE PROGRAM-- #

EXIT:
	li $v0, 10
	syscall

    .data

ACTION:			.asciiz  "\nSpecify an action to take for the linked list. Input must be A, B, C, or D:"
CREATE_L:		.asciiz  "\n\tA. Create the list."
DISPLAY:		.asciiz  "\n\tB. Display the list."
ADD:			.asciiz  "\n\tC. Add a single node."
DELETE:			.asciiz  "\n\tD. Delete a node."
TERMINATE:		.asciiz  "\nOr X to terminate: "
ASK_ID:			.asciiz  "\n\n\tEnter the integer for the ID field and press Enter: "
ASK_VALUE:		.asciiz  "\tEnter the node's data field and press Enter: "
NEXT_NODE:		.asciiz  "\n\n\tThe next node is:\t"
L_EXISTS:		.asciiz  "\n\n\tA linked list already exists. Enter 'C' in the menu to add nodes to it or 'B' to display it.\n"
ADD_TO_NH:		.asciiz  "\n\n\tYou must create a linked list before you can insert a new node. See menu option 'A'.\n"
DISPLAY_R_LIST: .asciiz  "\n\n\tYou must create a linked list before you can print it. See menu option 'A'.\n"
NO_N:			.asciiz  "\n\n\tYou must create a linked list before you can delete a node. See menu option 'A'.\n"
NODE_N_F:		.asciiz  "\n\n\tThe requested node was not found in the linked list.\n"
NODE_EXISTS:	.asciiz  "\n\tThe node with the requested ID already exists in the linked list.\n"
TAB:			.asciiz  "\t"
NL:				.asciiz  "\n"