package network.manu.loginstore;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import network.manu.loginstore.listeners.LoginListener;
import network.manu.loginstore.pojos.LoginEvent;
import org.bson.UuidRepresentation;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.logging.Logger;

public class LoginStore extends Plugin {
    public static Logger logger;
    public static MongoClient mongoClient;
    public static MongoCollection<LoginEvent> loginEvents;
    public static Configuration config;

    @Override
    public void onEnable() {
        logger = getLogger();

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            try (InputStream in = getResourceAsStream("config.yml")) {
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(getDataFolder(), "config.yml"));
        } catch  (IOException e) {
            e.printStackTrace();
        }

        CodecRegistry codecRegistry =
                CodecRegistries.fromRegistries(
                        MongoClientSettings.getDefaultCodecRegistry(),
                        CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
                );
        ConnectionString connectionString = new ConnectionString((String)config.get("mongo-connection-uri"));
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .uuidRepresentation(UuidRepresentation.STANDARD)
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(mongoClientSettings);
        loginEvents = mongoClient.getDatabase((String)config.get("database")).getCollection("loginEvents", LoginEvent.class);

        getProxy().getPluginManager().registerListener(this, new LoginListener());
    }

    @Override
    public void onDisable() {

    }
}
