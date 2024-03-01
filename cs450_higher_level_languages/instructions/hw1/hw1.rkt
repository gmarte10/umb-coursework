#lang racket
#|
    ===> PLEASE DO NOT DISTRIBUTE THE SOLUTIONS PUBLICLY <===

  We ask that solutions be distributed only locally -- on paper, on a
  password-protected webpage, etc.

  Students are required to adhere to the University Policy on Academic
  Standards and Cheating, to the University Statement on Plagiarism and the
  Documentation of Written Work, and to the Code of Student Conduct as
  delineated in the catalog of Undergraduate Programs. The Code is available
  online at: http://www.umb.edu/life_on_campus/policies/code/

                    * * * ATTENTION! * * *

  Every solution submitted to our grading server is automatically compared
  against a solution database for plagiarism, which includes every solution
  from every student in past semesters.

  WE FOLLOW A ZERO-TOLERANCE POLICY: any student breaking the Code of Student
  Conduct will get an F in this course and will be reported according to
  Section II Academic Dishonesty Procedures.

|#

;; Please, do not remove this line and do not change the function names,
;; otherwise the grader will not work and your submission will get 0 points.
(provide (all-defined-out))

(define ex1 'todo)
(define ex2 'todo)
(define ex3 'todo)

;; Constructs a tree from two trees and a value
(define (tree left value right) 'todo)
;; Constructs a tree with a single node
(define (tree-leaf value) 'todo)

;; Accessors
(define (tree-left self) 'todo)
(define (tree-value self) 'todo)
(define (tree-right self) 'todo)

;; Copies the source and updates one of the fields
(define (tree-set-value self value) 'todo)
(define (tree-set-left self left) 'todo)
(define (tree-set-right self right) 'todo)

;; Function that inserts a value in a BST
(define (bst-insert self value) 'todo)

;; lambda
(define (lambda? node) 'todo)
(define (lambda-params node) 'todo)
(define (lambda-body node) 'todo)

;; apply
(define (apply? l) 'todo)
(define (apply-func node) 'todo)
(define (apply-args node) 'todo)

;; define
(define (define? node) 'todo)
(define (define-basic? node) 'todo)
(define (define-func? node) 'todo)
