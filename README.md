# The expression problem
This problem is more fundamental than it may seem at first. We have a handful of types, and a handful of high-level operations like “interpret”. For each pair of type and operation, we need a specific implementation. Picture a table:

| -------------- | interepter() | resolver()   | analyze() |
|----------------|--------------|--------------|--------------|
| Binary   |  ||
| Grouping   | ||
| Unary | ||

Rows are types, and columns are operations. Each cell represents the unique piece of code to implement that operation on that type.

An object-oriented language like Java assumes that all of the code in one row naturally hangs together. It figures all the things you do with a type are likely related to each other, and the language makes it easy to define them together as methods inside the same class.

