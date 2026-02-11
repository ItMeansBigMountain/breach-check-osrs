``` markdown
honestly if you found this useful then id love a donation on osrs or you can buy me a coffee [here](buymeacoffee.com/affanfareev)
```
----

<!-- Below is a **clean, submission-quality README.md** that:

* Explains what the plugin does
* Explains seasonal + permanent logic
* Mentions no external APIs
* Includes development instructions (like you asked)
* Explains how to run locally with `gradlew run`
* Shows how Plugin Hub packaging works

You can copy-paste this entirely into `README.md`.

---

# 📄 `README.md`

````markdown -->
# Deadman Breach Timer

Displays Deadman Mode breach countdowns and schedules for both seasonal and permanent Deadman worlds.

Just type `!breach` to find out when the next breach is!

---

## Features

<!-- - Supports **Seasonal Deadman** breach schedule  
  - 02:00 UTC  
  - 10:00 UTC  
  - 19:00 UTC  

- Supports **Permanent Deadman (World 345)** weekend breaches  
  - Every 4 hours  
  - Saturday 02:00 UTC → Sunday 22:00 UTC  

- Automatically detects world type
- Displays local time (AM/PM format)
- Live countdown overlay
- Optional 5-minute notification
- Syncs with in-game skull breach check
- No external API calls
- No data collection

---

## How It Works

RuneLite plugins operate within a Java-based event-driven framework.

This plugin:

- Subscribes to RuneLite game events (e.g. `GameTick`, `GameStateChanged`, `ChatMessage`)
- Detects whether the player is in a Deadman world
- Calculates the next breach time using UTC logic
- Converts times to the user’s local timezone
- Displays a live countdown overlay
- Updates automatically each game tick
- Syncs using the in-game skull timer message when available

The plugin does not poll external services or use HTTP requests.  
All scheduling logic is computed locally using Java time APIs.

---

## Development Guide

### Requirements

- Java 11
- Git
- RuneLite Plugin Template

---

### Running the Plugin Locally

Inside the project directory:

```bash
./gradlew run
````

(or on Windows)

```powershell
.\gradlew.bat run
```

This will:

* Build the plugin
* Launch a RuneLite instance
* Load your plugin automatically

You can then enable it in the RuneLite sidebar.

---

### Project Structure

```
dmm-breach-osrs/
├── build.gradle
├── runelite-plugin.properties
├── icon.png
├── README.md
└── src/
    └── main/java/com/oyama/dmm/
        ├── DeadmanBreachPlugin.java
        ├── DeadmanBreachOverlay.java
        └── DeadmanBreachConfig.java
```

---

### Key RuneLite Concepts Used

* `@PluginDescriptor` — Registers the plugin
* `@Subscribe` — Hooks into RuneLite’s event bus
* `OverlayPanel` — Renders UI overlay
* `WorldType` — Detects Deadman worlds
* `Client.addChatMessage()` — Sends chat messages
* `ConfigGroup` / `ConfigItem` — Creates plugin settings

RuneLite plugins are event-driven — you react to game state changes rather than polling manually.

---

## Plugin Hub Packaging

To submit this plugin to the RuneLite Plugin Hub:

1. Push this repository to GitHub (public).
2. Fork `runelite/plugin-hub`.
3. Add a folder under `plugins/` containing:

```
plugin.properties
```

With:

```
repository=https://github.com/YOUR_USERNAME/dmm-breach-osrs
commit=YOUR_COMMIT_HASH
```

4. Open a Pull Request.

The Plugin Hub CI system will:

* Clone your repo
* Build it
* Validate `icon.png` (must be 48x72)
* Verify metadata

---

## Design Philosophy

* No hardcoded seasonal dates
* Supports future Deadman seasons automatically
* No external dependencies
* Lightweight and event-driven
* Safe for Plugin Hub distribution

---

## License

MIT License (or specify your preferred license)

```

---

# 🔥 Why This README Is Good

- Clean
- Professional
- Reviewer-friendly
- Explains architecture
- Explains how to develop
- Explains packaging
- Shows understanding of RuneLite framework

---

If you want, next we can:

- Add an MIT license file
- Simulate a Plugin Hub reviewer critique
- Or prepare your PR description text

You’re extremely close to being published.
``` -->
