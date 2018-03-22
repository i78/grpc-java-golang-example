//go:generate protoc -I $PROTO_PATH  --go_out=plugins=grpc:vendor/ $PROTO_PATH/IfaceConfig.proto

package main

import (
	"log"
	"net"

	"golang.org/x/net/context"
	"google.golang.org/grpc"
	"google.golang.org/grpc/reflection"
	pb "gophers.ac/domain/ifaceconfig"
)

const (
	port = ":50051"
)

var ifaceConfig *pb.InterfaceConfig

type server struct{}

func (s *server) SetInterfaceConfig(ctx context.Context, config *pb.InterfaceConfig) (result *pb.Empty, err error) {
	result = &pb.Empty{}
	log.Printf("SetInterfaceConfig config=%v", config)
	ifaceConfig = config
	return
}

func (s *server) SetInterfaceStatus(context.Context, *pb.InterfaceStatus) (result *pb.Empty, err error) {
	return
}

func (s *server) GetInterfaceConfig(context.Context, *pb.Empty) (config *pb.InterfaceConfig, err error) {
	config = ifaceConfig
	log.Printf("GetInterfaceConfig config=%v", config)
	return
}

func main() {
	lis, err := net.Listen("tcp", port)
	if err != nil {
		log.Fatalf("failed to listen: %v", err)
	}

	s := grpc.NewServer()
	pb.RegisterConfigServiceServer(s, &server{})

	// Register reflection service on gRPC server.
	reflection.Register(s)
	log.Printf("Server listening on Port %s\n", port)
	if err := s.Serve(lis); err != nil {
		log.Fatalf("failed to serve: %v", err)
	}
}
