/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import ac.gophers.domain.measurifaceconfigements.ConfigServiceGrpc;
import ac.gophers.domain.measurifaceconfigements.IfaceConfig;
import ac.gophers.domain.measurifaceconfigements.IfaceConfig.Empty;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author drs
 */
public class GrpcExamples {

    private static final Logger logger = Logger.getLogger(GrpcExamples.class.getName());

    private static ManagedChannel channel;
    private static ConfigServiceGrpc.ConfigServiceBlockingStub blockingStub;

    @Before
    public void setUp() {
        channel = ManagedChannelBuilder.forAddress("127.0.0.1", 50051)
                .usePlaintext(true)
                .build();
        blockingStub = ConfigServiceGrpc.newBlockingStub(channel);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSetInterfaceConfig() {
        IfaceConfig.InterfaceConfig config = IfaceConfig.InterfaceConfig.newBuilder()
                .setChannel(6)
                .setBeaconEnabled(true)
                .setSsid("my new SSID")
                .build();

        try {
            Empty response = blockingStub.setInterfaceConfig(config);
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }
    
     @Test
    public void testGetInterfaceConfig() {       
        try {
            IfaceConfig.InterfaceConfig config  = blockingStub.getInterfaceConfig(Empty.getDefaultInstance());
            logger.log(Level.INFO, "Received = {0}", config.toString());
        } catch (StatusRuntimeException e) {
            logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus());
            return;
        }
    }
}
