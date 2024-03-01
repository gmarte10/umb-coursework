#lang typed/racket
#|
    ===> PLEASE DO NOT DISTRIBUTE THIS FILE <===

  You are encouraged to read through the file for educational purposes,
  but you should not make this file available to a 3rd-party, e.g.,
  by making the file available in a website.

  Students are required to adhere to the University Policy on Academic
  Standards and Cheating, to the University Statement on Plagiarism and the
  Documentation of Written Work, and to the Code of Student Conduct as
  delineated in the catalog of Undergraduate Programs. The Code is available
  online at: http://www.umb.edu/life_on_campus/policies/code/

|#
(provide (all-defined-out))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Constants
(: k:const? (Any -> Boolean : k:const))
(define (k:const? v)
  (or (k:number? v)
      (k:string? v)
      (k:bool? v)
      (k:undef? v)))

(define-type k:const
  (U k:number k:string k:bool k:undef))

(struct k:number ([value : Real]) #:transparent)
(struct k:string ([value : String]) #:transparent)
(struct k:bool ([value : Boolean]) #:transparent)
(struct k:undef () #:transparent)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;                      SOURCE LANGUAGE
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; Simple JS

(define-type s:value k:const)
(define-type s:expression
  (U s:value
     s:variable
     s:assign
     s:invoke
     s:apply
     s:invoke
     s:load
     s:function
     s:new
     s:class
     s:let))

(struct s:variable
  ([name : Symbol])
  #:transparent)

(struct s:load
  ([obj : s:variable]
   [field : s:variable])
  #:transparent)

(struct s:assign
  ([obj : s:variable]
   [field : s:variable]
   [arg : s:expression])
  #:transparent)

(struct s:function
  ([params : (Listof s:variable)]
   [body : s:expression])
  #:transparent)

(struct s:invoke
  ([obj : s:variable]
   [meth : s:variable]
   [args : (Listof s:expression)])
  #:transparent)

(struct s:apply
  ([func : s:expression]
   [args : (Listof s:expression)])
  #:transparent)

(struct s:new
  ([constr : s:expression]
   [args : (Listof s:expression)])
  #:transparent)

(struct s:class
  ([parent : s:expression]
   [methods : (Immutable-HashTable s:variable s:function)])
  #:transparent)

(struct s:let
  ([name : s:variable]
   [body : s:expression]
   [kont : s:expression])
  #:transparent)

;; Helper function. In JS it corresponds to: e1; e2
(: s:seq (s:expression s:expression -> s:expression))
(define (s:seq e1 e2)
  (s:let (s:variable '_) e1 e2))

;; Heper function. Given a list of expressions e1...en does: e1; ...; en
(: s:begin ((Listof s:expression) -> s:expression))
(define (s:begin es)
  (: stmts (Listof s:expression))
  (define stmts (reverse es))
  (if (empty? es)
    (k:undef)
    (foldl s:seq (first stmts) (rest stmts))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;                      TARGET LANGUAGE
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; Values
(define-type j:value k:const)

;; Lambda-calculus
(struct j:variable ([name : Symbol]) #:transparent)

(struct j:lambda
  ([params : (Listof j:variable)]
   [body : j:expression])
  #:transparent)

(struct j:apply
  ([func : j:expression]
   [args : (Listof j:expression)])
  #:transparent)

;; Object-related operations
(struct j:object
  ([data :  (Immutable-HashTable k:string j:expression)])
  #:transparent)

(struct j:get
  ([obj : j:expression]
   [field : j:expression])
  #:transparent)

(struct j:set
  ([obj  : j:expression]
   [field : k:string]
   [arg : j:expression])
  #:transparent)

;; Heap-related operations
(struct j:alloc ([value : j:expression]) #:transparent)
(struct j:deref ([value : j:expression]) #:transparent)
(struct j:assign
  ([ref : j:expression]
   [value : j:expression])
  #:transparent)

(define-type j:expression
  (U ; lambda
     j:value j:variable j:lambda j:apply
     ; objects
     j:object j:set j:get
     ; memory
     j:alloc j:deref j:assign
     ))

;; ----

;; Helper function. In LambdaJS it corresponds to: (let ((x e1)) e2)
(: j:let (-> j:variable j:expression j:expression j:apply))
(define (j:let x e e-in)
  (j:apply (j:lambda (list x) e-in) (list e)))

;; Helper function. In LambdaJS it corresponds to: (begin e1 e2)
(: j:seq (j:expression j:expression -> j:expression))
(define (j:seq e1 e2)
  (j:let (j:variable '_) e1 e2))

;; A safer-version of let that automatically generates a variable name.
;; (mk-let e1 (lambda (x) x))  <- let x = e1 in x
(: mk-let (j:expression (j:variable -> j:expression) -> j:apply))
(define (mk-let e e-in)
  (let ([x (mk-var!)])
    (j:let x e (e-in x))))

(: var-count (Parameter (Boxof Integer)))
(define var-count (make-parameter (box 0)))

(: mk-var! (->* [] [Symbol] j:variable))
(define (mk-var! [prefix '@gen])
  (define ref (var-count))
  (define count (unbox ref))
  (set-box! ref (+ count 1))
  (j:variable (string->symbol (format "~a~a" prefix count))))

;; Utility function that converts a variable into a string
;; Useful when translating from SimpleJS into LambdaJS
(: mk-field (s:variable -> k:string))
(define (mk-field x)
  (match x
    [(s:variable x)
     (k:string (symbol->string x))]))

;; Utility function that allocates a j:object.
;; (mk-object) allocates an empty object
;; (mk-object (cons "foo" (k:number 1))) allocates an object with one field "foo"
(: mk-object (->* [] #:rest (Pair String j:expression) j:alloc))
(define (mk-object . args)
  (: on-elem (-> (Pairof String j:expression) (Pairof k:string j:expression)))
  (define (on-elem pair)
    (cons (k:string (car pair)) (cdr pair)))
  (j:alloc (j:object (make-immutable-hash (map on-elem args)))))
