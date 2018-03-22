# gRPC with Golang and Java Example

This example demonstrates a gRPC example workflow of a Java client (i.E. an Android application) remove-invoking methods on a go service.

## Prerequisites
To execute the examples, you need:
* Go >= 1.8 
* maven
* protobuf compiler

## Starting the examples

### Go application:
```
cd server
./build_server.sh
bin/grpc-go-server
```

### Java application
For convenience purposes, this example (ab)uses jUnit to trigger sniplets of code.

```
cd client/WifiConfigClient
# Run SetInterfaceConfig Test
mvn clean test -Dtest=GrpcExamples#testSetInterfaceConfig

# Run SetInterfaceConfig Test
mvn clean test -Dtest=GrpcExamples#testGetInterfaceConfig
```