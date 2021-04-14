LoginStore
-
This is a Bungeecord plugin that connects to a Mongo database—MongoDB—and stores loginEvents—login and logout.


What does it store?
-
- event time
- event type (login/logout)
- player name
- player uuid
- player IP

Where does it store data?
-
- mongo connection in config (default 'mongodb://localhost:27017')
- database in config (default 'serversnetwork')
- 'loginEvents' collection (not configurable)
