#lang errortrace typed/racket
#|
    ===> PLEASE DO NOT DISTRIBUTE SOLUTIONS NOR TESTS PUBLICLY <===

   We ask that solutions be distributed only locally -- on paper, on a
   password-protected webpage, etc.

   Students are required to adhere to the University Policy on Academic
   Standards and Cheating, to the University Statement on Plagiarism and the
   Documentation of Written Work, and to the Code of Student Conduct as
   delineated in the catalog of Undergraduate Programs. The Code is available
   online at: http://www.umb.edu/life_on_campus/policies/code/

|#
(require "hw4-util.rkt")
(provide (all-defined-out))

(: stream-skip : (All [Elem] Real (stream Elem) -> (stream Elem)))
; Parameterized on the type of the elements of the stream
; The first argument is the number of elements we wish to skip,
; and the second argument is the stream.

(define (stream-skip n s)
  (error "todo"))


(: stream-fold : (All [Elem Accum]
                      (Elem Accum -> Accum)  ;; "step" function
                      Accum                  ;; initial accumulator
                      (stream Elem)          ;; stream to process
                      ->
                      (stream Accum)))
; We have 2 type parameters,
; 1. the type of elements of the stream
; 2. the type of the result being accumulated

(define (stream-fold f a s)
  (error "todo"))

(: set-void : set)
(define set-void
  (error "todo"))

(: set-epsilon : set)
(define set-epsilon
  (error "todo"))

(: set-char : Char -> set)
(define (set-char x)
  (error "todo"))

(: set-prefix : String set -> set)
(define (set-prefix s p)
  (error "todo"))

(: set-union : set set -> set)
(define (set-union p1 p2)
  (error "todo"))

(: set-concat : set set -> set)
(define (set-concat p1 p2)
  (error "todo"))

(: r:eval-exp : r:expression -> Number)
(define (r:eval-exp exp)
  (match exp
    ; If it's a number, return that number
    [(r:number v) v]
    ; If it's a function with 2 arguments
    [(r:apply (r:variable f) (list arg1 arg2))
     (define func (r:eval-builtin f))
     (func (r:eval-exp arg1) (r:eval-exp arg2))]))

(: r:exp-to-string : r:expression -> String)
(define (r:exp-to-string exp)
  (error "todo"))

