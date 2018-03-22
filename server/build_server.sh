#!/bin/bash

GOPATH=$(go env GOPATH):$(pwd)
PROTO_PATH=$(readlink -f ../idl) go generate src/m9d.de/grpc-go-server/main.go 
rm bin/grpc-go-server
go install m9d.de/grpc-go-server/
