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


(define ex1 (/
             (*
              (/ 15 10) 7)
             (*
              (- 11 7) 15)))
(define ex2
  (list
   (/
    (*
     (/ 15 10) 7)
    (*
     (- 11 7) 15))
   (/
    (* 3/2 7)
    (*
     (- 11 7) 15))
   (/ 21/2
      (*
       (- 11 7) 15))
   (/ 21/2
      (* 4 15))
   (/ 21/2 60)
   7/40))

(define (ex3 x y)
              (>=
               (+
                (+ 2 15)
                (- x 5))
               (+
                (* 6 12) x)))
                

;; Constructs a tree from two trees and a value
(define (tree left value right) (list left value right))
;; Constructs a tree with a single node
(define (tree-leaf value) (tree null value null))

;; Accessors
(define (tree-left self) (car self))
(define (tree-value self) (car (cdr self)))
(define (tree-right self) (car (cdr (cdr self))))

;; Copies the source and updates one of the fields
(define (tree-set-value self value) (tree (car self) value (car (cdr (cdr self)))))
(define (tree-set-left self left) (tree left (car (cdr self)) (car (cdr (cdr self)))))
(define (tree-set-right self right) (tree (car self) (car (cdr self)) right))

;; Function that inserts a value in a BST
(define (bst-insert self value)
  (cond [(null? self) (tree null value null)]
        [(equal? value (car (cdr self))) (tree-set-value self value)]
        [(< value (car (cdr self))) (tree-set-left self (bst-insert (car self) value))]
        [else (tree-set-right self (bst-insert (car (cdr (cdr self))) value))]))

;; lambda
(define (lambda? node)
  (cond [(not (list? node)) #f]
        [(< (length node) 3) #f]
        [(not (equal? 'lambda (car node))) #f]
        [(not (list? (car (cdr node)))) #f]
        [(not (andmap symbol? (car (cdr node)))) #f]
        [else #t]))
(define (lambda-params node) (car (cdr node)))
(define (lambda-body node) (cdr (cdr node)))

;; apply
(define (apply? l)
  (cond [(null? l) #f]
        [(not (list? l)) #f]
        [else #t]))
(define (apply-func node) (car node))
(define (apply-args node) (cdr node))

;; define
(define (define? node)
  (cond
    [(or
      (define-basic? node)
      (define-func? node)) #t]
    [else #f]))

(define (define-basic? node)
  (cond
    [(and
      (not (null? node))
      (list? node)
      (equal? (length node) 3)
      (equal? (car node) 'define)
      (symbol? (car (cdr node)))) #t]
    [else #f]))

(define (define-func? node)
  (cond
    [(and
      (not (null? node))
      (list? node)
      (>= (length node) 3)
      (equal? (car node) 'define)
      (list? (car (cdr node)))
      (not (null? (car (cdr node))))
      (andmap symbol? (car (cdr node)))) #t]
    [else #f]))




