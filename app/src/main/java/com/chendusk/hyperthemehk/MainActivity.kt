package com.chendusk.hyperthemehk

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chendusk.hyperthemehk.ui.theme.HyperThemeTheme
import top.yukonga.miuix.kmp.basic.BasicComponent
import top.yukonga.miuix.kmp.basic.Button
import top.yukonga.miuix.kmp.basic.ButtonDefaults
import top.yukonga.miuix.kmp.basic.Card
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationBarItem
import top.yukonga.miuix.kmp.basic.Scaffold
import top.yukonga.miuix.kmp.basic.SmallTopAppBar
import top.yukonga.miuix.kmp.basic.Text
import top.yukonga.miuix.kmp.icon.MiuixIcons
import top.yukonga.miuix.kmp.icon.basic.Check
import top.yukonga.miuix.kmp.icon.basic.Search
import top.yukonga.miuix.kmp.preference.ArrowPreference
import top.yukonga.miuix.kmp.theme.MiuixTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HyperThemeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("首页", "关于")

    Scaffold(
        topBar = {
            SmallTopAppBar(title = "HyperTheme HK")
        },
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = if (index == 0) MiuixIcons.Basic.Search else MiuixIcons.Basic.Check,
                        label = label
                    )
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        when (selectedTab) {
            0 -> HomeContent(paddingValues)
            1 -> AboutContent(paddingValues)
        }
    }
}

@Composable
fun HomeContent(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 上部：设备信息卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            insideMargin = PaddingValues(0.dp)
        ) {
            BasicComponent(
                title = "设备",
                summary = Build.DEVICE ?: "未知"
            )
            BasicComponent(
                title = "内核版本",
                summary = System.getProperty("os.version") ?: "未知"
            )
            BasicComponent(
                title = "安卓版本",
                summary = "Android ${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})"
            )
            BasicComponent(
                title = "软件版本",
                summary = "1.0.0"
            )
            BasicComponent(
                title = "主题商店版本",
                summary = "未检测"
            )
        }

        // 下部：安装按钮
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
            },colors = ButtonDefaults.buttonColorsPrimary()) {
                Text("安装主题")
            }
        }
    }
}

@Composable
fun AboutContent(paddingValues: androidx.compose.foundation.layout.PaddingValues) {
    val context = androidx.compose.ui.platform.LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 26.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 软件信息卡片
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            insideMargin = PaddingValues(0.dp)
        ) {
            BasicComponent(
                title = "软件名称",
                summary = "HyperTheme HK"
            )
            BasicComponent(
                title = "版本",
                summary = "1.0.0"
            )
            BasicComponent(
                title = "包名",
                summary = "com.chendusk.hyperthemehk"
            )
        }

        // 开发者卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            insideMargin = PaddingValues(0.dp)
        ) {
            DeveloperInfoRow(context)
        }

        // 模块信息卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            insideMargin = PaddingValues(0.dp)
        ) {
            BasicComponent(
                title = "模块说明",
                summary = ""
            )
            BasicComponent(
                title = "支持系统",
                summary = "MIUI / HyperOS"
            )
        }

        // 引用卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            insideMargin = PaddingValues(0.dp)
        ) {
            BasicComponent(
                title = "引用",
                summary = ""
            )
        }
    }
}

@Composable
fun DeveloperInfoRow(context: android.content.Context) {
    ArrowPreference(
        title = "辰渊尘Dusk",
        summary = "一个09年高中生个人开发者",
        startAction = {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MiuixTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "D",
                    color = MiuixTheme.colorScheme.onPrimary,
                    style = MiuixTheme.textStyles.title2
                )
            }
        },
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/mcxiaochenn"))
            context.startActivity(intent)
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    HyperThemeTheme {
        MainScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AboutContentPreview() {
    HyperThemeTheme {
        AboutContent(paddingValues = PaddingValues(0.dp))
    }
}
