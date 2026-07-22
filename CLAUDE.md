# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

这是一个 Android LSPosed 模块（`com.chendusk.hyperthemehk`），用于绕过小米 MIUI/HyperOS 对本地自定义主题的安装限制。面向小米主题创作者，让他们可以在自己设备上直接安装和测试自己制作的 .mtz 主题。**不是破解工具，不能用于获取付费主题。**

技术栈：**Jetpack Compose** + **Miuix UI** (HyperOS 风格组件库) + **YukiHookAPI** (Xposed Hook 框架)。

## Build & Run Commands

- **Build (debug APK):** `./gradlew assembleDebug` (Unix) 或 `gradlew.bat assembleDebug` (Windows)
- **Run tests:** `./gradlew test`
- **Run a single unit test class:** `./gradlew testDebugUnitTest --tests "com.chendusk.hyperthemehk.ExampleUnitTest"`
- **Run instrumented (on-device) tests:** `./gradlew connectedAndroidTest`
- **Lint check:** `./gradlew lint`

## Build Configuration

- **应用包名：** `com.chendusk.hyperthemehk`
- **compileSdk：** 37
- **minSdk：** 35
- **targetSdk：** 36
- **Java 版本：** 11
- **关键依赖版本：**
  - AGP `9.3.0`
  - Kotlin `2.4.10`
  - Compose BOM `2026.02.01`
  - YukiHookAPI `1.3.2`
  - KavaRef `1.1.0`
  - Miuix `0.9.3`

依赖和版本统一在 `gradle/libs.versions.toml` 中管理。`api` 与 `ksp-xposed` 的版本必须保持一致。

## Architecture

- **UI 入口：** `MainActivity.kt` — 单个 `ComponentActivity`，使用 `setContent {}` 启动 Compose UI，外层包裹 `HyperThemeTheme`。
- **当前 UI 结构：** `MainScreen` 使用 `Scaffold` + `SmallTopAppBar` + `NavigationBar` 实现「首页 / 关于」两个标签页；`HomeContent` 展示设备信息卡片和安装按钮，`AboutContent` 使用 `Card`、`BasicComponent`、`ArrowPreference` 等组件展示应用信息。
- **Hook 入口：** 目前尚未创建。后续需要添加 `HookEntry` 对象，实现 `IYukiHookXposedInit` 接口，并用 `@InjectYukiHookWithXposed` 标注。在 `onHook()` 中使用 `encase { loadApp(name = "com.miui.home") { ... } }` 对小米主题相关组件进行 Hook。
- **主题包装：** `ui/theme/Theme.kt` 中的 `HyperThemeTheme` 使用默认 `ThemeController()` 包裹 `MiuixTheme`；`Color.kt` 和 `Type.kt` 保留了默认模板内容。
- **本地文档：** 离线文档已放入仓库 `docs/` 目录下，YukiHookAPI 文档位于 `docs/yukihookapi/git/zh-cn/`，Miuix 文档位于 `docs/miuix/git/zh_CN/`。

## YukiHookAPI 关键用法

**核心流程：**

```kotlin
@InjectYukiHookWithXposed
object HookEntry : IYukiHookXposedInit {
    override fun onInit() = configs { isDebug = BuildConfig.DEBUG }
    override fun onHook() = encase {
        loadApp(name = "目标包名") {
            // 在这里写 Hook 逻辑
        }
    }
}
```

**Hook 方法的基本模式（1.3.x + KavaRef）：**

```kotlin
"完整类名".toClass()
    .resolve()
    .firstMethod { name = "方法名" }
    .hook {
        before { /* 在原方法前执行 */ }
        after  { /* 在原方法后执行 */ }
    }
```

**重要注意事项：**

- `encase` 中不能直接 Hook，必须用 `loadApp` / `loadZygote` / `loadSystem` 包裹。
- 子 Hooker 建议用 `object` 单例，继承 `YukiBaseHooker`。
- `api` 与 `ksp-xposed` 版本必须一致。
- 1.3.0+ 反射 API 迁移到了 KavaRef，需要引入 `kavaref-core` 和 `kavaref-extension`。
- 需要在 `AndroidManifest.xml` 中声明 Xposed 模块相关的 `meta-data`（xposedmodule、xposeddescription、xposedminversion 等）。
- Android Gradle Plugin 8+ 需要在 `buildFeatures` 中启用 `buildConfig = true`。

**文档地址：** https://highcapable.github.io/YukiHookAPI/zh-cn/guide/home.html  
**本地文档：** `docs/yukihookapi/git/zh-cn/`

## Miuix UI 关键用法

Miuix 是一个提供小米 HyperOS 设计风格的 Compose Multiplatform 组件库。

**Maven 坐标 (Android)：**

```kotlin
implementation("top.yukonga.miuix.kmp:miuix-ui-android:<version>")
implementation("top.yukonga.miuix.kmp:miuix-preference-android:<version>")
```

**主题包装：**

```kotlin
val controller = remember { ThemeController() }
MiuixTheme(controller = controller) {
    Scaffold(
        topBar = { SmallTopAppBar(title = "标题") },
        bottomBar = { NavigationBar { ... } }
    ) { paddingValues ->
        // 内容
    }
}
```

**常用组件：**

- 文本/按钮：`Text`、`Button`、`IconButton`
- 卡片/列表项：`Card`、`BasicComponent`、`ArrowPreference`
- 顶部/底部导航：`SmallTopAppBar`、`TopAppBar`、`NavigationBar`
- 弹出层：`OverlayDialog`、`WindowDialog`、`OverlayBottomSheet`

**重要：** `Scaffold` 是弹出窗口容器，`OverlayDialog` 等弹出组件必须被 `Scaffold` 包裹。

**文档地址：** https://compose-miuix-ui.github.io/miuix/zh_CN/  
**本地文档：** `docs/miuix/git/zh_CN/`

## Key Conventions

- Composable functions use `Modifier` as the first optional parameter (after required params) and pass it through to the root layout node.
- Preview functions are annotated with `@Preview(showBackground = true)` and wrapped in `MiuixTheme {}`.

## 开发规范

- **中文优先** — 用户是中文使用者，交流和注释以中文为主。
- **先想再写** — 动手前明确假设、列出多种理解、提出更简单的方案。遇到不清楚的地方停下来提问。不假设、不隐藏困惑、把利弊权衡摆到台面上。
- **简洁优先** — 用最少的代码解决问题。不做没被要求的功能、不为只用一次的代码做抽象、不添加没被要求的"灵活性"或"可配置性"、不为不可能发生的场景做错误处理。200 行能用 50 行解决就重写。自问标准：一个资深工程师看了会觉得这太复杂了吗？
- **精准修改** — 只动必须动的地方。不"顺手优化"旁边的代码和格式、不重构没出问题的部分、匹配现有代码风格。改动产生的孤立引用要清理，但不删除改动之前就已存在的废弃代码。检验标准：每一行改动都能追溯到用户的请求。
- **目标驱动执行** — 把任务转化为可验证的目标（如"加验证"→"写测试然后让它通过"），多步骤任务先列计划，每步附验证方式。
- **Git 规范** — 使用 Conventional Commits 格式（`feat:`、`fix:`、`chore:`、`docs:` 等前缀）。除非用户明确说明 `push`/推送，否则不要主动推送。
