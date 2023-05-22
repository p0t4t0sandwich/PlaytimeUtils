# PlaytimeUtils

Plugin for various playtime-related tools

## Features

### Playtime tracking

Tracks the playtime of players and stores it in a database.

### Streaks

Tracks the streak of players and stores it in a database. Players need to log on every day to keep their streak.
There are events for streak increment and streak reset.

### Ranks

Players can rank up based on their playtime. Just add a rank to the config and set the required playtime.

## Commands

| Command | Permission | Description |
| --- | --- | --- |
| `/playtime` | `playtimeutils.playtime` | Shows the playtime of the player |
| `/streak` | `playtimeutils.streak` | Shows the streak of the player |

## TODO

- [ ] Add a command to check playtime
  - [ ] Fabric support
  - [ ] Forge support
- [ ] Add a command to check streak
  - [ ] Fabric support
  - [ ] Forge support
- [ ] Add a command to check playtime of other players
- [x] Events for streak increase/reset
- [ ] API for all that stuff
- [ ] Check the progress till the next rank
- [ ] add sqlite support
- [ ] add h2 support
- [ ] add filestorage support
- [ ] make sure player schema is compliant with account link system
- [ ] add config section for commands to run on events triggered by the plugin
- [ ] Events for rank up
