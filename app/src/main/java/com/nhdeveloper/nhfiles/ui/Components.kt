package com.nhdeveloper.nhfiles.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.FolderZip
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.InsertDriveFile
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.nhdeveloper.nhfiles.data.FileItem
import com.nhdeveloper.nhfiles.data.FileKind
import com.nhdeveloper.nhfiles.data.FileUtils
import com.nhdeveloper.nhfiles.data.OpProgress

fun kindColor(k: FileKind): Color = when (k) {
    FileKind.FOLDER -> Color(0xFFFFA726)
    FileKind.IMAGE -> Color(0xFFFF7043)
    FileKind.VIDEO -> Color(0xFFEF5350)
    FileKind.AUDIO -> Color(0xFFAB47BC)
    FileKind.ARCHIVE -> Color(0xFFFFCA28)
    FileKind.APK -> Color(0xFF66BB6A)
    FileKind.TEXT -> Color(0xFF42A5F5)
    FileKind.PDF -> Color(0xFFE53935)
    FileKind.DOCUMENT -> Color(0xFF5C9DFF)
    FileKind.OTHER -> Color(0xFF9AA0A6)
}

fun kindIcon(k: FileKind): ImageVector = when (k) {
    FileKind.FOLDER -> Icons.Filled.Folder
    FileKind.IMAGE -> Icons.Filled.Image
    FileKind.VIDEO -> Icons.Filled.Videocam
    FileKind.AUDIO -> Icons.Filled.MusicNote
    FileKind.ARCHIVE -> Icons.Filled.FolderZip
    FileKind.APK -> Icons.Filled.Android
    FileKind.TEXT -> Icons.Filled.Code
    FileKind.PDF -> Icons.Filled.PictureAsPdf
    FileKind.DOCUMENT -> Icons.Filled.Description
    FileKind.OTHER -> Icons.Filled.InsertDriveFile
}

@Composable
fun FileIcon(kind: FileKind, size: Dp = 40.dp) {
    val c = kindColor(kind)
    Box(
        Modifier
            .size(size)
            .clip(RoundedCornerShape(10.dp))
            .background(c.copy(alpha = if (kind == FileKind.FOLDER) 0f else 0.16f)),
        contentAlignment = Alignment.Center,
    ) {
        Icon(kindIcon(kind), contentDescription = null, tint = c, modifier = Modifier.size(size * 0.66f))
    }
}

fun displayName(item: FileItem, showExt: Boolean): String =
    if (showExt || item.isDir) item.name else FileUtils.baseName(item.name)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FileRow(
    item: FileItem,
    selected: Boolean,
    selecting: Boolean,
    showExt: Boolean,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    compact: Boolean = false,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    val bg by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.55f) else Color.Transparent,
        animationSpec = tween(150),
        label = "rowBg",
    )
    Row(
        modifier
            .fillMaxWidth()
            .heightIn(min = 58.dp)
            .background(bg)
            .combinedClickable(onClick = onClick, onLongClick = onLongClick)
            .padding(horizontal = if (compact) 8.dp else 14.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        FileIcon(item.kind, if (compact) 38.dp else 42.dp)
        Spacer(Modifier.width(if (compact) 8.dp else 12.dp))
        Column(Modifier.weight(1f)) {
            Text(
                displayName(item, showExt),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                subtitle ?: if (compact) {
                    if (item.isDir) FileUtils.formatDate(item.modified)
                    else FileUtils.formatSize(item.size) + " · " + FileUtils.formatDate(item.modified)
                } else if (item.isDir) "${item.childCount} items" else FileUtils.formatSize(item.size),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
        if (compact) {
            if (selecting) {
                Spacer(Modifier.width(6.dp))
                Checkbox(checked = selected, onCheckedChange = null, modifier = Modifier.size(22.dp))
            }
        } else {
        Spacer(Modifier.width(8.dp))
        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
            Text(
                FileUtils.formatDate(item.modified),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
            )
            if (selecting) {
                Spacer(Modifier.height(2.dp))
                Checkbox(checked = selected, onCheckedChange = null, modifier = Modifier.size(22.dp))
            }
        }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
    )
}

@Composable
fun NameDialog(
    title: String,
    label: String,
    initial: String,
    confirm: String,
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit,
) {
    var v by remember { mutableStateOf(initial) }
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = { Text(title) },
        text = { OutlinedTextField(value = v, onValueChange = { v = it }, singleLine = true, label = { Text(label) }) },
        confirmButton = { TextButton(onClick = { onConfirm(v.trim()) }, enabled = v.isNotBlank()) { Text(confirm) } },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}

@Composable
fun ConfirmDialog(
    title: String,
    text: String,
    confirm: String,
    danger: Boolean = false,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(confirm, color = if (danger) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } },
    )
}

@Composable
fun MessageDialog(title: String, text: String, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = { TextButton(onClick = onDismiss) { Text("OK") } },
    )
}

@Composable
fun ChoiceDialog(
    title: String,
    options: List<String>,
    selected: Int,
    onSelect: (Int) -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(20.dp),
        title = { Text(title) },
        text = {
            Column {
                options.forEachIndexed { i, label ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(i) }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        RadioButton(selected = i == selected, onClick = null)
                        Spacer(Modifier.width(12.dp))
                        Text(label)
                    }
                }
            }
        },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } },
    )
}

@Composable
fun ProgressDialog(p: OpProgress, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = {},
        shape = RoundedCornerShape(20.dp),
        title = { Text(p.title) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (p.total > 0) {
                    LinearProgressIndicator(
                        progress = { (p.done.toFloat() / p.total.toFloat()).coerceIn(0f, 1f) },
                        modifier = Modifier.fillMaxWidth(),
                    )
                } else {
                    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                }
                if (p.current.isNotEmpty()) {
                    Text(p.current, maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.bodySmall)
                }
                if (p.total > 0) {
                    Text(
                        if (p.bytes) "${FileUtils.formatSize(p.done)} / ${FileUtils.formatSize(p.total)}" else "${p.done} / ${p.total}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        },
        confirmButton = { TextButton(onClick = onCancel) { Text("Cancel") } },
    )
}

@Composable
fun EmptyState(text: String, modifier: Modifier = Modifier) {
    Box(modifier.fillMaxWidth().height(160.dp), contentAlignment = Alignment.Center) {
        Text(text, color = MaterialTheme.colorScheme.onSurfaceVariant, style = MaterialTheme.typography.bodyMedium)
    }
}
