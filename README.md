[Support development on Buy Me a Coffee](https://buymeacoffee.com/affanfareev).

# Deadman Breach Timer

Deadman Breach Timer is a RuneLite external plugin that displays the configured
Deadman breach schedule, a countdown overlay, and an optional five-minute
notification during the supported seasonal window.

## Features

- Detects Deadman worlds through RuneLite's local client API.
- Shows the next breach countdown in an overlay.
- Converts the UTC schedule to the computer's local timezone.
- Synchronizes the countdown from RuneScape's in-game breach message.
- Supports a local `!breach` command and an optional five-minute notification.

## Privacy and network usage

The plugin makes no HTTP requests and sends no player, account, or gameplay data
to an external service. It reads only RuneLite client state and game messages
needed for the breach countdown.

## Development

Requirements: Java 11 and Git.

Linux/macOS:

```bash
./gradlew clean test assemble --no-daemon --console=plain
./gradlew run --no-daemon --console=plain
```

Windows:

```bat
gradlew.bat clean test assemble --no-daemon --console=plain
gradlew.bat run --no-daemon --console=plain
```

## Plugin Hub metadata

- Repository: https://github.com/ItMeansBigMountain/breach-check-osrs
- Plugin Hub marker: `plugins/deadman-breach-timer`
- Root `icon.png`: 48x48 pixels

## License

BSD 2-Clause License. See [LICENSE](LICENSE).