package lia;

import com.google.protobuf.InvalidProtocolBufferException;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import static lia.AiApiMessages.*;

/**
 * Handles the connection to the game engine and takes
 * care of sending and retrieving data.
 **/
public class NetworkingClient extends WebSocketClient {

    private Callable myBot;

    public static NetworkingClient create(String uri, String playerId, Callable myBot) throws URISyntaxException {
        Map<String,String> httpHeaders = new HashMap<>();
        httpHeaders.put("Id", playerId);
        return new NetworkingClient(new URI(uri), httpHeaders, myBot);
    }

    private NetworkingClient(URI serverUri, Map<String, String> httpHeaders, Callable myBot) {
        super(serverUri, httpHeaders);
        this.myBot = myBot;
    }

    @Override
    public void onMessage(ByteBuffer bytes) {
        try {
            Message msg = Message.parseFrom(bytes.array());

            Response response = null;

            // If the message is data about map
            if (msg.hasMapData()) {
                MapData mapData = msg.getMapData();
                response = new Response(mapData.getUid());
                myBot.process(mapData);
            }
            // If the message is an update about game state
            else if (msg.hasStateUpdate()) {
                StateUpdate stateUpdate = msg.getStateUpdate();
                response = new Response(stateUpdate.getUid());
                myBot.process(stateUpdate, response);
            }

            // Send response back to the game engine
            send(response.toByteArray());

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed. Exiting...");
        System.exit(0);
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {}

    @Override
    public void onMessage(String message) {}
}