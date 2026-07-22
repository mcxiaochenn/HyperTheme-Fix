package com.chendusk.hyperthemehk.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.theme.ThemeController

@Composable
fun HyperThemeTheme(content: @Composable () -> Unit) {
    val controller = remember { ThemeController() }
    MiuixTheme(controller = controller, content = content)
}