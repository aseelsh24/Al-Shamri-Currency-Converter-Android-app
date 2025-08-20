# Al-Shamri Currency Converter

Al-Shamri Currency Converter is a modern, offline-first Android application designed for seamless currency conversion. Built with the latest Android development standards, it provides a clean, intuitive user experience powered by Material Design 3.

## ✨ Features

- **Offline Conversion**: Convert amounts between five major currencies without needing an internet connection.
- **Supported Currencies**:
  - US Dollar (USD)
  - Saudi Riyal (SAR)
  - Old Yemeni Rial (YER-Old)
  - New Yemeni Rial (YER-New)
  - Gold Pound (Gold)
- **Manual Rate Management**: A dedicated settings screen allows the user to manually update and persist exchange rates.
- **Conversion History**: Automatically saves the last 5 conversions for quick reference, accessible via a bottom sheet.
- **Modern UI/UX**: A beautiful and responsive interface built with Material Design 3, supporting dynamic colors (Material You) on Android 12+.
- **Light/Dark Mode**: A persistent theme toggle lets users switch between light, dark, and system default modes.

## 🛠️ Tech Stack & Architecture

- **Language**: 100% [Kotlin](https://kotlinlang.org/)
- **Architecture**: Model-View-ViewModel (MVVM)
- **UI**: XML Layouts with ViewBinding
- **Component Toolkit**: [Material Design 3](https://m3.material.io/)
- **Asynchronous Programming**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html)
- **Local Storage**: [Room Database](https://developer.android.com/training/data-storage/room) for exchange rates and [SharedPreferences] for history/theme.
- **Dependency Injection**: Manual DI via a custom `AppModule`.
- **Navigation**: [Android Jetpack Navigation Component](https://developer.android.com/guide/navigation).

## 🚀 Getting Started

### Prerequisites

- Android Studio Iguana | 2023.2.1 or later
- JDK 17

### Installation

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/example/alshamri-currency-converter.git
    ```
2.  **Open in Android Studio:**
    - Open Android Studio.
    - Click on `File` > `Open`.
    - Navigate to the cloned repository directory and select it.
3.  **Build the project:**
    - Let Android Studio sync the Gradle files.
    - Click `Build` > `Make Project` or run it directly on an emulator or a physical device.

## 📦 Building a Signed Release APK

To build a signed release APK, you must first generate a keystore file and then provide its credentials to the build system.

### 1. Generate a Keystore

Place the generated `keystore.jks` file inside the `app/` directory of the project. Use the following command to generate it:

```bash
keytool -genkey -v -keystore app/keystore.jks -keyalg RSA -keysize 2048 -validity 9125 -alias Al-Shamri
```
You will be prompted to enter a store password and a key password. Remember these for the next step.

### 2. Configure Build Credentials

The project is set up to read keystore credentials from environment variables. You can either set these variables in your local environment or use them directly in the CI/CD pipeline.

## 🤖 Continuous Integration with GitHub Actions

This project includes a GitHub Actions workflow (`.github/workflows/build.yml`) to automatically build and sign a release APK on every push to the `main` branch.

### How to Set Up GitHub Secrets

To enable the workflow, you must configure the following secrets in your GitHub repository settings (`Settings` > `Secrets and variables` > `Actions`):

1.  **`KEYSTORE_BASE64`**: The base64-encoded version of your `keystore.jks` file.
    -   To generate this, run the following command in your terminal (on Linux/macOS):
        ```bash
        base64 app/keystore.jks
        ```
    -   Copy the entire output and paste it as the value for this secret.

2.  **`STORE_PASSWORD`**: The store password you set when generating the keystore.

3.  **`KEY_ALIAS`**: The alias for your key (e.g., `Al-Shamri`).

4.  **`KEY_PASSWORD`**: The password for the key alias.

Once these secrets are set, the workflow will run automatically and upload the signed APK as a build artifact, which you can download directly from the Actions tab in GitHub.
