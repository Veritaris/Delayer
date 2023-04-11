# Delayer

### Simple tool for management commands on player join to server

### Why?

Sometimes you need to save player from crash-zone or need for player to be online for smth but cannot
connect with him  
Now can delay command until player log into game and given command will be executed over him

### Usage:

Note: you have to enter command with respect to player nickname, but you can also replace it with `$p$` 
to avoid writing username several times and making mistakes 

Set command to be executed for `player`  
```/delay set <player> <command>```  
Example:  
```/delay set Veritaris kick $p$ too good developer```

Remove command to be executed for `player`  
```/delay remove <player>```  
Example:  
```/delay remove Veritaris```

List first 8 delayed commands  
```/delay list```  
Example:  
```/delay list```

Get commands on Nth page  
```/delay page N```  
Example:  
```/delay page 2```

Reload plugin, this also re-fetch data into memory for **Filebased** backend  
```/delay reload```  
Example:  
```/delay reload```

Set command to be executed for `player`  
```/delay set <player> <command>```  
Example:  
```/delay set Veritaris kick $p$ too good developer```

Set command to be executed for `player`  
```/delay set <player> <command>```  
Example:  
```/delay set Veritaris kick $p$ too good developer```

Roadmap:

- [x] In-memory command line interface
- [x] Filebased storage
- [x] Redis storage
- [ ] H2 support in external-map way
- [ ] Database storage
- [ ] Different events support (death, teleport, etc)
- [ ] Check for world where event occurred if possible (like overworld, nether or the_end)

### Tech specs for devs and server admins:
 - storage difference  
- - **Filebased** under the hood uses **in-memory** storage, the difference is that filebased loads it's 
state on server startup and saves state to disk if command was executed / added / removed. Select **inmemory** 
if you don't desire to keep commands persistence and **Filebased** if you want to always keep your commands queue, 
but this way is slow and I recommend you to see other backends
- - **Redis** uses [Redis](https://redis.io/) to store commands map externally but with faster access than 
database. I used `jedis 3.7.0` for development, so I recommend to use not lower that this version. Redis advantages 
are that you get external extremely fast storage for saving your data. Ensure that you you turned on redis data dumps 
to disk to keep your commands. *Select this if you way best performance*
- - **Database** storage (when implemented) uses JDBC driver to connect to database, so you shouldn't take care of 
what database to choose (MySQL, MariaDB, PostgreSQL, SQLite etc). This way is a little slower than redis, but gives you most 
robust storage of your commands data. *Select this if you want to be sure that your data always up to date and have opportunity 
to add / edit commands manually*
