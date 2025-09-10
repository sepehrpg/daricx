package com.example.designsystem.component.tabs


import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.designsystem.component.AppHorizontalDivider
import com.example.designsystem.component.icons.AppIcon
import com.example.designsystem.component.AppText
import com.example.designsystem.extension.clickableWithNoRipple
import com.example.designsystem.theme.GradientColor1

/**
 * A tab that displays an icon before its label.
 */
@Composable
fun AppLeadingIconTab(
    selected: Boolean,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource? = null
) {
    LeadingIconTab(
        selected = selected,
        onClick = onClick,
        text = text,
        icon = icon,
        modifier = modifier,
        enabled = enabled,
        selectedContentColor = selectedContentColor,
        unselectedContentColor = unselectedContentColor,
        interactionSource = interactionSource
    )
}

/**
 * Model for [AppLeadingIconTab].
 */
data class AppLeadingIconTabItem(
    val id: Int,
    val text: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val description: String? = null,
    val selected: Boolean = false
)

@Preview(showBackground = true)
@Composable
fun AppLeadingIconTabPreview() {
    Row(Modifier.fillMaxWidth().height(100.dp)){
        var index by remember { mutableStateOf(0) }
        repeat(3){
            AppLeadingIconTab(
                selected = index == it,
                onClick = { index = it },
                text = { Text("Tab Item") },
                modifier = Modifier.weight(1f),
                icon = {
                    AppIcon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Icon"
                    )
                },
                selectedContentColor = Color.Blue,
                unselectedContentColor = Color.Gray
            )
        }
    }
}




data class AppCustomLeadingIconTabItem(
    val id:Int,
    val text: @Composable () -> Unit,
    val icon: @Composable () -> Unit,
    val description: String? = null,
    var selected: Boolean = false,
    val modifier: Modifier? = null,
    val enabled: Boolean? = null,
    val additionalUi: Boolean = false,
    val selectedContentColor: Color? = null,
    val unselectedContentColor: Color? = null,
    val interactionSource: MutableInteractionSource? = null
)

@Composable
fun AppCustomLeadingIconTab(
    item:List<AppCustomLeadingIconTabItem>,
    onClick: (index:Int) -> Unit,
    sharedClick: () -> Unit,
    modifier: Modifier = Modifier,
    boxModifier: Modifier = Modifier.fillMaxWidth()
        .shadow(0.dp, RoundedCornerShape(0.dp)).clip(RoundedCornerShape(10.dp)).background(
            Color.Gray
        ),
    rowModifier: Modifier = Modifier.fillMaxWidth(),
    selectedModifier:Modifier = Modifier.fillMaxWidth().shadow(0.dp, RoundedCornerShape(0.dp)).clip(
        RoundedCornerShape(10.dp)
    )
        .background(Color.White).clip(RoundedCornerShape(12.dp)),
    enabled: Boolean = true,
    selectedContentColor: Color = LocalContentColor.current,
    unselectedContentColor: Color = selectedContentColor,
    interactionSource: MutableInteractionSource? = null
) {
    var selected :Int? by remember { mutableStateOf(null) }

    Box(boxModifier){
        Column(Modifier.fillMaxWidth()){
            Row(rowModifier.height(52.dp)){
                repeat(item.size){ index->
                    if (item[index].selected) selected = index
                    LeadingIconTab(
                        selected = item[index].selected,
                        onClick = {
                            onClick(index)
                            selected = index
                        },
                        text = item[index].text,
                        icon = item[index].icon,
                        modifier = Modifier.weight(1f).padding(horizontal = 5.dp, vertical = 5.dp)
                            .then(if (item[index].selected) selectedModifier else item[index].modifier?:modifier ),
                        enabled = item[index].enabled?:enabled,
                        selectedContentColor = item[index].selectedContentColor?:selectedContentColor,
                        unselectedContentColor = item[index].unselectedContentColor?:unselectedContentColor,
                        interactionSource = item[index].interactionSource?:interactionSource
                    )
                }
            }

            if (selected!=null){
                if (item[selected!!].selected && !item[selected!!].description.isNullOrEmpty()){
                    AppHorizontalDivider()

                    if(item[selected!!].additionalUi){
                        Spacer(Modifier.height(10.dp))
                        Row(Modifier.fillMaxWidth().clickableWithNoRipple {
                            sharedClick()
                        }.padding(horizontal = 14.dp, vertical = 5.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
                            AppText("Share With", color = Color.DarkGray, fontSize = 13.sp)
                            AppIcon(Icons.Rounded.KeyboardArrowRight, contentDescription = "", tint = Color.Gray)
                        }
                        Spacer(Modifier.height(2.dp))
                    }

                    Box(Modifier.fillMaxWidth().padding(horizontal = 10.dp, vertical = 5.dp).background(
                        Color.Gray, shape = RoundedCornerShape(10.dp)
                    )){
                        AppText(item[selected!!].description.toString(), fontSize = 13.sp, modifier = Modifier.padding(10.dp))
                    }
                }
            }

        }
    }
}


@Preview
@Composable
fun AppCustomLeadingIconTabPreview(){
    val list = listOf(
        AppCustomLeadingIconTabItem(id = 1,text = {Text("Company", fontSize = 11.sp, fontWeight = FontWeight.Bold)},
            icon = { AppIcon(modifier = Modifier.size(20.dp), imageVector = Icons.Default.Groups, contentDescription = "Home Icon") },
            selected = true
        ),
        AppCustomLeadingIconTabItem(id = 2,text ={Text("Private", fontSize = 10.sp, fontWeight = FontWeight.Bold)},
            icon = { AppIcon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.Group, contentDescription = "Home Icon") } ),
        AppCustomLeadingIconTabItem(id = 3,text ={Text("Personal", fontSize = 10.sp, fontWeight = FontWeight.Bold)},
            icon = { AppIcon(modifier = Modifier.size(20.dp),imageVector = Icons.Default.Person, contentDescription = "Home Icon") } ,
            selected = true),
    )
    Box(Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 10.dp)){
        AppCustomLeadingIconTab(
            item = list,
            selectedContentColor = GradientColor1.bottom,
            unselectedContentColor = Color.Gray,
            onClick = {

            },
            sharedClick = {

            }
        )
    }
}





