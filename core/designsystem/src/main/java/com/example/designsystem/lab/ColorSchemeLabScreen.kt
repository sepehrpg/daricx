package com.example.designsystem.lab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.elevatedButtonColors
import androidx.compose.material3.ButtonDefaults.filledTonalButtonColors
import androidx.compose.material3.ButtonDefaults.outlinedButtonColors
import androidx.compose.material3.ButtonDefaults.textButtonColors
import androidx.compose.material3.TopAppBarDefaults.centerAlignedTopAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.AppTheme // <- از تم خودت استفاده می‌کنیم
import com.example.designsystem.theme.LightColorScheme
import com.example.designsystem.theme.ThemePreviews
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorSchemeLabScreen(
    modifier: Modifier = Modifier
) {
    val cs = MaterialTheme.colorScheme
    val shapes = MaterialTheme.shapes

    val snackbarHostState = remember { SnackbarHostState() }
    var showDialog by remember { mutableStateOf(false) }
    var showScrim by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("ColorScheme Lab", maxLines = 1, overflow = TextOverflow.Ellipsis) },
                colors = centerAlignedTopAppBarColors(
                    containerColor = cs.surface,
                    titleContentColor = cs.onSurface,
                    navigationIconContentColor = cs.onSurface,
                    actionIconContentColor = cs.onSurfaceVariant
                ),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar(containerColor = cs.surface) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Favorite, null) },
                    label = { Text("Primary") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = cs.onPrimary,
                        selectedTextColor = cs.onPrimary,
                        indicatorColor = cs.primary,
                        unselectedIconColor = cs.onSurfaceVariant,
                        unselectedTextColor = cs.onSurfaceVariant
                    )
                )
                NavigationBarItem(
                    selected = false, onClick = {},
                    icon = { Icon(Icons.Default.ArrowUpward, null) },
                    label = { Text("Success") }
                )
                NavigationBarItem(
                    selected = false, onClick = {},
                    icon = { Icon(Icons.Default.ArrowDownward, null) },
                    label = { Text("Error") }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showScrim = !showScrim },
                containerColor = cs.primary,
                contentColor = cs.onPrimary
            ) {
                Text(if (showScrim) "Hide" else "Scrim")
            }
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    containerColor = cs.inverseSurface,
                    contentColor = cs.inverseOnSurface,
                    action = {
                        TextButton(onClick = { /* handle action */ }) {
                            Text(
                                "UNDO",
                                color = cs.inversePrimary
                            )
                        }
                    },
                    dismissAction = {
                        IconButton(onClick = { /* dismiss */ }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Dismiss",
                                tint = cs.inverseOnSurface
                            )
                        }
                    }
                ) {
                    Text("Message")
                }
            }
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        Box(Modifier.fillMaxSize().padding(innerPadding )) {
            LazyColumn(
                //contentPadding = innerPadding + PaddingValues(16.dp),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                // 1) Overview (swatches of all roles)
                item {
                    RoleSectionTitle("Overview: Roles as Swatches")
                    RoleGrid(
                        roles = buildRoleSwatches(cs),
                        shape = shapes.medium
                    )
                }

                // 2) Buttons & Text/Icons
                item { RoleSectionTitle("Buttons & Text / Icons") }
                item { ButtonsRow() }
                item { TextAndIconsDemo() }

                // 3) Surfaces (Card/Elevation/Tint)
                item { RoleSectionTitle("Surfaces, Elevation & surfaceTint") }
                items(listOf(0.dp, 1.dp, 3.dp, 6.dp, 12.dp)) { elev ->
                    SurfaceCardExample(elev)
                }

                // 4) SurfaceVariant & Outline
                item { RoleSectionTitle("surfaceVariant & outline / outlineVariant") }
                item { VariantAndOutlineDemo() }

                // 5) Status (Success/Error via tertiary/error)
                item { RoleSectionTitle("Status Chips (tertiary / error)") }
                item { StatusChipsRow() }

                // 6) SurfaceContainers ladder
                item { RoleSectionTitle("Surface Container Levels") }
                item { SurfaceContainersDemo() }

                // 7) Snackbar / Inverse colors
                item {
                    ElevatedButton(onClick = {
                        /*LaunchedEffect(Unit) {
                            // just to calm lint; real call inside
                        }*/
                    }) {
                        // no-op
                        Text(" ")
                    }
                    // Trigger snackbar
                    FilledTonalButton(onClick = {
                        // Use LaunchedEffect-scoped call
                        // Provide scope here:
                    }) { /* placeholder to keep spacing */ }
                }
                item {
                    Button(onClick = {
                        // fire a snackbar
                        // Use remember outside:
                    }) { Text("Show Snackbar (inverseSurface)") }
                }

                // 8) Scrim toggle info
                item {
                    RoleSectionTitle("Scrim")
                    Text(
                        "Tap the FAB to toggle scrim overlay. Scrim uses colorScheme.scrim with alpha.",
                        color = cs.onSurfaceVariant
                    )
                }

                // 9) Dialog sample
                item {
                    RoleSectionTitle("Dialog")
                    OutlinedButton(onClick = { showDialog = true }) {
                        Text("Show Dialog")
                    }
                }
            }

            // Scrim overlay
            AnimatedVisibility(showScrim) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
                )
            }
        }

        // Dialog
        if (showDialog) {
            SampleDialog(onDismiss = { showDialog = false })
        }

        // a small LaunchedEffect to show snackbar via top-level Scaffold Host
        LaunchedEffect(Unit) {
            snackbarHostState.showMessage = { msg ->
                // extension below
            }
        }
    }
}

/* ---------------- helpers & components ---------------- */

private fun buildRoleSwatches(cs: ColorScheme): List<RoleSwatch> = listOf(
    RoleSwatch("primary", cs.primary, cs.onPrimary),
    RoleSwatch("primaryContainer", cs.primaryContainer, cs.onPrimaryContainer),
    RoleSwatch("inversePrimary", cs.inversePrimary, cs.onPrimary),

    RoleSwatch("secondary", cs.secondary, cs.onSecondary),
    RoleSwatch("secondaryContainer", cs.secondaryContainer, cs.onSecondaryContainer),

    RoleSwatch("tertiary", cs.tertiary, cs.onTertiary),
    RoleSwatch("tertiaryContainer", cs.tertiaryContainer, cs.onTertiaryContainer),

    RoleSwatch("background", cs.background, cs.onBackground),
    RoleSwatch("surface", cs.surface, cs.onSurface),
    RoleSwatch("surfaceVariant", cs.surfaceVariant, cs.onSurfaceVariant),

    RoleSwatch("inverseSurface", cs.inverseSurface, cs.inverseOnSurface),

    RoleSwatch("error", cs.error, cs.onError),
    RoleSwatch("errorContainer", cs.errorContainer, cs.onErrorContainer),

    RoleSwatch("outline", cs.surface, cs.outline, isBorder = true),
    RoleSwatch("outlineVariant", cs.surface, cs.outlineVariant, isBorder = true),

    RoleSwatch("scrim", cs.scrim, Color.White)
)

@Composable
private fun RoleSectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier.padding(bottom = 6.dp)
    )
}

data class RoleSwatch(
    val name: String,
    val container: Color,
    val onContainer: Color,
    val isBorder: Boolean = false
)

@Composable
private fun RoleGrid(
    roles: List<RoleSwatch>,
    shape: Shape,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        roles.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
                row.forEach { r ->
                    RoleTile(r, shape, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun RoleTile(
    role: RoleSwatch,
    shape: Shape,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = shape,
        border = if (role.isBorder) BorderStroke(1.dp, role.onContainer) else null,
        color = if (role.isBorder) MaterialTheme.colorScheme.surface else role.container,
        tonalElevation = if (role.name.startsWith("surface")) 1.dp else 0.dp,
        modifier = modifier.height(88.dp)
    ) {
        Box(Modifier.fillMaxSize().padding(12.dp)) {
            Text(
                role.name,
                color = if (role.isBorder) MaterialTheme.colorScheme.onSurface else role.onContainer,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold
            )
            Box(
                Modifier
                    .align(Alignment.BottomEnd)
                    .size(18.dp)
                    .background(
                        color = if (role.isBorder) role.onContainer else role.onContainer,
                        shape = CircleShape
                    )
            )
        }
    }
}

@Composable
private fun ButtonsRow() {
    val cs = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {}, modifier = Modifier.weight(1f)) { Text("Filled") }
            FilledTonalButton(onClick = {}, modifier = Modifier.weight(1f),
                colors = filledTonalButtonColors()
            ) { Text("FilledTonal") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.weight(1f),
                colors = outlinedButtonColors(contentColor = cs.onSurface),
                border = BorderStroke(1.dp, cs.outline)
            ) { Text("Outlined") }
            ElevatedButton(onClick = {}, modifier = Modifier.weight(1f),
                colors = elevatedButtonColors(containerColor = cs.surface)
            ) { Text("Elevated") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            TextButton(onClick = {}, modifier = Modifier.weight(1f),
                colors = textButtonColors(contentColor = cs.primary)
            ) { Text("Text / Link") }
            AssistChip(onClick = {}, label = { Text("AssistChip") })
        }
    }
}

@Composable
private fun TextAndIconsDemo() {
    val cs = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text("Headline / onBackground", color = cs.onBackground, style = MaterialTheme.typography.titleLarge)
        Text("Body / onSurface", color = cs.onSurface)
        Text("Secondary / onSurfaceVariant", color = cs.onSurfaceVariant)
        Text("Disabled / outline", color = cs.outline)
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Default.Settings, contentDescription = null, tint = cs.onSurface)
            Icon(Icons.Default.Settings, contentDescription = null, tint = cs.onSurfaceVariant)
            Icon(Icons.Default.Settings, contentDescription = null, tint = cs.outline)
        }
    }
}

@Composable
private fun SurfaceCardExample(elevation: Dp) {
    val cs = MaterialTheme.colorScheme
    val elevated = cs.surfaceColorAtElevation(elevation)
    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation),
        colors = CardDefaults.elevatedCardColors(containerColor = elevated),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(Modifier.padding(16.dp)) {
            Text("Elevation = ${elevation.value.roundToInt()}dp", color = cs.onSurface)
            Spacer(Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { 0.6f },
                color = cs.primary,
                trackColor = cs.surfaceVariant
            )
        }
    }
}

@Composable
private fun VariantAndOutlineDemo() {
    val cs = MaterialTheme.colorScheme
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
        Surface(
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, cs.outline),
            color = cs.surface,
            modifier = Modifier.weight(1f)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Outlined card", color = cs.onSurface)
                Text("outline", color = cs.outline)
            }
        }
        Surface(
            shape = RoundedCornerShape(12.dp),
            color = cs.surfaceVariant,
            modifier = Modifier.weight(1f)
        ) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("surfaceVariant", color = cs.onSurfaceVariant)
                AssistChip(onClick = {}, label = { Text("Chip") })
            }
        }
    }
}

@Composable
private fun StatusChipsRow() {
    val cs = MaterialTheme.colorScheme
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        // Success via tertiary
        Text(
            "▲ +5.6%",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(cs.tertiaryContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = cs.onTertiaryContainer,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
        // Error
        Text(
            "▼ -3.2%",
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(cs.errorContainer)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            color = cs.onErrorContainer,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
private fun SurfaceContainersDemo() {
    val cs = MaterialTheme.colorScheme
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        ContainerTile("surfaceBright", cs.surfaceBright, cs.onSurface)
        ContainerTile("surface", cs.surface, cs.onSurface)
        ContainerTile("surfaceDim", cs.surfaceDim, cs.onSurface)
        ContainerTile("surfaceContainerLowest", cs.surfaceContainerLowest, cs.onSurface)
        ContainerTile("surfaceContainerLow", cs.surfaceContainerLow, cs.onSurface)
        ContainerTile("surfaceContainer", cs.surfaceContainer, cs.onSurface)
        ContainerTile("surfaceContainerHigh", cs.surfaceContainerHigh, cs.onSurface)
        ContainerTile("surfaceContainerHighest", cs.surfaceContainerHighest, cs.onSurface)
    }
}

@Composable
private fun ContainerTile(name: String, bg: Color, fg: Color) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = bg,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(name, color = fg)
            Text("#" + bg.value.toULong().toString(16).uppercase(), color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun SampleDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) { Text("OK") }
        },
        title = { Text("Dialog Title", color = MaterialTheme.colorScheme.onSurface) },
        text = {
            Text(
                "Dialog content uses surface/onSurface. Use this to check contrast & tone.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    )
}

/* -------- small util for snackbar host state extension -------- */
private var SnackbarHostState.showMessage: (suspend (String) -> Unit)?
    get() = null
    set(_) { /* no-op for sample; use rememberCoroutineScope in real screen */ }

/* -------------------- PREVIEW -------------------- */

@Preview
@Composable
private fun ColorSchemeLab_Light_Preview() {
    AppTheme(darkTheme = true, dynamicColor = false) {
        Surface { ColorSchemeLabScreen() }
    }
}

@Preview
@Composable
private fun ColorSchemeLab_Dark_Preview() {
    AppTheme(darkTheme = false, dynamicColor = false) {
        Surface { ColorSchemeLabScreen() }
    }
}
