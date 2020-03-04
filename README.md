# GenericsTabulator (Expression Parser), Recursive Walk, ArraySet, StudentDB, Implementor

<h1>Task 1. File Walker<h1>
  Class RecursiveWalk, counting FNV hash of files in directories.
  Input file contains list of files and directories, which should be recursively visited. Exceptions are caught and handled properly.

Sample:
  Input file:
      java/info/kgeorgiy/java/advanced/walk/samples/binary
      java/info/kgeorgiy/java/advanced/walk/samples

  Output file:
      8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary
      050c5d2e java/info/kgeorgiy/java/advanced/walk/samples/1
      2076af58 java/info/kgeorgiy/java/advanced/walk/samples/12
      72d607bb java/info/kgeorgiy/java/advanced/walk/samples/123
      81ee2b55 java/info/kgeorgiy/java/advanced/walk/samples/1234
      8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary

  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.walk RecursiveWalk <full classname>
       
Task 2. ArraySortedSet 
  Class ArraySet, implementing NavigableSet, is an unmodifiable sorted set. Exceptions are caught and handled properly.
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.arrayset NavigableSet <full classname> 
  
Task 3. Student DataBase  
  Class StudentDB implements AdvancedStudentGroupQuery.
  Lambdas and streams are used.
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.student StudentGroupQuery <full classname> 

Task 4. Implementor
  Class Implementor implements Impler. It generates implementations of Abstract classes and Interfaces, overrided methods return default values.
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.implementor advanced <full classname>   
    
Task 5. Expression Parser
  Implements interfaces Parser. Exceptions are caught and handled properly.
  Classes CheckedAdd, CheckedSubtract, CheckedMultiply, CheckedDivide and CheckedNegate implement TripleExpression.
  Class GenericTabulator implements Tabulator, it makes a sheet of solutions of a given expression.
  mode - modes of evaluation:
    i — int with overflow check;
    d — double without overflow check;
    bi — BigInteger.
    x1, x2 — min and max values of variable x (included)
    y1, y2, z1, z2 — similarly for y and z.
  result[i][j][k] contains result of expression with x = x1 + i, y = y1 + j, z = z1 + k. May be null if is not determined.
  Added operations and modes:
    abs, square, mod (multiply/divide priority).  
    u — int without overflow check;
    f — float without overflow check;
    b — byte without overflow check.
