#lang racket
#|
            #####################################################
            ###  PLEASE DO NOT DISTRIBUTE SOLUTIONS PUBLICLY  ###
            #####################################################

  Copy your solution of HW1 as file "hw1.rkt". The file should be in the same
  directory as "hw2.rkt" and "ast.rkt".
|#
(require "ast.rkt")
(require "hw1.rkt")
(require rackunit)
(provide (all-defined-out))
;; ^^^^^ DO NOT CHANGE ANY CODE ABOVE THIS LINE ^^^^^


;; Exercise 1
;; min-from: Real (Listof Real) -> Real
(define (min-from n l)
  (foldl min n l))

;; Exercise 2
;; count: (Listof X) -> Real
(define (count l)
  (define (c-step x accum)
    (+ 1 accum))
  (foldl c-step 0 l))

;; Exercise 3
;; sum: (Listof Real) -> Real
(define (sum l)
  (define (sum-step x accum)
    (+ x accum))
  (foldl sum-step 0 l))

;; Exercise 4
;; occurences: X (Listof X) -> Real
(define (occurrences n l)
  (define (occ-step x accum)
    (cond
      [(equal? x n) (+ 1 accum)]
      [else (+ 0 accum)]))
  (foldl occ-step 0 l))

;; Exercise 5
;; prefix: X (Listof X) -> (Listof X)
(define (prefix s l)
  (match l
    ['() empty]
    [(cons f r)
     (define (pre-step x accum)
       (cons (string-append s x) accum))
     (foldr pre-step empty l)]))

;; Exercise 6
;; interleave: (Listof X) (Listof Y) -> (Listof Z)
(define (interleave l1 l2)
  (match l1
    [(list)
      l2]
    [(cons h1 l1)
     (cons h1 (interleave l2 l1))]))

;; Exercise 7
;; intersperse: (Listof X) X -> (Listof X)
(define (intersperse l v)
  (match l
    ['() empty]
    [(cons f r)
     (define (inter-step x accum)
       (cons x (cons v accum)))
     (foldl cons empty (foldl inter-step (list f) r))]))

;; Exercise 8
;; parse-ast: Quote -> Struct
(define (parse-ast node)
  (define (make-define-func node)
    (r:define
     (parse-ast (first (first (rest node))))
     (r:lambda
      (map parse-ast (rest (first (rest node))))
      (map parse-ast (rest (rest node))))))
      
  (define (make-define-basic node)
    (r:define
     (parse-ast (second node))
     (parse-ast (third node))))
  
  (define (make-lambda node)
    (r:lambda
     (map parse-ast (first (rest node)))
     (map parse-ast (rest (rest node)))))
  
  (define (make-apply node)
    (r:apply
     (parse-ast (first node))
     (map parse-ast (rest node))))
  
  (define (make-number node) (r:number node))
  (define (make-variable node) (r:variable node))

  (cond
    [(define-basic? node) (make-define-basic node)]
    [(define-func? node) (make-define-func node)]
    [(symbol? node) (make-variable node)]
    [(real? node) (make-number node)]
    [(lambda? node) (make-lambda node)]
    [else (make-apply node)]))
