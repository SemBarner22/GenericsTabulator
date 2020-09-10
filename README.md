# GenericsTabulator (Expression Parser), Recursive Walk, ArraySet, StudentDB, Implementor

<p><em>Tests, necessary jars and scripts are in folder "artifacts", its content should be put in out/production/GenericsTabulator</em></p>
<h1>Task 1. File Walker</h1>
  <p>Class RecursiveWalk, counting FNV hash of files in directories.<br>
  Input file contains list of files and directories, which should be recursively visited. Exceptions are caught and handled properly.</p>

<p>Sample:<br>
  &nbsp;&nbsp;Input file:</p>
      <p>&nbsp;&nbsp;&nbsp;&nbsp;java/info/kgeorgiy/java/advanced/walk/samples/binary<br>
      &nbsp;&nbsp;&nbsp;&nbsp;java/info/kgeorgiy/java/advanced/walk/samples</p>

  <p>&nbsp;&nbsp;Output file:</p>
      <p>&nbsp;&nbsp;&nbsp;&nbsp;8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary<br>
      &nbsp;&nbsp;&nbsp;&nbsp;050c5d2e java/info/kgeorgiy/java/advanced/walk/samples/1<br>
      &nbsp;&nbsp;&nbsp;&nbsp;2076af58 java/info/kgeorgiy/java/advanced/walk/samples/12<br>
      &nbsp;&nbsp;&nbsp;&nbsp;72d607bb java/info/kgeorgiy/java/advanced/walk/samples/123<br>
      &nbsp;&nbsp;&nbsp;&nbsp;81ee2b55 java/info/kgeorgiy/java/advanced/walk/samples/1234<br>
      &nbsp;&nbsp;&nbsp;&nbsp;8e8881c5 java/info/kgeorgiy/java/advanced/walk/samples/binary</p>

  <p>Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.walk RecursiveWalk <full classname></p>
       
<h1>Task 2. ArraySortedSet</h1> 
  <p>Class ArraySet, implementing NavigableSet, is an unmodifiable sorted set. Exceptions are caught and handled properly.<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.arrayset NavigableSet <full classname></p>
  
<h1>Task 3. Student DataBase</h1>  
  <p>Class StudentDB implements AdvancedStudentGroupQuery.<br>
  Lambdas and streams are used.<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.student StudentGroupQuery <full classname></p>

<h1>Task 4. Implementor</h1>
  <p>Class Implementor implements Impler. It generates implementations of Abstract classes and Interfaces, overrided methods return default values.<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.implementor advanced <full classname></p>
    
<h1>Task 5. Expression Parser</h1>
  <p>Implements interfaces Parser. Exceptions are caught and handled properly.<br>
  Classes CheckedAdd, CheckedSubtract, CheckedMultiply, CheckedDivide and CheckedNegate implement TripleExpression.<br>
  Class GenericTabulator implements Tabulator, it makes a sheet of solutions of a given expression.<br>
  mode - modes of evaluation:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;pi — int with overflow check;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;d — double without overflow check;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;bi — BigInteger.<br>
    &nbsp;&nbsp;&nbsp;&nbsp;x1, x2 — min and max values of variable x (included)<br>
    &nbsp;&nbsp;&nbsp;&nbsp;y1, y2, z1, z2 — similarly for y and z.<br>
  result[i][j][k] contains result of expression with x = x1 + i, y = y1 + j, z = z1 + k. May be null if is not determined.<br>
  Added operations and modes:<br>
    &nbsp;&nbsp;&nbsp;&nbsp;abs, square, mod (multiply/divide priority). <br> 
    &nbsp;&nbsp;&nbsp;&nbsp;u — int without overflow check;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;f — float without overflow check;<br>
    &nbsp;&nbsp;&nbsp;&nbsp;b — byte without overflow check.<br>
  Usage example: System.out.println(parse("1 + 5 mod 3", new FloatOperator()).evaluate(-3f, -5f, -7f));</p> 
  
<h1>Tasks 6. Iterative Parallelism</h1>
  <p>Implements interfaces ListIP. Throws InterruptedException<br>
   &nbsp;&nbsp;filter(threads, list, predicate) — return List containing elements, passed predicate;<br>
   &nbsp;&nbsp;map(threads, list, function) — return List containing mapped elements;<br>
   &nbsp;&nbsp;join(threads, list) — concatenate elements to String.</p>
  
<h1>Tasks 7. Parallel Mapper</h1>
  <p>Implements interfaces ListIP. Throws InterruptedException<br>
   &nbsp;&nbsp;filter(threads, list, predicate) — return List containing elements, passed predicate;<br>
   &nbsp;&nbsp;map(threads, list, function) — return List containing mapped elements;<br>
   &nbsp;&nbsp;join(threads, list) — concatenate elements to String.<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.mapper advanced <full classname></p>
  
<h1>Tasks 7. Parallel Mapper</h1>
  <p>Implements interfaces ListIP. Throws InterruptedException<br>
   &nbsp;&nbsp;filter(threads, list, predicate) — return List containing elements, passed predicate;<br>
   &nbsp;&nbsp;map(threads, list, function) — return List containing mapped elements;<br>
   &nbsp;&nbsp;join(threads, list) — concatenate elements to String.<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.mapper advanced <full classname></p> 
 
 <h1>Tasks 9. Web Crawler</h1>
  <p>Implements interfaces ListIP. Throws InterruptedException<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.crawler hard <full classname></p>
  
 <h1>Tasks 10. HelloUDP</h1>
  <p>Implements interfaces ListIP. Throws InterruptedException<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.hello server-evil<br>
  Usage: java -cp . -p . -m info.kgeorgiy.java.advanced.hello client-evil
  <full classname></p>
  
