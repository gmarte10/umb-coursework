#!/usr/bin/env racket
#lang racket

(require "interp.rkt")

; Suppresses printing the result (which is invariably undefined)
(display ((interp empty-env) (read)))
