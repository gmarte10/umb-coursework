#|
    ===> PLEASE DO NOT DISTRIBUTE THE SOLUTIONS PUBLICLY <===

   We ask that solutions be distributed only locally -- on paper, on a
   password-protected webpage, etc.

   Students are required to adhere to the University Policy on Academic
   Standards and Cheating, to the University Statement on Plagiarism and the
   Documentation of Written Work, and to the Code of Student Conduct as
   delineated in the catalog of Undergraduate Programs. The Code is available
   online at: http://www.umb.edu/life_on_campus/policies/code/

|#
;; PLEASE DO NOT CHANGE THE FOLLOWING LINES
#lang typed/racket
(provide (all-defined-out))
(require "hw5-util.rkt")
;; END OF REQUIRES

;; Exercise 1
(: s:subst (s:expression s:variable s:value -> s:expression))
(define (s:subst exp var val)
  (error "to do"))

;; Exercise 2
(: s:eval ((s:expression s:variable s:value -> s:expression) s:expression -> s:value))
(define (s:eval subst exp)
  (match exp
    [(s:apply ef ea)
     (error "to do")]
    [(? s:value?)
     (error "to do")]))

;; Exercise 3
(: e:eval (e:environ e:expression -> e:value))
(define (e:eval env exp)
  (error "to do"))

;; Exercise 4 (Manually graded)
#|
PLEASE REPLACE THIS TEXT BY YOUR ANSWER.
YOU MAY USE MULTIPLE LINES.
|#

;; Exercise 5 (Manually graded)
#|
PLEASE REPLACE THIS TEXT BY YOUR ANSWER
YOU MAY USE MULTIPLE LINES.
|#
