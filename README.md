# Surah Shiksha (সূরা শিক্ষা)

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
[![Version](https://img.shields.io/badge/Version-1.0.8-blue.svg)](https://github.com)

An Android application designed to help users learn and memorize Quranic Surahs through interactive audio playback and verse-by-verse learning.

## 📖 About

Surah Shiksha is an educational mobile application that provides an effective way to learn and memorize Quranic Surahs. The app features audio playback of individual verses with repeat functionality, multi-language support, and an intuitive interface for focused learning.

## ✨ Features

- **📱 Interactive Audio Playback**: Play individual verses or ranges of verses
- **🔁 Smart Repeat Mode**: Set start and end verse numbers with customizable repeat counts
- **🌍 Multi-language Support**: English and Bengali (Bangla) with RTL layout support
- **🎯 Verse Selection**: Tap on any verse to play it individually
- **📜 Auto-scroll**: Automatic scrolling during verse playback
- **🔍 Search Functionality**: Quick search to find specific Surahs
- **📊 Sorting Options**: Sort by Surah number, verse count, or duration
- **🎵 Continuous Playback**: Option to play Surahs continuously
- **🎨 Modern UI**: Material Design components with custom themes

## 🛠️ Technical Details

### Built With

- **Language**: Java
- **Min SDK**: API 21 (Android 5.0 Lollipop)
- **Target SDK**: API 36
- **Build Tools**: Gradle 8.13.0
- **Architecture**: MVC Pattern

### Key Dependencies

```gradle
androidx.appcompat:appcompat:1.7.1
androidx.constraintlayout:constraintlayout:2.2.1
androidx.recyclerview:recyclerview:1.4.0
com.google.android.material:material:1.12.0
```

### Project Structure

```
app/src/main/java/com/shahriar/surahshikkha/
├── UI/                          # Activity classes
│   ├── SplashActivity.java
│   ├── DashboardActivity.java
│   └── SurahActivity.java
├── manager/                     # Business logic managers
│   └── MediaManager.java
├── Adapter/                     # RecyclerView adapters
├── Data/                        # Surah data classes
├── Dialog/                      # Custom dialog implementations
├── Utility/                     # Helper classes
│   ├── LocaleManager.java
│   ├── SharedPreferenceController.java
│   └── Constants.java
├── Interfaces/                  # Callback interfaces
├── CustomComponents/            # Custom UI components
└── LayoutManager/               # Custom layout managers
```

## 🚀 Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- JDK 11 or higher
- Android SDK with API 21+

### Installation

1. Clone the repository
```bash
git clone https://github.com/yourusername/surah-shiksha.git
```

2. Open the project in Android Studio

3. Sync Gradle files

4. Run the application on an emulator or physical device

### Building

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease
```

## 📱 Usage

1. **Select a Surah**: Browse and select from the dashboard
2. **Play Individual Verse**: Tap on any verse to play
3. **Set Repeat Range**: Use bottom controls to set start and end verse numbers
4. **Configure Repeat Count**: Set maximum repeat count from the top right menu
5. **Enable Auto-scroll**: Toggle auto-scroll for automatic verse highlighting

## 🔧 Configuration

The app uses SharedPreferences for storing user settings:
- Language preference (English/Bengali)
- Repeat count settings
- Auto-scroll preferences
- Sort order preferences

## 📄 License

This project is proprietary software. All rights reserved.

## 👨‍💻 Developer

**H. M. Shahriar**

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

## 📝 Version History

- **1.0.8** (Version Code 16) - Current version
  - Enhanced stability
  - UI improvements
  - Bug fixes

## 🙏 Acknowledgments

- Quranic audio resources
- Material Design guidelines
- Android developer community

---

**Note**: This application is designed for educational purposes to help users learn and memorize Quranic verses with proper pronunciation.