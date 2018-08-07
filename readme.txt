
0, Execute the war file.
  a, either run "java -jar jetty-runner.jar ot-0.0.1-SNAPSHOT.war" under this directory \test\package\
  b, or, if maven is availabe, run "mvn jetty:run" under this directory \test\code\ot\


1, Get random question.
  http://localhost:8080/ot/quizr
  GET request: use Postman (a Chrome browser plugin app)
  response: {"quizId":1,"quizType":11,"quizText":"Question #1"}

  Try more hit, will get random question e.g. {"quizId":5,"quizType":15,"quizText":"Question #5"} in response.


2, Commit and get reply.
  http://localhost:8080/ot/commit 
  POST request: use Postman, and put in body the json {"userId":"123","quizId":"1","quizType":"11","inputText":"foobar"}
  response: {"userId":123,"quizId":"1","quizType":11,"inputText":"foobar","outputCategary":1,"outputText":"Execution Result is correct: foobar"}

  Try more hit with "inputText" other than "foobar", will have "outputCategary":2 and "outputText":"Execution Result is incorrect: xxx from agent..." in response.


3, Sample data.
  http://localhost:8082 , it's H2 embedded database (url is jdbc:h2:~/test , user is sa, password is blank), holds a table, SELECT * FROM QUIZ : 
  1	11	Question #1
  2	12	Question #2
  3	13	Question #3
  4	14	Question #4
  5	15	Question #5


4, Port 61616 is started by ActiveMQ broker to mimic sent out message to some agent box to execute input (not implemented).
  netstat -na | find "61616"


5, Not did yet, testcases or cache or user domain hibernate etc.


6, Dependencies are in file dependency.txt
  mvn dependency:tree
