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
(require "hw6-util.rkt")
(provide (all-defined-out))
;; END OF REQUIRES

;; Exercise 1
(: eval-exp (memory handle d:expression -> (eff memory d:value)))
(define (eval-exp mem env exp)
  ; mem is M
  ; env is E
  (match exp
    [(? d:value?)
     ; Return: v ▶ M
     (error "todo!")]
    [(? d:variable?) ; exp is x
     ; Return: E(x) ▶ M
     (error "todo!")]
    [(d:lambda x t)
     ; Return: {E, λx.t} ▶ M
     (error "todo!")]
    [(d:apply ef ea)
     (match (eval-exp mem env ef)
       ;; ef ⇓E {Ef, λx.tb} ▶ M1
       [(eff M1 (d:closure Ef x tb))
        ;; ea ⇓E va ▶ M2
        ;; ...
        ;; Eb ← Ef + [x := a] ▶ M3
        ;; ...
        ;; tb ⇓Eb vb ▶ M4
        ;; ...
        ;; Return: vb ▶ M4
        (error "todo!")])]))

;; Exercise 2

(: eval-term (memory handle d:term -> (eff memory d:value)))
(define (eval-term mem env term)
  (match term
    [(d:define x e)
     ;; e ⇓E v ▶ M1
     ;; ...
     ;; E ← [x := v] ▶ M2
     ;; ...
     ;; Return: void ▶ M2
     (error "todo!")]
    [(d:seq t1 t2)
     ;; ​t1​ ⇓E ​v1 ▶ M1
     ;; ...
     ;; t2 ⇓E v2 ▶ M2
     ;; ...
     ;; Return: v2 ▶ M2
     (error "todo!")]
    [(? d:expression?)
     (eval-exp mem env term)]))

;; Exercise 3 (Manually graded)
#|
PLEASE REPLACE THIS TEXT BY YOUR ANSWER.
YOU MAY USE MULTIPLE LINES.
|#
