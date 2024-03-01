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
;; PLEASE DO NOT CHANGE THE FOLLOWING LINES
(require "hw7-util.rkt")
(provide (all-defined-out))
;; END OF REQUIRES
(require racket/set)
;;;;;;;;;;;;;;;
;; Exercise 1
;
; Given a frame, return all handles contained therein.
;
; Use frame-values; only closure's env is relevant to this problem
; Read the hints in the PDF.
(: frame-refs (frame -> (Setof handle)))
(define (frame-refs frm)
  ; parent handle
  (define p-h (frame-parent frm))
  
  ; frame values
  (define frm-v (frame-values frm))
  
  ; frame closures
  (define frm-c (filter d:closure? frm-v))
  
  ; closure environments
  (define c-env (map d:closure-env frm-c))
  
  ; local handles
  (define local-h (list->set c-env))

  (cond
    ; no parent
    [(equal? #f p-h) local-h]
 
    [else
     (set-union (set p-h) local-h)]))
  

;;;;;;;;;;;;;;;
;; Exercise 2
; Standard graph algorithm: return all handles reacheable via `contained`.
; Hint: Consider solving this exercise last, as conceptually the most difficult
;       exercise.
; Hint: This is a simple breadth-first algorith. The algorithm should start
;       in env and obtain the set of next elemens by using `contained`.
; Hint: The algorithm must handle cycles.
; Hint: Do not hardcode your solution to frames (you should test it with
;       frames though)

(: mem-mark
   (All [T]
        (T -> (Setof handle))
        (heap T)
        handle
        ->
        (Setof handle)))
(define (mem-mark contained mem env)

  ; (: mem-mark-iter (-> (Listof handle) (Setof handle) (Setof handle)))
  ; One solution to this problem is to loop while maintaining
  ; a list of environments to visit, and a set of elements visited
  ; (define (mem-mark-iter to-visit visited)
  ;   ; while not empty(to-visit):
  ;   ;    1. pick one env from from to-visit and retrieve its "frame"
  ;   ;    2. let "c" be the set of env's contained in the given "frame"
  ;   ;    3. let the set "new" be every element that is in "c" but not in "visited" (use set-minus)
  ;   ;    4. add every element of "new" to the rest of elements to be visited
  ;   ;    5. update the set of visited elements to include the new elements
  ;   (todo "todo"))
  ; ; run the loop with 1 element to visit, and 1 element visited
  ; (mem-mark-iter (list env) (set env))
  (: mem-mark-iter ((Listof handle) (Setof handle) -> (Setof handle)))
  (define (mem-mark-iter to-visit visited)
    (match to-visit
      ['() visited]
      [(cons h l)
       (define h-env-frm (heap-get mem h))
       (define c (contained h-env-frm))
       (define new (set-subtract c visited))
       (define new-list (set->list new))
       (define new-add-to-visit(append l new-list))
       (define new-update-visited (set-union new visited))
       (mem-mark-iter new-add-to-visit new-update-visited)]))
  (mem-mark-iter (list env) (set env)))

;;;;;;;;;;;;;;;
;; Exercise 3
;
; Return a new heap that only contains the key-values referenced in to-keep.
;
; Tip 1: We have learned a similar pattern than what is asked here.
; Tip 2: The function you want to use starts with heap-
; Tip 3: The solution is a one-liner

(: mem-sweep
  (All [T]
       ; heap
       (heap T)
       ; set of handles to keep
       (Setof handle)
       ; the new heap
       ->
       (heap T)))

(define (mem-sweep mem to-keep)
  (: mem-sweep-inner
     (; key
      handle
      ; value
      T
      ; return boolean
      ->
      Boolean))
  
  (define (mem-sweep-inner k v)
    (set-member? to-keep k))
    
  (heap-filter mem-sweep-inner mem))


;;;;;;;;;;;;;;;
;; Exercise 4

(define-syntax do
  (syntax-rules (: <-)
    [(do expr)
     expr]
    [(do var : type <- rhs rest ...)
     (eff-bind rhs (lambda ([var : type]) (do rest ...)))]))

(: eff-map
   (All [State Input Output]
        (Input -> Output)
        (Listof (eff-op State Input))
        ->
        (eff-op State (Listof Output))))

(define (eff-map f l)
  (: map-iter ((Listof Output) (Listof (eff-op State Input)) -> (eff-op State (Listof Output))))
  (define (map-iter accum l)
    (match l
      ['()
       (eff-pure (reverse accum))]
      [(cons h l)
       (do h-val : Input <- h
         (map-iter (cons (f h-val) accum) l))]))
  (map-iter (list) l))
  

;;;;;;;;;;;;;;;
;; Exercise 5

(: eff-exists?
  (All [State T]
       (T -> Boolean)
       (Listof (eff-op State T))
       ->
       (eff-op State Boolean)))

(define (eff-exists? f l)
  (: exists-iter (Boolean (Listof (eff-op State T)) -> (eff-op State Boolean)))
  (define (exists-iter accum l)
    (cond
      [(equal? #t accum) (eff-pure #t)]
      [else
       (match l
         ['()
          (eff-pure accum)]
         [(cons h l)
          (do h-val : T <- h
            (exists-iter (f h-val) l))])]))
  (exists-iter #f l))
    
;;;;;;;;;;;;;;;
;; Exercise 6 (MANUALLY GRADED)
#|
It affects soundness of memory management because the reference count is going to
reuse numbers that are already being used as references. This will most
likely mess up how the memory works. |#
