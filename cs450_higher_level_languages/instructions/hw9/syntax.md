# Syntax of SimpleJS

A SimpleJS `<Expr>` is one of the following:
- `<Constant>`
- `<FieldRef>` -- represents a field lookup; the first part is a variable that should contain an object, and the second part is the field name
- `<SafeId>` -- represents a variable, possibly the name of a built-in operator
- `(let <SafeId> <Expr> <Expr>)`
- `(begin <Expr> ...)`
- `(set! <FieldRef> <Expr>)` -- represents a field assignment
- `(function (<SafeId> ...) <Expr>)`
- `(new <Expr> <Expr> ...)` -- constructor call; the first expression is the constructor function, and the rest of the expressions are the arguments
- `(<FieldRef> <Expr> ...)` -- represents a method call
- `(! <Expr> <Expr> ...)` -- represents a function call or use of a built-in operator

A `<Constant>` is one of the following:
- `<Number>`
- `<String>`
- `<Boolean>`
- `undefined`

A `<FieldRef>` consists of two names joined by a dot. For example: `x.y`,
`p1.translate`, etc.

A `<SafeId>` is a name that does not begin with the `@` character (reserved for
use by the translator).

# Syntax of LambdaJS

A LambdaJS `<Expr>` is one of the following:
- `<Constant>`
- `<Name>` -- variable reference
- `(object [<String> <Expr>] ...)`
- `(lambda (<Name> ...) <Expr>)`
- `(let ([<Name> <Expr>] ...) <Expr>)`
- `(alloc <Expr>)` -- allocates a mutable reference initially containing the value of the given expression
- `(set! <Expr> <Expr>)` -- updates the mutable reference produced by the first expression to contain the value produced by the second
- `(deref <Expr>)` -- accesses the value contained by the given mutable reference
- `(get-field <Expr> <Expr>)` -- field access; the first expression is the object, the second expression is the field label (a string value)
- `(update-field <Expr> <Expr> <Expr>)` -- field assignment
- `(delete-field <Expr> <Expr>)` -- field deletion
- `(begin <Expr> ...)`
- `(if <Expr> <Expr> <Expr>)`
- `(<JS-Operator> <Expr> ...)` -- call a built-in JS operator
- `(<Expr> <Expr> ...)` -- call a function

A `<JS-Operator>` is one of the following:
- `+`, `string-+`,
  `%`, `-`, `*`, `/`, `===`, `==`,
  `<`, `string-<`,
  `&`, `|`, `^`, `~`, `<<`, `>>`, `>>>`,
  `to-integer`, `to-uint-32`, `to-int-32`,
  `=`, 
  `typeof`, `surface-typeof`,
  `prim->number`, `prim->string`, `prim->bool`,
  `has-own-prop?`,
  `print-string`,
  `str-contains`, `str-startswith`, `str-length`, `str-split-regexp`, `str-split-strexp`,
  `regexp-quote`, `regexp-match`,
  `obj-iterate-has-next?`, `obj-iterate-next`, `obj-iterate-key`,
  `obj-delete`, `obj-can-delete?`
  `math-sin`, `math-cos`, `math-log`, `math-exp`, `math-abs`, `math-pow`, 
  `prim?`
