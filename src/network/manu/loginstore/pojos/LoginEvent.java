package network.manu.loginstore.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.bson.types.ObjectId;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.UUID;

public class LoginEvent {
    @Getter @Setter private ObjectId id;
    @Getter @Setter private String playerName;
    @Getter @Setter private UUID playerUUID;
    @Getter @Setter private long time;
    @Getter @Setter private LoginAction action;
    @Getter @Setter private String IP;

    public LoginEvent() {
        time = System.currentTimeMillis();
    }

    public LoginEvent(LoginAction action) {
        this();
        this.action = action;
    }

    public LoginEvent(LoginAction action, ProxiedPlayer player) {
        this(action);
        playerName = player.getName();
        playerUUID = player.getUniqueId();

        SocketAddress socketaddress = player.getSocketAddress();
        if (socketaddress instanceof InetSocketAddress) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) socketaddress;
            IP = inetSocketAddress.getHostName();
        }
    }
}
