#lang typed/racket
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
(require "hw9-util.rkt")

(provide (all-defined-out))

(: translate-var (s:variable -> j:variable))
(define (translate-var x)
  (match x
    [(s:variable x) (j:variable x)]))

;;;;;;;;;;;;;;;
;; Exercise 1
(: translate  (s:expression -> j:expression))
(define (translate exp)
  (match exp
    [(? k:const? k)
     k]
    [(? s:variable? x)
     (translate-var x)]
    [(s:let (s:variable x) s1 s2)
     (j:let (j:variable x) (translate s1) (translate s2))]
    [(s:apply f ea)
     (j:apply (translate f) (map translate ea))]
    ;; TODO: write the missing cases
    ))
