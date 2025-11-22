# Daricx

Daricx is a multi-module Android app that explores the crypto market domain (coins, NFTs, categories, exchanges and watchlist) using a modern Android tech stack and clean, modular architecture.  
It is designed both as a real-world crypto market app and as a playground for modern Android patterns (Jetpack Compose, Hilt, Coroutines/Flow, DataStore, Room, multi-module architecture).

🚧 > **Status:** Work in progress 

---

## 🎥 Project Screenshots And Video
<p align="center">
  <img src="doc/photo_2025-11-17_21-37-35.jpg?raw=true" alt="Screen 1" width="300"/>
  <img src="doc/photo_2025-11-22_16-53-27.jpg?raw=true" alt="Screen 1" width="300"/>
</p>
----


## 📚 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Architecture](#architecture)
  - [Module Structure](#module-structure)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
  - [Clone & Run](#clone--run)
- [Contributing](#contributing)
- [Contact](#contact)

---

## 🔍 Overview

Daricx aims to be a solid base for building crypto/fintech products on Android.  
The project focuses on:

- Clear **separation of concerns** via multi-module architecture.
- Fully **Compose-based UI** with reusable design-system components.
- **Reactive data flow** powered by Kotlin Coroutines and Flow.
- Easy future integration with **real-world crypto APIs** (e.g., CoinGecko) and later **AI/ML modules** for predictions.



---

## ✨ Features

> These are the features already present or actively being developed in this repository.

### 📊 Markets

- **Coins list** with pricing, market cap and 24h performance.
- **Trending section** for quick overview of important coins, categories and NFTs 
- **Categories** list with market cap, 24h change, volume.
- **NFTs list** with floor prices and volume.
- Screen is implemented with Jetpack Compose and structured into dedicated `data`, `ui`, `model` and `navigation` packages.

### ⭐ Watchlist

- Ability to mark specific coins as **favorites** (watchlist).
- Watchlist state persisted locally (via Database modules), allowing the app to remember user selections.


### ⚙️ Settings

- **Settings screen & drawer menu**, implemented in `feature:settings`.
- Designed to host options like:
  - App theme (light/dark/system).
  - Language. (Soon)
  - Default currency/timezone. (Soon)


### 🎨 Design System

- Custom **design system** in `core:designsystem`:
  - Reusable top bars, tab pagers, chips, cards, list items, etc.
  - Centralized theming (colors, typography, shapes).
  - Reusable components across all features.

### 🧰 Error Handling & Logging

- `Application` class annotated with `@HiltAndroidApp`.
- Global **uncaught exception handler** configured.
- **Timber** is used for structured logging.
- Dedicated **NetworkMonitor** and **TimeZoneMonitor** injected into `MainActivity` for reactive UI behavior based on network/time changes.

---

## 🏗 Architecture

Daricx follows a **modular Clean Architecture** style with a clear separation between:

- **Core modules** – shared/common code (design system, data, network, datastore, database, models, UI helpers).
- **Feature modules** – screen-level functionality (Markets, Settings, etc.).
- **App module** – entry point; wires everything together (navigation, DI setup, application lifecycle).

The internal layers generally follow:

- **UI layer (Compose screens + ViewModels)** – stateful presentation logic.
- **Domain / Model layer** – domain models, mappers, business rules.
- **Data layer** – repositories, local (Room/DataStore) + remote (Retrofit) sources.

### 🧩 Module Structure

From `settings.gradle.kts` the modules are:

- `:app` – Main Android application module.
- Core modules:
  - `:core:designsystem`
  - `:core:database`
  - `:core:datastore`
  - `:core:network`
  - `:core:data`
  - `:core:common`
  - `:core:model`
  - `:core:ui`
- Feature modules:
  - `:feature:markets`
  - `:feature:settings`

This structure allows:

- Faster builds due to isolated modules.
- Reuse of **core** logic across multiple features or even apps.
- Easier testing and maintenance.

---

## 🛠 Tech Stack

**Language & UI**

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Material 3 components and custom design system

**Architecture & Patterns**

- Multi-module **Clean Architecture**
- MVVM + unidirectional data flow (UDF) in ViewModels
- Repository pattern

**Async & Reactivity**

- Kotlin Coroutines
- Kotlin Flow & StateFlow & SharedFlow

**Dependency Injection**

- [Dagger Hilt](https://dagger.dev/hilt/)

**Local Storage**

- Room (in `core:database`)
- DataStore Preferences / Proto (in `core:datastore`)

**Networking**

- Retrofit & OkHttp (in `core:network`) – prepared for real crypto APIs

**Utilities**

- Timber for logging
- Custom NetworkMonitor & TimeZoneMonitor

---

## 🚀 Getting Started

### Clone & Run

1. **Clone the repository**

   ```bash
   git clone https://github.com/sepehrpg/daricx.git
   cd daricx



## 📥 Contributing

Contributions, ideas, and bug reports are welcome.

### How to Contribute

1. **Fork** the repository.
2. **Create a feature branch**:

   ```bash
   git checkout -b feature/my-new-feature
   ```

3. **Implement your changes**
   - Follow the existing architecture and module structure.
   - Keep UI state and logic in ViewModels where possible.
   - Reuse shared components (`core:designsystem`, `core:ui`, etc.) instead of duplicating code.

4. **Run checks**:

   ```bash
   ./gradlew clean build
   ```

5. **Commit & push**:

   ```bash
   git commit -m "Add my new feature"
   git push origin feature/my-new-feature
   ```

6. **Open a Pull Request**
   - Describe the motivation and the changes.
   - Reference related issues (e.g. `Fixes #12`).
   - Attach screenshots or screen recordings for UI changes when relevant.

### Guidelines

- **Code Style**
  - Use idiomatic Kotlin.
  - Keep functions and classes small and focused.
  - Prefer immutable state and unidirectional data flow.

- **Architecture**
  - Shared logic belongs in `core` modules.
  - Feature-specific code stays in the corresponding `feature:*` module.
  - Avoid adding heavy dependencies to features when they can live in `core`.

- **UI**
  - Prefer composables from the design system when possible.
  - Keep composables as stateless as possible and drive them via ViewModel state.

---


## 📬 Contact

If you have questions, suggestions, or would like to collaborate:

- Website: [sepehrpg.ir](https://sepehrpg.ir/)
- GitHub: [@sepehrpg](https://github.com/sepehrpg)

Issues and pull requests on the repository are also welcome as a way to start a discussion.

