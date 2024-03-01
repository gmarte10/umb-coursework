#lang racket

#|
            ######################################################
            ###  PLEASE DO NOT DISTRIBUTE TEST CASES PUBLICLY  ###
            ###         SHARING IN THE FORUM ENCOURAGED        ###
            ######################################################

  You are encouraged to share your test cases in the course forum, but please
  do not share your test cases publicly (eg, GitHub), as that stops future
  students from learning how to write test cases, which is a crucial part of
  this course.
|#

(require "hw1.rkt")
(require rackunit)

; 3)
(define bst7 (tree-leaf 7))
(check-equal? bst7 (list null 7 null))
(define bst76 (bst-insert bst7 6))
(check-equal? bst76 (tree (tree-leaf 6) 7 null))
(define bst768 (bst-insert bst76 8))
(check-equal? bst768 (tree (tree-leaf 6) 7 (tree-leaf 8)))
(define bst7685 (bst-insert bst768 5))
(check-equal? bst7685 (tree (tree (tree-leaf 5) 6 null) 7 (tree-leaf 8)))
(check-equal? (bst-insert bst7685 7) bst7685)

; 4.a)
(check-false (lambda? '()))
(check-false (lambda? '(lambda)))
(check-false (lambda? '10))
(check-false (lambda? '(define ())))
(check-false (lambda? '(lambda (1) 2)))
(check-true (lambda? (quote (lambda (x) x))))
(check-false (lambda? (quote 3)))
(check-false (lambda? '(lambda ())))
(check-true (lambda? '(lambda (x) ())))
(check-true (lambda? '(lambda () (+ 1 2))))
(check-false (lambda? '(lambda x 10)))
(check-true (andmap symbol? (quote (x y z))))
(check-false (andmap symbol? (quote (x 3 z))))
; 4.b)
(check-equal? (lambda-params (quote (lambda (a b c) 1 2 3))) '(a b c))
(check-equal? (list 'x) (lambda-params (quote (lambda (x) y))))
; 4.c)
(check-equal? (lambda-body (quote (lambda (a b c) 1 2 3))) '(1 2 3))
(check-equal? (list 'y) (lambda-body (quote (lambda (x) y))))
; 4.d)
(check-true (apply? (quote (x y))))
(check-false (apply? (quote ())))
(check-false (apply? 10))
(check-true (apply? '((lambda (x) x) 1)))
; 4.e)
(check-equal? 'x (apply-func (quote (x y))))
; 4.f)
(check-equal? (list 'y) (apply-args (quote (x y))))
; 4.g)
(check-true (define? (quote (define x 3))))
; 4.h)
(check-true (define-basic? (quote (define x 3))))
(check-false (define-basic? '(define)))
(check-false (define-basic? '(define 1)))
(check-false (define-basic? '(define 1 2)))
(check-false (define-basic? '(define () 2)))
(check-false (define-basic? '(define (1) 2)))
(check-false (define-basic? '(define (x) 2)))
(check-false (define-basic? '(define (x 1) 2)))
(check-false (define-basic? '(1 define 2)))
(check-false (define-basic? '(1 2 define)))
(check-false (define-basic? '(define 2 define)))
(check-true (define-basic? '(define define 2)))
(check-true (define-basic? '(define define define)))

; 4.i)
(check-true (define-func? (quote (define (x) 3))))
(check-true (define-func? '(define (x y z x) 3 3 3 3)))
