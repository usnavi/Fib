Fib

Greg Sharek

1/17/2013

DESCRIPTION

A Fibonacci RESTful web service.  This project uses jersey & the grizzly http server to 
create a RESTful web service which returns a list of the first n instances in the 
Fibonacci sequence.  The web service is located at:

http://localhost:8080/fibonacci/<non-negative integer>

The reply to an HTTP GET request is of the following format:

<fibonacci>
    <value index="0">0</value>
    <value index="1">1</value>
    <value index="2">1</value>
    <value index="3">2</value>
    ...
</fibonacci>

All other GET requests, including those with negative numbers for the instance count, 
will receive status 404

An internal server errors will get a response of Status code 500 and:

<error>erorr message</error>

The XML Schema for the fibonacci tag is at xsd/FibServer.xsd

This project has been verified with Java 1.7_09.

BUILD

To build the system, have ant in your path and execute the following from root directory:

ant jar

The jar file will be built at build/jar/Fib.jar

DEPLOY

To deploy the server, have ant in your path, ensure that the jar has been built at
build/jar/Fib.jar and run:

ant run

The server can be verified by using curl:

curl -v http://localhost:8080/fibonacci/5
curl -v http://localhost:8080/fibonacci/-1

To stop the server, hit return.

TEST

The project contains 2 types of unit tests.  Both can be executed by having ant in your path
and running:

ant junit|junit-rest

The junit test verifies the public methods on gregsharek.FibCalc

The junit-rest tests web service interface and requires a server be running at http://localhost:8080




