# HyperTheme HK

一个用于绕过小米 MIUI/HyperOS 对本地自定义主题安装限制的 Android LSPosed 模块。

An Android LSPosed module to bypass Xiaomi MIUI/HyperOS restrictions on installing local custom themes.

> **⚠️ 仅供主题创作者在自己设备上测试使用，不能用于获取付费主题。**  
> **⚠️ For theme creators to test on their own devices only. Not for obtaining paid themes.**

## 功能 / Features

- 绕过系统主题商店对本地 `.mtz` 主题的安装限制
- 简洁的 HyperOS 风格界面（基于 Miuix UI）
- 基于 YukiHookAPI 的 Xposed Hook 框架

- Bypass system Theme Store restrictions on local `.mtz` theme installation
- Clean HyperOS-style UI based on Miuix UI
- Xposed Hook framework powered by YukiHookAPI

## 技术栈 / Tech Stack

- **UI:** Jetpack Compose + [Miuix UI](https://github.com/YuKongA/Miuix)
- **Hook Framework:** [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI)
- **Build System:** Gradle with Version Catalog

## 环境要求 / Requirements

- Android 15+（minSdk 35）
- 已安装 LSPosed 或其他兼容 Xposed 框架
- Android Studio / IntelliJ IDEA

- Android 15+ (minSdk 35)
- LSPosed or another compatible Xposed framework installed
- Android Studio / IntelliJ IDEA

## 构建 / Build

```bash
# Debug APK
./gradlew assembleDebug

# 或 Windows
gradlew.bat assembleDebug
```

## 测试 / Test

```bash
# 单元测试
./gradlew test

# 单个测试类
./gradlew testDebugUnitTest --tests "com.chendusk.hyperthemehk.ExampleUnitTest"

# 仪器测试（需要连接设备）
./gradlew connectedAndroidTest

# Lint 检查
./gradlew lint
```

## 使用 / Usage

1. 在 LSPosed 中启用本模块并勾选「系统框架」及「主题商店」相关作用域。
2. 打开 App，点击「安装主题」选择本地 `.mtz` 文件。
3. 应用后重启目标应用或系统即可生效。

1. Enable this module in LSPosed and select the relevant scopes (System Framework and Theme Store).
2. Open the app and tap "Install Theme" to choose a local `.mtz` file.
3. Apply and restart the target app or system if necessary.

## 免责声明 / Disclaimer

本项目仅供学习和个人测试使用，作者不对因使用本项目造成的任何损失负责。请勿用于商业用途或破解付费内容。

This project is for educational and personal testing purposes only. The author is not responsible for any damages caused by using this project. Do not use it for commercial purposes or cracking paid content.

## 许可证 / License

[MIT](LICENSE)

## 开发者 / Developer

- **辰渊尘Dusk** · [GitHub](https://github.com/mcxiaochenn)
