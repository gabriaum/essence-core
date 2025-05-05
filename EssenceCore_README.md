
# EssenceCore Plugin Documentation

## Overview
EssenceCore is a comprehensive plugin for managing various commands and features within your Minecraft server. This plugin provides a range of useful utilities, including teleportation (TPA), god mode, game mode management, and inventory management. The following guide explains how to set up and use the different functionalities of the EssenceCore plugin.

## Features
- **Game Mode Commands**: Allows players to change their game mode.
- **God Mode**: Enables god mode for players, making them invulnerable to damage.
- **Teleportation (TPA)**: Teleport to other players or accept teleport requests.
- **Inventory Management**: Open another player's inventory, manage ender chests, and clean items.
- **Trash Inventory**: Allows players to open a trash inventory to dispose of unwanted items.

## Commands

### `/gamemode <mode> [player]`
- **Usage**: Changes the game mode of the player.
- **Modes**: survival, creative, adventure, spectator
- **Permissions**: `essencecore.command.gamemode`
- **Messages**:
  - Invalid mode: "Invalid game mode. Valid modes are: survival, creative, adventure, spectator."
  - Already in mode: "You are already in that game mode."
  - Target player already in mode: "Target player is already in that game mode."
  - Mode changed successfully: "Game mode of {player} changed to {mode}."

### `/god [player]`
- **Usage**: Toggles god mode for the player.
- **Permissions**: `essencecore.command.god`
- **Messages**:
  - God mode enabled: "God mode enabled."
  - God mode disabled: "God mode disabled."
  - Target god mode enabled: "God mode enabled for {player}."
  - Target god mode disabled: "God mode disabled for {player}."

### `/openinventory <player>`
- **Usage**: Opens the specified player's inventory.
- **Permissions**: `essencecore.command.openinventory`
- **Messages**:
  - Inventory opened: "Opened {player}'s inventory."

### `/enderchest <player>`
- **Usage**: Opens the specified player's ender chest.
- **Permissions**: `essencecore.command.enderchest`
- **Messages**:
  - Ender chest opened: "Opened {player}'s ender chest."
  - Ender chest not found: "Ender chest not found."

### `/fix`
- **Usage**: Repairs the item in your hand.
- **Permissions**: `essencecore.command.fix`
- **Messages**:
  - Item repaired: "Item repaired."
  - No item in hand: "You must be holding an item."
  - Item not repairable: "This item cannot be repaired."
  - Already repaired: "This item is already repaired."

### `/tpa <player>`
- **Usage**: Sends a teleport request to another player.
- **Permissions**: `essencecore.command.tpa`
- **Messages**:
  - Usage reminder: "Usage: /{label} <player>"
  - Player can't teleport to self: "You cannot teleport to yourself."
  - Request already sent: "You have already sent a teleport request."
  - Request sent: "Teleport request sent to {player}."
  - Request received: "You have received a teleport request from {player}. You have 30 seconds to accept or decline."
  - No request found: "No teleport request found."

### `/tpaccept`
- **Usage**: Accepts a teleport request.
- **Permissions**: `essencecore.command.tpaccept`
- **Messages**:
  - Teleport request accepted: "Teleport request accepted."
  - Target accepted request: "You have accepted the teleport request from {player}."

### `/tpdeny`
- **Usage**: Denies a teleport request.
- **Permissions**: `essencecore.command.tpadeny`
- **Messages**:
  - Teleport request declined: "Teleport request declined."

## Configuration (config.yml)
The configuration file allows you to customize messages, inventory sizes, and other settings.

```yaml
messages:
  player-not-found: "Player not found."

inventories:
  trash:
    title: "&cTrash"
    size: 6

commands:
  gamemode:
    usage: "&cUsage: /{label} <mode> [player]"
    invalid-mode: "&cInvalid game mode. Valid modes are: survival, creative, adventure, spectator."
    already-in-mode: "&cYou are already in that game mode."
    target-already-in-mode: "&cTarget player is already in that game mode."
    target-changed: "&aGame mode of &b{player}&a changed to &b{mode}&a."
    changed: "&aYour game mode has been changed to &b{mode}&a."
  god:
    usage: "&cUsage: /{label} [player]"
    enabled: "&aGod mode enabled."
    disabled: "&cGod mode disabled."
    target-enable: "&aGod mode enabled for &b{player}&a."
    target-disable: "&cGod mode disabled for &b{player}&a."
  open-inventory:
    usage: "&cUsage: /{label} <player>"
    target-opened: "&aOpened &b{player}&a's inventory."
  enderchest:
    target-opened: "&aOpened &b{player}&a's ender chest."
    not-found: "&cEnder chest not found."
  fix:
    repaired: "&aItem repaired."
    no-item-in-hand: "&cYou must be holding an item."
    not-repairable: "&cThis item cannot be repaired."
    already-repaired: "&cThis item is already repaired."
  tpa:
    usage: "&cUsage: /{label} <player>"
    self: "&cYou cannot teleport to yourself."
    already-sent: "&cYou have already sent a teleport request."
    request-sent: "&aTeleport request sent to &b{player}&a."
    request-received: "&aYou have received a teleport request from &b{player}&a. You have 30 seconds to accept, Use &b/tpaccept &aor &b/tpdeny &ato respond."
    request-accepted: "&aTeleport request accepted."
    request-accepted-target: "&aYou have accepted the teleport request from &b{player}&a."
    request-declined: "&cTeleport request declined."
    teleporting: "&aTeleporting in &b{time} seconds&a."
    teleport-cancelled: "&cTeleportation cancelled."
    teleported: "&aTeleported to &b{player}&a."
    no-request: "&cNo teleport request found."
```

## Installation
1. Download the EssenceCore plugin JAR file.
2. Place the JAR file in your server's `plugins/` folder.
3. Restart your server to enable the plugin.

## Dependencies
- **None**: EssenceCore works independently without external dependencies.
