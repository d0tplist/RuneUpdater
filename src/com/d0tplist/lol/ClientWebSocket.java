package com.d0tplist.lol;

import com.google.gson.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.Type;
import java.net.URI;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class ClientWebSocket extends WebSocketClient {

    private SocketListener socketListener;
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(Event.class, new EventDeserializer())
            .registerTypeAdapter(Message.class, new MessageDeserializer())
            .create();
    private static HashMap<Pattern, Class> patterns = new HashMap<>();

//    static {
//        for (String s : UriMap.toClass.keySet()) {
//            patterns.put(Pattern.compile(s), UriMap.toClass.get(s));
//        }
//    }

    ClientWebSocket(String token, int port) throws Exception {
        super(new URI("wss://localhost:" + port + "/"), createHeaders("Authorization", "Basic " + token));
        //ignore SSL as
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }

                }
        };
        SSLContext sslContext;
        sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, null);

        SSLSocketFactory factory = sslContext.getSocketFactory();
        setSocket(factory.createSocket());
        connectBlocking();
        subscribe("OnJsonApiEvent");
    }

    private static Map<String, String> createHeaders(String... headers) {
        if (headers.length % 2 != 0) throw new IllegalArgumentException("Invalid amount of parameters!");
        HashMap<String, String> map = new HashMap<>();
        for (int i = 0; i < headers.length; i += 2) {
            String key = headers[i];
            String value = headers[i + 1];
            map.put(key, value);
        }
        return map;
    }

    public void subscribe(String event) {
        sendMessage(MessageType.SUBSCRIBE, event);
    }

    public void unsubscribe(String event) {
        sendMessage(MessageType.UNSUBSCRIBE, event);
    }


    public void onOpen(ServerHandshake handshakedata) {
    }

    public void onClose(int code, String reason, boolean remote) {
        if (socketListener != null) {
            socketListener.onClose(code, reason);
        }
    }

    public void onMessage(String message) {
        if (message.isEmpty()) return;
        Message mess = GSON.fromJson(message, Message.class);
        if (mess.type == MessageType.EVENT && socketListener != null) {
            socketListener.onEvent(mess.event);
        }
    }

    public void setSocketListener(SocketListener socketListener) {
        this.socketListener = socketListener;
    }

    public void sendMessage(MessageType type, String message) {
        send(GSON.toJson(new Object[]{type.getId(), message}));
    }

    @Override
    public void onError(Exception ex) {
        ex.printStackTrace();
    }

    public interface SocketListener {

        void onEvent(Event event);

        void onClose(int code, String reason);
    }

    public enum MessageType {
        WELCOME(0),
        PREFIX(1),
        CALL(2),
        CALLRESULT(3),
        CALLERROR(4),
        SUBSCRIBE(5),
        UNSUBSCRIBE(6),
        PUBLISH(7),
        EVENT(8);

        private static final HashMap<Integer, MessageType> fromId = new HashMap<>();

        static {
            for (MessageType type : values()) {
                fromId.put(type.id, type);
            }
        }

        private final int id;

        MessageType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public static MessageType getById(int id) {
            return fromId.get(id);
        }

    }

    public static class Message {
        private MessageType type;
        private String source;
        private Event event;

        private Message(MessageType type, String source, Event event) {
            this.type = type;
            this.source = source;
            this.event = event;
        }

        public MessageType getType() {
            return type;
        }

        public String getSource() {
            return source;
        }

        public Event getEvent() {
            return event;
        }

        @Override
        public String toString() {
            return "Message{" +
                    "type=" + type +
                    ", source='" + source + '\'' +
                    ", event=" + event +
                    '}';
        }
    }

    public static class Event {
        private Object data;
        private String eventType;
        private String uri;

        private Event(Object data, String eventType, String uri) {
            this.data = data;
            this.eventType = eventType;
            this.uri = uri;
        }

        public Object getData() {
            return data;
        }

        public String getEventType() {
            return eventType;
        }

        public String getUri() {
            return uri;
        }

        @Override
        public String toString() {
            return "Event{" +
                    "eventType='" + eventType + '\'' +
                    ", uri='" + uri + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public static class EventDeserializer implements JsonDeserializer<Event> {

        @Override
        public Event deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jObject = (JsonObject) json;
            String uri = jObject.get("uri").getAsString();
            String eventType = jObject.get("eventType").getAsString();
            Object data = jObject.get("data");
//            Class c = UriMap.toClass.get(uri);
//            if (c == null) {
//                for (Pattern pattern : patterns.keySet()) {
//                    if (pattern.matcher(uri).matches()) {
//                        c = patterns.get(pattern);
//                    }
//                }
//            }
//            if (c == null) data = context.deserialize((JsonElement) data, Object.class);
//            else {
//                try {
//                    data = context.deserialize((JsonElement) data, c);
//                } catch (JsonSyntaxException e) {
//                    System.out.println("Failed to deserialize from URI " + uri);
//                    return new Event(data, eventType, uri);
//                }
//            }
//            return new Event(data, eventType, uri);


            return new Event(data, eventType, uri);
        }
    }

    public static class MessageDeserializer implements JsonDeserializer<Message> {

        @Override
        public Message deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonArray jArr = (JsonArray) json;
            MessageType type = MessageType.getById(jArr.get(0).getAsInt());
            String source = jArr.get(1).getAsString();
            Event event = null;
            if (type == MessageType.EVENT) {
                event = context.deserialize(jArr.get(2), Event.class);
            }
            return new Message(type, source, event);
        }
    }

}
