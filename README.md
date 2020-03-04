# GenericsTabulator

Task 1. File Walker
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
    
      
