package network.manu.loginstore.listeners;

import jline.internal.Log;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import network.manu.loginstore.LoginStore;
import network.manu.loginstore.pojos.LoginAction;
import network.manu.loginstore.pojos.LoginEvent;

public class LoginListener implements Listener {

    @EventHandler
    public void listen(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();

        LoginEvent loginEvent = new LoginEvent(LoginAction.LOGIN, player);

        LoginStore.loginEvents.insertOne(loginEvent);
    }

    @EventHandler
    public void listen(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();

        LoginEvent loginEvent = new LoginEvent(LoginAction.LOGOUT, player);

        LoginStore.loginEvents.insertOne(loginEvent);
    }
}
